import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../models/login-request-payload";
import {LoginResponse} from "../models/login-response-payload";
import {map, tap} from "rxjs/operators";
import {LocalStorageService} from "ngx-webstorage";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbAlert} from "@ng-bootstrap/ng-bootstrap";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: LoginRequest = {
    email:'',
    password: ''
  };

  constructor(private client: HttpClient, private localStorage: LocalStorageService,
              private activatedRoute: ActivatedRoute, private router: Router,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {

    this.activatedRoute.queryParams
      .subscribe(params => {
        if (params.registered !== undefined && params.registered === 'true') {
          this._snackBar.open('Please Check your inbox for activation email'
            + '\nactivate your account before you Login!', 'Close', {
            duration: 5000
          });
        }
      });
  }

  loginCompany(): void {
    let url = 'http://localhost:8081/login';
    this.client.post<LoginResponse>(url, this.model).pipe(map(data => {
      this.localStorage.store('token', data.token);
      this.localStorage.store('email', data.email);
      this.localStorage.store('expiresIn', data.expiresIn);
    })).subscribe(data => {
      this.router.navigateByUrl('/home');
    }, error => {
      this._snackBar.open('Login Failed. Please check your credentials and try again', 'Close', {
        duration: 5000
      });
    });
  }

  getJwtToken() {
    return this.localStorage.retrieve('token');
  }

  getEmail() {
    return this.localStorage.retrieve('email');
  }

}


