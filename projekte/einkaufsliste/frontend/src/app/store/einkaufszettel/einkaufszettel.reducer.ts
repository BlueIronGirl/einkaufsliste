import {createReducer, on} from '@ngrx/store';
import {EinkaufszettelActions} from './einkaufszettel.actions';
import {Kategorie} from "../../entities/kategorie";

export const einkaufszettelFeatureKey = 'einkaufszettel';

export interface State {
  kategorien: Kategorie[];
}

export const initialState: State = {
  kategorien: []
};

export const einkaufszettelReducer = createReducer(
  initialState,
  on(EinkaufszettelActions.loadEinkaufszettels, state => state),
  on(EinkaufszettelActions.loadEinkaufszettelsSuccess, (state, action) => {
    return {...state, kategorien: action.data}
  }),
  on(EinkaufszettelActions.loadEinkaufszettelsFailure, (state, action) => state),
);

