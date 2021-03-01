import { Component, OnInit } from '@angular/core';
import {UserModel} from "../models/user.model";
import {UserService} from "../service/user.service";
import {LocalStorageService} from "ngx-webstorage";
import {PositionModel} from "../models/position.model";
import {PositionService} from "../service/position.service";
import {MatTableDataSource} from "@angular/material/table";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {EditJobDialogComponent} from "./edit-job-dialog/edit-job-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmDeleteComponent} from "./confirm-delete/confirm-delete.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  displayedColumns: string[] = ['date', 'title', 'location', 'clicks', 'edit','applications', 'delete'];
  token: string;
  user: UserModel;
  positions: PositionModel[] = null;
  datasource: any
  loading = true;

  constructor(private userService: UserService, private positionService: PositionService, private localStorage: LocalStorageService,
              private router : Router, private dialog: MatDialog, private _snackBar: MatSnackBar) {
    this.token = this.localStorage.retrieve('token');
    this.userService.getCurrentUser(this.token).subscribe(user => {
      this.user = user;
      this.positionService.getCompanyPositions(this.user.companyId).subscribe(data => {
        this.positions = data;
        this.datasource = new MatTableDataSource(data);
        this.loading = false;
      });
    });
  }

  onDelete(position: PositionModel) {
    const deleteDialog =
      this.dialog.open(ConfirmDeleteComponent, {
        width: '500px'
      });
    deleteDialog.afterClosed().subscribe(result => {
      if(result === 'yes') {
        this.positionService.deletePosition(position.positionId).subscribe(data => {
          this._snackBar.open('Position deleted', 'Close', {
            duration: 3000,
          });
        },
          error => {
          console.log(error);
            this._snackBar.open('An error has occurred', 'Close', {
              duration: 3000,
            });
          });
      }
    });
  }

  onEdit(position: PositionModel) {
    const editDialog =
      this.dialog.open(EditJobDialogComponent, {
        width: '500px',
        data: position
      });
    editDialog.afterClosed().subscribe(result => {
      if(result !== undefined) {
        this.positionService.updatePosition(result).subscribe(data => {
          this._snackBar.open('Saved', 'Close', {
            duration: 3000,
          });
        });
      }
      else {
        this._snackBar.open('No changes were saved', 'Close', {
          duration: 3000,
        });
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit(): void {
  }

}
