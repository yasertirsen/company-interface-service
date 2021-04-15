import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../model/login-request-payload";
import {first} from "rxjs/operators";
import {LocalStorageService} from "ngx-webstorage";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide = true;
  model: LoginRequest = {
    email:'',
    password: ''
  };
  returnUrl: string;

  constructor(private client: HttpClient, private localStorage: LocalStorageService,
              private activatedRoute: ActivatedRoute, private router: Router,
              private _snackBar: MatSnackBar, private userService: UserService) { }

  ngOnInit(): void {

    this.activatedRoute.queryParams
      .subscribe(params => {
        if(params.registered !== undefined && params.registered === 'true') {
          this._snackBar.open('Please Check your inbox for activation email'
            + '\nactivate your account before you Login!', 'Close', {
            duration: 5000
          });
        }
        if(!!params.token) {
          this.userService.verify(params.token).subscribe(data => {
              this._snackBar.open(data, 'Close', {duration: 3000});
            },
            error => {
              this._snackBar.open('Error while verifying account', 'Close', {duration: 3000});
            });
        }
        this.returnUrl = params.returnUrl? params.returnUrl: '/home'
      });
  }

  loginCompany(): void {
    this.userService.login(this.model)
      .pipe(first())
      .subscribe(data => {
        this.router.navigateByUrl(this.returnUrl);
      }, error => {
        this._snackBar.open('Login Failed. Please check your credentials and try again.', 'Close', {
          duration: 5000,
        });
      });
  }

  getEmail() {
    return this.localStorage.retrieve('email');
  }

}


