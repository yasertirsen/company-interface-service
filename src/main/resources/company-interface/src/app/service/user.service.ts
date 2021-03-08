import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserModel} from "../models/user.model";
import {LocalStorageService} from "ngx-webstorage";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  headers: any
  constructor(private http: HttpClient, private localStorage: LocalStorageService) {
    this.headers = new HttpHeaders({
      'Content-type': 'application/json',
      'Authorization': `Bearer ${this.localStorage.retrieve('token')}`
    });
  }

  getCurrentUser(): Observable<any>{
    return this.http.get('http://localhost:8081/currentUser', {
      headers: this.headers
    });
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
      },
      {headers: this.headers});
  }
}
