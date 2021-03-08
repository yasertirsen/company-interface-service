import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {LocalStorageService} from "ngx-webstorage";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  headers: any;

  constructor(private http: HttpClient, private localStorage: LocalStorageService) {
    this.headers = new HttpHeaders({
      'Content-type': 'application/json',
      'Authorization': `Bearer ${this.localStorage.retrieve('token')}`
    });
  }

  getStudent(email: string): Observable<any> {
    return this.http.get('http://localhost:8081/getStudent',
      {headers: this.headers,
        params: new HttpParams().set("email", email)});
  }

  getStats(emails): Observable<any> {
    return this.http.post('http://localhost:8081/getStats',
      {
        "emails": emails
      },
      {headers: this.headers});
  }
}
