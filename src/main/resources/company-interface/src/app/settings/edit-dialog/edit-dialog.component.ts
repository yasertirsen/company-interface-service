import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {UserModel} from "../../model/user.model";

@Component({
  selector: 'app-edit-dialog',
  templateUrl: './edit-dialog.component.html',
  styleUrls: ['./edit-dialog.component.css']
})
export class EditDialogComponent implements OnInit {
  user: UserModel;

  constructor(public edit: MatDialogRef<EditDialogComponent>) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
  }

  onNoClick(): void {
    this.edit.close();
  }

  onSave() {
    this.edit.close(this.user);
  }
}
