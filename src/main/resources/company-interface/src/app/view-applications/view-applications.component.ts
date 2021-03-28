import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PositionService} from "../service/position.service";
import {ApplicationModel} from "../model/application.model";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-view-applications',
  templateUrl: './view-applications.component.html',
  styleUrls: ['./view-applications.component.css']
})
export class ViewApplicationsComponent implements OnInit {
  loading = true;
  applications: ApplicationModel[];
  datasource: any;
  displayedColumns: string[] = ['name', 'email', 'cv'];

  constructor(private positionService: PositionService, private activatedRoute: ActivatedRoute) {
    this.positionService.getApplications(this.activatedRoute.snapshot.params.positionId).subscribe(data => {
      this.applications = data;
      this.datasource = new MatTableDataSource(data);
      this.loading = false;
    },
      error => {
      console.log(error);
        this.loading = false;
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }

  onCv(applicationId: number):void {
    this.positionService.getCv(applicationId).subscribe(data => {
      const fileURL = URL.createObjectURL(data);
      window.open(fileURL, '_blank');
    },
      error => {
      console.log(error)
      });

  }

  ngOnInit(): void {
  }
}
