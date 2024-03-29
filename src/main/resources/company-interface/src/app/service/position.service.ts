import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PositionModel} from "../model/position.model";
import {LocalStorageService} from "ngx-webstorage";
import {ApplicationModel} from "../model/application.model";
import {ResumeModel} from "../model/resume.model";

@Injectable({
  providedIn: 'root'
})
export class PositionService {

  constructor(private localStorage: LocalStorageService, private http: HttpClient) {}

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
        "archive": position.archive,
        "company": position.company,
        "requirements": position.requirements
      });
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
        "archive": position.archive,
        "company": position.company,
        "requirements": position.requirements
      });
  }

  deletePosition(positionId: number): Observable<any>{
    return this.http.delete('http://localhost:8081/positions/delete/' + positionId);
  }

  getCompanyPositions(id: number): Observable<any>{
    return this.http.get('http://localhost:8081/getCompanyPositions/' + id);
  }

  getApplications(positionId: number): Observable<any> {
    return this.http.get('http://localhost:8081/positions/getPositionApplications/' + positionId);
  }

  getCv(applicationId: number): Observable<any> {
    return this.http.get('http://localhost:8081/getCv/' + applicationId,
      {responseType: 'blob'});
  }

  updateApplication(application: ApplicationModel, message: string): Observable<any> {
    return this.http.put('http://localhost:8081/application/update', {
      "applicationId": application.applicationId,
      "fullName": application.fullName,
      "email": application.email,
      "resume": application.resume,
      "positionId": application.positionId,
      "status": application.status,
      "date": application.date,
    },
      {params: {message: message}});
  }
}
