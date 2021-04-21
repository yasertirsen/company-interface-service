import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ApplicationModel} from "../../model/application.model";

export interface Action {
  viewValue: string;
  value: string;
}

@Component({
  selector: 'app-actions-dialog',
  templateUrl: './actions-dialog.component.html',
  styleUrls: ['./actions-dialog.component.css']
})
export class ActionsDialogComponent implements OnInit {
  actions: Action[] = [
    {value: 'Rejected', viewValue: 'Reject'},
    {value: 'Asked For Interview', viewValue: 'Ask for interview'},
    {value: 'Offered', viewValue: 'Offer position'},
    {value: 'Under Review', viewValue: 'Application under review'},
    {value: 'No Response', viewValue: 'Other'}];
  status: string;

  constructor(public action: MatDialogRef<ActionsDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public application: ApplicationModel) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.action.close();
  }

  onSend(): void {
    this.application.status = this.status
    this.action.close(this.application);
  }

}
