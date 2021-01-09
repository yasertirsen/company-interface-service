import { Component, OnInit } from '@angular/core';
import {UserModel} from "../models/user.model";
import {UserService} from "../service/user.service";
import {LocalStorageService} from "ngx-webstorage";
import {PositionModel} from "../models/position.model";
import {PositionService} from "../service/position.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  token: string;
  user: UserModel;
  positions: PositionModel[] = null;

  constructor(private userService: UserService, private positionService: PositionService, private localStorage: LocalStorageService) {
    this.token = this.localStorage.retrieve('token');
    this.userService.getCurrentUser(this.token).subscribe(user => {
      this.user = user;
      this.positionService.getCompanyPositions(this.user.companyId).subscribe(data => {
        this.positions = data;
      });
    });
  }

  ngOnInit(): void {
  }

}
