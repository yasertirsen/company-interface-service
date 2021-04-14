import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RegisterComponent} from './register/register.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {LayoutModule} from '@angular/cdk/layout';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {NgxWebstorageModule} from "ngx-webstorage";
import {ToastrModule} from "ngx-toastr";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {LoginComponent} from './login/login.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {MainNavComponent} from './main-nav/main-nav.component';
import {HomeComponent} from './home/home.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AddJobComponent} from './add-job/add-job.component';
import {MatTableModule} from '@angular/material/table';
import {MatSpinnerOverlayComponent} from './shared/mat-spinner-overlay/mat-spinner-overlay.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatStepperModule} from "@angular/material/stepper";
import {MatDialogModule} from "@angular/material/dialog";
import {MatChipsModule} from "@angular/material/chips";
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {EditJobDialogComponent} from './home/edit-job-dialog/edit-job-dialog.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {ConfirmDeleteComponent} from './home/confirm-delete/confirm-delete.component';
import {ViewApplicationsComponent} from './view-applications/view-applications.component';
import {NgxStripeModule} from 'ngx-stripe';
import {StripePaymentComponent} from './stripe-payment/stripe-payment.component';
import {MatSelectModule} from "@angular/material/select";
import {JobStatsComponent} from './job-stats/job-stats.component';
import {ChartsModule} from 'ng2-charts';
import {TokenInterceptor} from "./shared/interceptor/TokenInterceptor";
import {ChooseCandidateDialogComponent} from './home/choose-candidate-dialog/choose-candidate-dialog.component';
import {ArchivedJobsComponent} from './archived-jobs/archived-jobs.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from "@angular/material/sort";
import {FlexLayoutModule} from '@angular/flex-layout';
import {PaymentSuccessComponent} from './stripe-payment/payment-success/payment-success.component';
import {PaymentFailureComponent} from './stripe-payment/payment-failure/payment-failure.component';
import {VerifyChangePasswordComponent} from "./login/verify-change-password/verify-change-password.component";
import {ChangePasswordComponent} from "./login/change-password/change-password.component";
import {AlumniProfileComponent} from "./alumni-profile/alumni-profile.component";
import {MatExpansionModule} from '@angular/material/expansion';
import {ActionsDialogComponent} from './view-applications/actions-dialog/actions-dialog.component';
import {SettingsComponent} from './settings/settings.component';
import {EditDialogComponent} from './settings/edit-dialog/edit-dialog.component';
import {MatBadgeModule} from '@angular/material/badge';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    NotFoundComponent,
    MainNavComponent,
    HomeComponent,
    AddJobComponent,
    MatSpinnerOverlayComponent,
    EditJobDialogComponent,
    ConfirmDeleteComponent,
    ViewApplicationsComponent,
    StripePaymentComponent,
    JobStatsComponent,
    ChooseCandidateDialogComponent,
    ArchivedJobsComponent,
    PaymentSuccessComponent,
    PaymentFailureComponent,
    VerifyChangePasswordComponent,
    ChangePasswordComponent,
    AlumniProfileComponent,
    ActionsDialogComponent,
    SettingsComponent,
    EditDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    FontAwesomeModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgbModule,
    MatStepperModule,
    ReactiveFormsModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatChipsModule,
    MatSnackBarModule,
    MatAutocompleteModule,
    NgxStripeModule.forRoot('pk_test_51IR1L4GPvpv5kncLxVVXUDzx4vG2xCUB6nPRrlsoYTGas4F3JhGzA1QIPb5KhxcrLhv1VHm9B4pRgSvX4x9HTXdT00hwEm9Fni'),
    MatSelectModule,
    ChartsModule,
    MatPaginatorModule,
    MatSortModule,
    FlexLayoutModule,
    MatExpansionModule,
    MatBadgeModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
