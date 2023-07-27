import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, retry} from "rxjs";
import {Kategorie} from "../entities/kategorie";

@Injectable({
  providedIn: 'root'
})
export class EinkaufszettelStoreService {
  private api = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {
  }

  getAll(): Observable<Kategorie[]> {
    return this.httpClient.get<Kategorie[]>(`${this.api}/alleKategorien`).pipe(
      retry(3)
    );
  }
}
