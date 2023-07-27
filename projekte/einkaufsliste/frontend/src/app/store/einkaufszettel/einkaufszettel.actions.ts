import { createActionGroup, emptyProps, props } from '@ngrx/store';
import {Artikel} from "../../entities/artikel";
import {HttpErrorResponse} from "@angular/common/http";

export const EinkaufszettelActions = createActionGroup({
  source: 'Einkaufszettel',
  events: {
    'Load Einkaufszettels': emptyProps(),
    'Load Einkaufszettels Success': props<{ data: Artikel[] }>(),
    'Load Einkaufszettels Failure': props<{ error: HttpErrorResponse }>(),
  }
});
