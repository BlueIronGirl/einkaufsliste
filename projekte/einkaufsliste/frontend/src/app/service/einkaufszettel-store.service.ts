import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, retry, throwError} from "rxjs";
import {Artikel} from "../entities/artikel";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class EinkaufszettelStoreService {
  private api = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {
  }

  private static errorHandler(error: HttpErrorResponse): Observable<never> {
    console.error('Fehler aufgetreten!' + error);
    return throwError(() => error);
  }

  getAll(): Observable<Artikel[]> {
    return this.httpClient.get<Artikel[]>(`${this.api}/alleArtikel`).pipe(
      retry(3)
    );
  }

  addArtikel(artikel: Artikel) {
    return this.httpClient.post<Artikel>(`${this.api}/artikel`, artikel).pipe(
      catchError(EinkaufszettelStoreService.errorHandler)
    );
  }

  editArtikel(artikel: Artikel) {
    return this.httpClient.put<Artikel>(`${this.api}/artikel/${artikel.id}`, artikel).pipe(
      catchError(EinkaufszettelStoreService.errorHandler)
    );
  }

  deleteArtikel(artikel: Artikel) {
    return this.httpClient.delete<Artikel>(`${this.api}/artikel/${artikel.id}`).pipe(
      catchError(EinkaufszettelStoreService.errorHandler)
    );
  }
}
