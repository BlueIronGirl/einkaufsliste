import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, concatMap, map, switchMap} from 'rxjs/operators';
import {of} from 'rxjs';
import {EinkaufszettelActions} from './einkaufszettel.actions';
import {EinkaufszettelStoreService} from "../../service/einkaufszettel-store.service";


@Injectable()
export class EinkaufszettelEffects {

  loadEinkaufszettels$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(EinkaufszettelActions.loadEinkaufszettels),
      switchMap(() => this.einkaufszettelService.getAll().pipe(
          map(artikels => EinkaufszettelActions.loadEinkaufszettelsSuccess({data: artikels})),
          catchError(error => of(EinkaufszettelActions.loadEinkaufszettelsFailure({error})))
        )
      )
    );
  });

  addArtikel$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(EinkaufszettelActions.addArtikel),
      map(action => action.data),
      concatMap(inputData => this.einkaufszettelService.addArtikel(inputData).pipe(
        map(data => EinkaufszettelActions.addArtikelSuccess({data: data})),
        catchError(error => of(EinkaufszettelActions.addArtikelFailure({error})))
      ))
    )
  });

  editArtikel$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(EinkaufszettelActions.editArtikel),
      map(action => action.data),
      concatMap(inputData => this.einkaufszettelService.editArtikel(inputData).pipe(
        map(data => EinkaufszettelActions.editArtikelSuccess({data: data})),
        catchError(error => of(EinkaufszettelActions.editArtikelFailure({error})))
      ))
    )
  });

  deleteArtikel$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(EinkaufszettelActions.deleteArtikel),
      map(action => action.data),
      concatMap(inputData => this.einkaufszettelService.deleteArtikel(inputData).pipe(
        map(data => EinkaufszettelActions.deleteArtikelSuccess({data: data})),
        catchError(error => of(EinkaufszettelActions.deleteArtikelFailure({error})))
      ))
    )
  });


  constructor(private actions$: Actions, private einkaufszettelService: EinkaufszettelStoreService) {
  }
}
