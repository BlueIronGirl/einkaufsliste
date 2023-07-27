import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {EinkaufszettelActions} from "../../store/einkaufszettel/einkaufszettel.actions";
import {selectAllArtikel} from "../../store/einkaufszettel/einkaufszettel.selectors";
import {Kategorie} from "../../entities/kategorie";

@Component({
  selector: 'app-einkaufszettel',
  templateUrl: './einkaufszettel.component.html',
  styleUrls: ['./einkaufszettel.component.scss']
})
export class EinkaufszettelComponent implements OnInit{
  kategories!: Kategorie[];

  constructor(private store: Store) {
  }

  ngOnInit(): void {
    this.store.dispatch(EinkaufszettelActions.loadEinkaufszettels());

    this.store.select(selectAllArtikel).subscribe(kategories => this.kategories = kategories);
  }

}
