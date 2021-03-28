import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {UserModel} from "../model/user.model";
import {PositionModel} from "../model/position.model";
import {UserService} from "../service/user.service";
import {PositionService} from "../service/position.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-archived-jobs',
  templateUrl: './archived-jobs.component.html',
  styleUrls: ['./archived-jobs.component.css']
})
export class ArchivedJobsComponent implements AfterViewInit {
  displayedColumns: string[] = ['date', 'title', 'location', 'clicks', 'statistics','applications'];
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
        if(position.archive) {
          this.positions.push(position);
        }
      }
      this.datasource.data = this.positions;
      this.loading = false;
    });
  }

  onStats(positionId: number): void {
    this.router.navigateByUrl('/stats/' + positionId)
  }

  onApplications(positionId: number): void {
    this.router.navigateByUrl('/applications/' + positionId);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }

  ngAfterViewInit(): void {
  }

}
