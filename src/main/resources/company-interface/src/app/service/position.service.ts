import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserModel} from "../models/user.model";
import {Observable} from "rxjs";
import {PositionModel} from "../models/position.model";
import {SkillModel} from "../models/skill.model";

@Injectable({
  providedIn: 'root'
})
export class PositionService {

  constructor(private http: HttpClient) { }

  addPosition(position: PositionModel): Observable<PositionModel>{
    return this.http.post<PositionModel>('http://localhost:8081/positions/add',
      {
        "title": position.title,
        "description": position.description,
        "location": position.location,
        "date": position.date,
        "salary": position.salary,
        "clicks": position.clicks,
        "company": position.company,
        "requirements": position.requirements
      });
  }

  getCompanyPositions(id: number): Observable<Array<PositionModel>>{
    // @ts-ignore
    return this.http.get('http://localhost:8081/getCompanyPositions/' + id);
  }
}
