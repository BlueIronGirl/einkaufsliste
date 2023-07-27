import {createActionGroup, emptyProps, props} from '@ngrx/store';
import {HttpErrorResponse} from "@angular/common/http";
import {Kategorie} from "../../entities/kategorie";

export const EinkaufszettelActions = createActionGroup({
  source: 'Einkaufszettel',
  events: {
    'Load Einkaufszettels': emptyProps(),
    'Load Einkaufszettels Success': props<{ data: Kategorie[] }>(),
    'Load Einkaufszettels Failure': props<{ error: HttpErrorResponse }>(),
  }
});
