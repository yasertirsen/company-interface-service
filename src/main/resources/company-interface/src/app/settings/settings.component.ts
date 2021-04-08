import {Component, OnInit} from '@angular/core';
import {UserModel} from "../model/user.model";
import {MatDialog} from "@angular/material/dialog";
import {EditDialogComponent} from "./edit-dialog/edit-dialog.component";
import {UserService} from "../service/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  user: UserModel;
  loading = true;

  constructor(private dialog: MatDialog, private userService: UserService,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.loading = false;
  }

  openDialog() {
    const skillsDialog =
      this.dialog.open(EditDialogComponent, {
        width: '500px',
        data: {user: this.user}
      });
    skillsDialog.afterClosed().subscribe(result => {
      console.log(result);
      if(!!result) {
        result.token = null;
        this.userService.updateUser(result).subscribe(data => {
          this.user = data;
          localStorage.setItem('currentUser', JSON.stringify(data));
          this._snackBar.open('Changes saved successfully', 'Close', {
              duration: 3000,
            });
        },
          error => {
            console.log(error);
          });
      }
      else {
        this._snackBar.open('No changes were saved', 'Close', {
          duration: 3000,
        });
      }
    });
  }
}
