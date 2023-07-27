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

@NgModule({
  declarations: [
    AppComponent,
    EinkaufszettelComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CardModule,
    StoreModule.forRoot({}, {}),
    StoreDevtoolsModule.instrument({maxAge: 25, logOnly: !isDevMode()}),
    EffectsModule.forRoot([]),
    EffectsModule.forFeature([EinkaufszettelEffects]),
    StoreModule.forFeature(einkaufszettelFeatureKey, einkaufszettelReducer),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
