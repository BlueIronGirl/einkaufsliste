import {createReducer, on} from '@ngrx/store';
import {EinkaufszettelActions} from './einkaufszettel.actions';
import {Artikel} from "../../entities/artikel";

export const einkaufszettelFeatureKey = 'einkaufszettel';

export interface State {
  artikels: Artikel[];
}

export const initialState: State = {
  artikels: []
};

export const einkaufszettelReducer = createReducer(
  initialState,
  // loadEinkaufszettel
  on(EinkaufszettelActions.loadEinkaufszettelsSuccess, (state, action) => {
    return {...state, artikels: action.data}
  }),

  // addArtikel
  on(EinkaufszettelActions.addArtikelSuccess, (state, action) => {
    let artikels = [...state.artikels, action.data];
    return {...state, artikels: artikels};
  }),

  // updateArtikel
  on(EinkaufszettelActions.editArtikelSuccess, (state, action) => {
    let indexToUpdate = state.artikels.findIndex(item => item.id === action.data.id);

    let artikels = [...state.artikels];
    if (indexToUpdate >= 0) {
      artikels[indexToUpdate] = action.data;
    }

    return {...state, artikels: artikels};
  }),

  // deleteArtikel
  on(EinkaufszettelActions.deleteArtikelSuccess, (state, action) => {
    let indexToRemove = state.artikels.findIndex(item => item.id === action.data.id);

    let artikels = [...state.artikels];
    if (indexToRemove >= 0) {
      artikels.splice(indexToRemove, 1);
    }

    return {artikels: artikels};
  }),
);

