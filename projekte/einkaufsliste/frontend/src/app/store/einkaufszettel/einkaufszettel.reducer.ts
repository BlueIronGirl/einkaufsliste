import { createFeature, createReducer, on } from '@ngrx/store';
import { EinkaufszettelActions } from './einkaufszettel.actions';
import {Artikel} from "../../entities/artikel";

export const einkaufszettelFeatureKey = 'einkaufszettel';

export interface State {
  artikel: Artikel[];
}

export const initialState: State = {
  artikel: []
};

export const reducer = createReducer(
  initialState,
  on(EinkaufszettelActions.loadEinkaufszettels, state => state),
  on(EinkaufszettelActions.loadEinkaufszettelsSuccess, (state, action) => {
    return {...state, artikel: action.data}
  }),
  on(EinkaufszettelActions.loadEinkaufszettelsFailure, (state, action) => state),
);

export const einkaufszettelFeature = createFeature({
  name: einkaufszettelFeatureKey,
  reducer,
});

