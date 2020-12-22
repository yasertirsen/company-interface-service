import { Component, OnInit } from '@angular/core';
import {UserModel} from "../models/user.model";
import {UserService} from "../service/user.service";
import {LocalStorageService} from "ngx-webstorage";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  token: string;
  user: UserModel;

  constructor(private userService: UserService, private localStorage: LocalStorageService) {
    this.token = this.localStorage.retrieve('token');
    this.userService.getCurrentUser(this.token).subscribe(user => {
      this.user = user;
    });
  }

  ngOnInit(): void {
  }

}
