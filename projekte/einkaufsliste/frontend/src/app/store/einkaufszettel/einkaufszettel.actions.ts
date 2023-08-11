import {createActionGroup, emptyProps, props} from '@ngrx/store';
import {HttpErrorResponse} from "@angular/common/http";
import {Artikel} from "../../entities/artikel";

export const EinkaufszettelActions = createActionGroup({
  source: 'Einkaufszettel',
  events: {
    'Load Einkaufszettels': emptyProps(),
    'Load Einkaufszettels Success': props<{ data: Artikel[] }>(),
    'Load Einkaufszettels Failure': props<{ error: HttpErrorResponse }>(),

    'Create Artikel': props<{ data: Artikel }>(),
    'Create Artikel Success' : props<{ data: Artikel }>(),
    'Create Artikel Failure': props<{ error: HttpErrorResponse }>(),

    'Update Artikel': props<{ data: Artikel }>(),
    'Update Artikel Success' : props<{ data: Artikel }>(),
    'Update Artikel Failure': props<{ error: HttpErrorResponse }>(),

    'Delete Artikel': props<{ data: Artikel }>(),
    'Delete Artikel Success' : props<{ data: Artikel }>(),
    'Delete Artikel Failure': props<{ error: HttpErrorResponse }>(),

    'Archiviere Artikel': emptyProps(),
    'Archiviere Artikel Success' : props<{ data: Artikel[] }>(),
    'Archiviere Artikel Failure': props<{ error: HttpErrorResponse }>(),
  }
});
