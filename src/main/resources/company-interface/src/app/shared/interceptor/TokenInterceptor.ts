import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from "rxjs";
import {UserService} from "../../service/user.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(public userService: UserService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if(!localStorage.getItem('currentUser')) {
      return next.handle(request);
    }
      const tokenizedRequest = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.userService.getToken()}`
        }
      });
      return next.handle(tokenizedRequest);
  }
}
