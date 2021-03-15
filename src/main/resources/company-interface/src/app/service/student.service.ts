import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {LocalStorageService} from "ngx-webstorage";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient, private localStorage: LocalStorageService) {}

  getStudent(email: string): Observable<any> {
    return this.http.get('http://localhost:8081/getStudent',
      {
        params: new HttpParams().set("email", email)
      });
  }

  getStats(emails): Observable<any> {
    return this.http.post('http://localhost:8081/getStats',
      {
        "emails": emails
      });
  }
}
