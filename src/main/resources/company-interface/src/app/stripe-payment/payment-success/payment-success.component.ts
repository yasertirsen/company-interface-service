import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {UserModel} from "../../model/user.model";

@Component({
  selector: 'app-payment-success',
  templateUrl: './payment-success.component.html',
  styleUrls: ['./payment-success.component.css']
})
export class PaymentSuccessComponent implements OnInit {
  user: UserModel;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private userService: UserService) {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    const token = localStorage.getItem('subscription_token');
    const linkToken = this.activatedRoute.snapshot.params.token;
    if(token !== linkToken) {
      router.navigateByUrl('/payment/failure')
    }
    else {
      this.user.subscribed = true;
      this.userService.updateUser(this.user).subscribe(data => {
        localStorage.setItem('currentUser', JSON.stringify(data));
        console.log(data);
      });
    }
  }

  ngOnInit(): void {
  }

}
