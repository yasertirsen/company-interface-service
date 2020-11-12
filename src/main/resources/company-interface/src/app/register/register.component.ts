import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterRequest} from "../models/register-request-payload";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  hide = true;
  model: RegisterRequest = {
    name:'',
    email:'',
    password:''
  };

  constructor(private client: HttpClient, private router: Router,
              private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  registerCompany(): void {
    let url = 'http://localhost:8081/register';
    this.client.post(url, this.model)
      .subscribe(data => {
        this.router.navigate(['/login'],
          {queryParams: {registered: 'true'}});
      }, error => {
        console.log(error);
        this.toastr.error('Registration Failed! Please try again');
      });
  }
}
