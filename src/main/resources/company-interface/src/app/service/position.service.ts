import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {PositionModel} from "../models/position.model";
import {LocalStorageService} from "ngx-webstorage";

@Injectable({
  providedIn: 'root'
})
export class PositionService {
  headers: any;

  constructor(private localStorage: LocalStorageService, private http: HttpClient) {
    this.headers = new HttpHeaders({
      'Authorization': `Bearer ${this.localStorage.retrieve('token')}`
    });
  }

  addPosition(position: PositionModel): Observable<any>{
    return this.http.post('http://localhost:8081/positions/add',
      {
        "positionId": position.positionId,
        "title": position.title,
        "description": position.description,
        "location": position.location,
        "date": position.date,
        "salary": position.salary,
        "clicks": position.clicks,
        "priority": position.priority,
        "company": position.company,
        "requirements": position.requirements
      },
      {headers: this.headers});
  }

  updatePosition(position: PositionModel): Observable<any>{
    return this.http.put('http://localhost:8081/positions/update',
      {
        "positionId": position.positionId,
        "title": position.title,
        "description": position.description,
        "location": position.location,
        "date": position.date,
        "salary": position.salary,
        "clicks": position.clicks,
        "priority": position.priority,
        "company": position.company,
        "requirements": position.requirements
      },
      {headers: this.headers});
  }

  deletePosition(positionId: number): Observable<any>{
    return this.http.delete('http://localhost:8081/positions/delete/' + positionId,
      {headers: this.headers});
  }

  getCompanyPositions(id: number): Observable<any>{
    return this.http.get('http://localhost:8081/getCompanyPositions/' + id,
      {headers: this.headers});
  }

  getApplications(positionId: number): Observable<any> {
    return this.http.get('http://localhost:8081/positions/getPositionApplications/' + positionId,
      {headers: this.headers});
  }

  getCv(applicationId: number): Observable<any> {
    return this.http.get('http://localhost:8081/getCv/' + applicationId,
      {headers: this.headers, responseType: 'blob'});
  }
}
