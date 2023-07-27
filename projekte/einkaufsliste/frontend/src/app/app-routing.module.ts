import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {EinkaufszettelComponent} from "./components/einkaufszettel/einkaufszettel.component";

const routes: Routes = [
  {path: '', redirectTo: 'einkaufszettel', pathMatch: 'full'},
  {path: 'einkaufszettel', component: EinkaufszettelComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
