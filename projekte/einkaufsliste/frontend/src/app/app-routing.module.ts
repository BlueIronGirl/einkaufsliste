import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {EinkaufszettelComponent} from "./components/einkaufszettel/einkaufszettel.component";
import {EditArtikelComponent} from "./components/edit-artikel/edit-artikel.component";

const routes: Routes = [
  {path: '', redirectTo: 'einkaufszettel', pathMatch: 'full'},
  {path: 'einkaufszettel', component: EinkaufszettelComponent},
  {path: 'artikel/:id', component: EditArtikelComponent},
  {path: 'artikel/new', component: EditArtikelComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
