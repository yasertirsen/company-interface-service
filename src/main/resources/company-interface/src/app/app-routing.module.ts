import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RegisterComponent} from "./register/register.component";
import {LoginComponent} from "./login/login.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {HomeComponent} from "./home/home.component";
import {AddJobComponent} from "./add-job/add-job.component";
import {ViewApplicationsComponent} from "./view-applications/view-applications.component";
import {StripePaymentComponent} from "./stripe-payment/stripe-payment.component";
import {JobStatsComponent} from "./job-stats/job-stats.component";

const routes: Routes = [
  {
    path:'',
    component: RegisterComponent,
    pathMatch: 'full'
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'add-job',
    component: AddJobComponent
  },{
    path: 'applications/:positionId',
    component: ViewApplicationsComponent
  },
  {
    path: 'payment',
    component: StripePaymentComponent
  },
  {
    path: 'stats/:positionId',
    component: JobStatsComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
