import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, retry} from "rxjs";
import {Artikel} from "../entities/artikel";

@Injectable({
  providedIn: 'root'
})
export class EinkaufszettelStoreService {
  private api = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {
  }

  getAll(): Observable<Artikel[]> {
    return this.httpClient.get<Artikel[]>(`${this.api}/alleArtikel`).pipe(
      retry(3)
    );
  }
}
