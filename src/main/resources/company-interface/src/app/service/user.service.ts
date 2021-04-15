import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserModel} from "../model/user.model";
import {LoginRequest} from "../model/login-request-payload";
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
    return this.http.put<UserModel>('http://localhost:8081/update', user);
  }

  subscribeCompany(user: UserModel): Observable<any> {
    return this.http.post<UserModel>('http://localhost:8081/creatCheckoutSession', user);
  }

  verifyToken(token: string, password: string): Observable<any>{
    return this.http.put('http://localhost:8081/changePassword/' + token, {},
      {params: {password: password}});
  }

  sendVerify(email: string): Observable<any>{
    return this.http.get('http://localhost:8081/sendVerify/',
      {params: {email: email}});
  }

  verify(token: string): Observable<any> {
    return this.http.get('http://localhost:8081/verification/' + token);
  }
}
