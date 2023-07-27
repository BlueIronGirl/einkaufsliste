import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, map, switchMap} from 'rxjs/operators';
import {of} from 'rxjs';
import {EinkaufszettelActions} from './einkaufszettel.actions';
import {EinkaufszettelStoreService} from "../../service/einkaufszettel-store.service";


@Injectable()
export class EinkaufszettelEffects {

  loadEinkaufszettels$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(EinkaufszettelActions.loadEinkaufszettels),
      switchMap(() => this.einkaufszettelService.getAll().pipe(
          map(artikel => EinkaufszettelActions.loadEinkaufszettelsSuccess({data: artikel})),
          catchError(error => of(EinkaufszettelActions.loadEinkaufszettelsFailure({error})))
        )
      )
    );
  });


  constructor(private actions$: Actions, private einkaufszettelService: EinkaufszettelStoreService) {
  }
}
