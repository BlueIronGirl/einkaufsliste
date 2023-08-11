import {createActionGroup, emptyProps, props} from '@ngrx/store';
import {HttpErrorResponse} from "@angular/common/http";
import {Artikel} from "../../entities/artikel";

export const EinkaufszettelActions = createActionGroup({
  source: 'Einkaufszettel',
  events: {
    'Load Einkaufszettels': emptyProps(),
    'Load Einkaufszettels Success': props<{ data: Artikel[] }>(),
    'Load Einkaufszettels Failure': props<{ error: HttpErrorResponse }>(),

    'Add Artikel': props<{ data: Artikel }>(),
    'Add Artikel Success' : props<{ data: Artikel }>(),
    'Add Artikel Failure': props<{ error: HttpErrorResponse }>(),

    'Edit Artikel': props<{ data: Artikel }>(),
    'Edit Artikel Success' : props<{ data: Artikel }>(),
    'Edit Artikel Failure': props<{ error: HttpErrorResponse }>(),

    'Delete Artikel': props<{ data: Artikel }>(),
    'Delete Artikel Success' : props<{ data: Artikel }>(),
    'Delete Artikel Failure': props<{ error: HttpErrorResponse }>(),
  }
});
