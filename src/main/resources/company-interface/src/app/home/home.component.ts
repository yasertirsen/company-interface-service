import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {UserModel} from "../model/user.model";
import {UserService} from "../service/user.service";
import {PositionModel} from "../model/position.model";
import {PositionService} from "../service/position.service";
import {MatTableDataSource} from "@angular/material/table";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {EditJobDialogComponent} from "./edit-job-dialog/edit-job-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmDeleteComponent} from "./confirm-delete/confirm-delete.component";
import {ChooseCandidateDialogComponent} from "./choose-candidate-dialog/choose-candidate-dialog.component";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements AfterViewInit {

  displayedColumns: string[] = ['date', 'title', 'location', 'clicks', 'statistics','applications', 'edit', 'archive'];
  user: UserModel;
  positions: PositionModel[] = [];
  datasource = new MatTableDataSource();
  loading = true;

  private paginator: MatPaginator;
  private sort: MatSort;

  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    this.setDataSourceAttributes();
  }

  setDataSourceAttributes() {
    this.datasource.paginator = this.paginator;
    this.datasource.sort = this.sort;
  }

  constructor(private userService: UserService, private positionService: PositionService,
              private router : Router, private dialog: MatDialog, private _snackBar: MatSnackBar) {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.positionService.getCompanyPositions(this.user.companyId).subscribe(data => {
      for(let position of data) {
        if(!position.archive) {
          this.positions.push(position);
        }
      }
      this.datasource.data = this.positions;
      this.loading = false;
    });
  }

  ngAfterViewInit() {
  }

  onStats(positionId: number): void {
    this.router.navigateByUrl('/stats/' + positionId)
  }

  onApplications(positionId: number): void {
    this.router.navigateByUrl('/applications/' + positionId);
  }

  onArchive(position: PositionModel): void {
    const deleteDialog =
      this.dialog.open(ConfirmDeleteComponent, {
        width: '500px'
      });
    deleteDialog.afterClosed().subscribe(result => {
      if(result === 'yes') {
        const chooseCandidate = this.dialog.open(ChooseCandidateDialogComponent, {
          width: '500px',
          data: position
        });

        chooseCandidate.afterClosed().subscribe(result => {
          if(result === 'done') {
            this.archivePosition(position);
          }
        });
      }
       if(result === 'no')
        this.archivePosition(position);
    });
  }

  archivePosition(position: PositionModel) {
    position.archive = true;
    position.priority = false;
    this.positionService.updatePosition(position).subscribe(data => {
      this._snackBar.open('Archived successfully', 'Close', {
        duration: 5000
      });
    });
  }

  onEdit(position: PositionModel): void {
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

}
