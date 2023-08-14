import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {MessageService} from "primeng/api";
import {User} from "../../entities/user";
import {EinkaufszettelActions} from "../../store/einkaufszettel/einkaufszettel.actions";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [MessageService]
})
export class LoginComponent implements OnInit {
  user?: User;

  constructor(private store: Store, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.user = {
      username: 'alice',
      password: 'admin'
    }
    this.login();
  }

  login() {
    if (this.user) {
      this.store.dispatch(EinkaufszettelActions.login({data: this.user}));
    }
  }
}
