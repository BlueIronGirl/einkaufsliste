import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EinkaufszettelComponent} from "./components/einkaufszettel/einkaufszettel.component";
import {EditArtikelComponent} from "./components/edit-artikel/edit-artikel.component";
import {LoginComponent} from "./components/login/login.component";

const routes: Routes = [
  {path: '', redirectTo: 'einkaufszettel', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'einkaufszettel', component: EinkaufszettelComponent},
  {path: 'artikel/:id', component: EditArtikelComponent},
  {path: 'artikel/new', component: EditArtikelComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
