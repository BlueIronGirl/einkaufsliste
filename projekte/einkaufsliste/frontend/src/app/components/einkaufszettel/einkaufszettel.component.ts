import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {EinkaufszettelActions} from "../../store/einkaufszettel/einkaufszettel.actions";
import {Artikel} from "../../entities/artikel";
import {Observable} from "rxjs";
import {selectAllArtikel} from "../../store/einkaufszettel/einkaufszettel.selectors";

@Component({
  selector: 'app-einkaufszettel',
  templateUrl: './einkaufszettel.component.html',
  styleUrls: ['./einkaufszettel.component.scss']
})
export class EinkaufszettelComponent implements OnInit{
  artikel$!: Observable<Artikel[]>;

  constructor(private store: Store) {
  }

  ngOnInit(): void {
    this.store.dispatch(EinkaufszettelActions.loadEinkaufszettels());

    this.artikel$ = this.store.select(selectAllArtikel);
  }

}
