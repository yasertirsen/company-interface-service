import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) {}

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

  getStudentAvatar(studentId: number): Observable<any>{
    return this.http.get('http://localhost:8081/getStudentAvatar/' + studentId);
  }
}
