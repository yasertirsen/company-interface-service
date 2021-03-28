import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {RegisterRequest} from "../model/register-request-payload";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  hide = true;
  user: RegisterRequest = {
    email: '',
    password: '',
    name: '',
    companyUrl: '',
    address: '',
    recruiter: '',
    recruiterPhone: ''
  };

  constructor(private client: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  registerCompany(): void {
    let url = 'http://localhost:8081/register';
    this.client.post(url, this.user)
      .subscribe(data => {
        this.router.navigate(['/login'],
          {queryParams: {registered: 'true'}});
      }, error => {
        console.log(error);
      });
  }
}
