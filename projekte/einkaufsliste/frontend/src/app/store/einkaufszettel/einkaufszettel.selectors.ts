import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromEinkaufszettel from './einkaufszettel.reducer';

export const selectEinkaufszettelState = createFeatureSelector<fromEinkaufszettel.State>(
  fromEinkaufszettel.einkaufszettelFeatureKey
);
