import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserModel} from "../models/user.model";
import {LocalStorageService} from "ngx-webstorage";
import {LoginRequest} from "../models/login-request-payload";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient, private localStorage: LocalStorageService) {}

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
        return user;
      }));
  }

  logout(): void {
    localStorage.removeItem('currentUser');
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
        "profile": user.profile
      });
  }
}
