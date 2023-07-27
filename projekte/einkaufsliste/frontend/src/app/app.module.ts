import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EinkaufszettelComponent } from './components/einkaufszettel/einkaufszettel.component';

@NgModule({
  declarations: [
    AppComponent,
    EinkaufszettelComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
