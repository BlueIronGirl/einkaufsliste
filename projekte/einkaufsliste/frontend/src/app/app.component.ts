import { Component } from '@angular/core';
import {Store} from "@ngrx/store";
import {MessageService} from "primeng/api";
import {EinkaufszettelActions} from "./store/einkaufszettel/einkaufszettel.actions";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [MessageService]
})
export class AppComponent {
  adminModus = false;
  menuRendered = false;

  constructor(private store: Store, private messageService: MessageService) {
  }

  archiviereGekaufteArtikel() {
    this.store.dispatch(EinkaufszettelActions.archiviereArtikel());

    this.messageService.clear();
    this.messageService.add({severity: 'success', summary: 'Artikel wurden archiviert'});
  }
}
