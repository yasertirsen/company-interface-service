import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserModel} from "../models/user.model";
import {LocalStorageService} from "ngx-webstorage";
import {LoginRequest} from "../models/login-request-payload";
import {map} from "rxjs/operators";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {}

  public getToken(): string {
    return localStorage.getItem('token');
  }

  login(details: LoginRequest): Observable<any> {
    return this.http.post<any>('http://localhost:8081/login',
      {"email": details.email,
        "password": details.password})
      .pipe(map(user => {
        if(user && user.token) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          localStorage.setItem('token', user.token);
          localStorage.setItem('email', user.email);
          localStorage.setItem('expiresIn', user.expiresIn);
        }
        this.autoLogout(user.expiresIn);
        return user;
      }));
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    this.router.navigateByUrl('/login');
    if(this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }

  updateUser(user: UserModel): Observable<UserModel>{
    return this.http.put<UserModel>('http://localhost:8081/update',
      {
        "companyId": user.companyId,
        "email": user.email,
        "password": user.password,
        "name": user.name,
        "companyUrl": user.companyUrl,
        "address": user.address,
        "recruiter": user.recruiter,
        "recruiterPhone": user.recruiterPhone,
        "created": user.created,
        "role": user.role,
        "authorities": user.authorities,
        "isLocked": user.isLocked,
        "enabled": user.enabled,
        "subscribed": user.subscribed,
        "profile": user.profile
      });
  }

  subscribeCompany(user: UserModel): Observable<any> {
    return this.http.post<UserModel>('http://localhost:8081/creatCheckoutSession',
      {
        "companyId": user.companyId,
        "email": user.email,
        "password": user.password,
        "name": user.name,
        "companyUrl": user.companyUrl,
        "address": user.address,
        "recruiter": user.recruiter,
        "recruiterPhone": user.recruiterPhone,
        "created": user.created,
        "role": user.role,
        "authorities": user.authorities,
        "isLocked": user.isLocked,
        "enabled": user.enabled,
        "subscribed": user.subscribed,
        "profile": user.profile
      });
  }
}
