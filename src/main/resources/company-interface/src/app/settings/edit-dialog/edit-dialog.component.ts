import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UserModel} from "../../model/user.model";

@Component({
  selector: 'app-edit-dialog',
  templateUrl: './edit-dialog.component.html',
  styleUrls: ['./edit-dialog.component.css']
})
export class EditDialogComponent implements OnInit {

  constructor(public edit: MatDialogRef<EditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {user: UserModel}) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.edit.close();
  }

  onSave() {
    this.edit.close(this.data.user);
  }
}
