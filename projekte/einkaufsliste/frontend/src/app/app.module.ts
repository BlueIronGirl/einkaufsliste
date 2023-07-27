import {NgModule, isDevMode} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {EinkaufszettelComponent} from './components/einkaufszettel/einkaufszettel.component';
import {StoreModule} from '@ngrx/store';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {EffectsModule} from '@ngrx/effects';
import {EinkaufszettelEffects} from "./store/einkaufszettel/einkaufszettel.effects";
import {einkaufszettelFeatureKey, einkaufszettelReducer} from "./store/einkaufszettel/einkaufszettel.reducer";
import {HttpClientModule} from "@angular/common/http";
import {CardModule} from "primeng/card";
import {CheckboxModule} from "primeng/checkbox";
import {FormsModule} from "@angular/forms";
import {PanelModule} from "primeng/panel";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    EinkaufszettelComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    CardModule,
    CheckboxModule,
    StoreModule.forRoot({}, {}),
    StoreDevtoolsModule.instrument({maxAge: 25, logOnly: !isDevMode()}),
    EffectsModule.forRoot([]),
    EffectsModule.forFeature([EinkaufszettelEffects]),
    StoreModule.forFeature(einkaufszettelFeatureKey, einkaufszettelReducer),
    FormsModule,
    PanelModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
