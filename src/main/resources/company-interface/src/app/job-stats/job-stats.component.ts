import { Component, OnInit } from '@angular/core';
import {PositionService} from "../service/position.service";
import {ActivatedRoute} from "@angular/router";
import {ApplicationModel} from "../models/application.model";
import {StudentService} from "../service/student.service";
import { SingleDataSet, Label, monkeyPatchChartJsLegend, monkeyPatchChartJsTooltip } from 'ng2-charts';
import {ChartOptions, ChartType} from "chart.js";

@Component({
  selector: 'app-job-stats',
  templateUrl: './job-stats.component.html',
  styleUrls: ['./job-stats.component.css']
})
export class JobStatsComponent implements OnInit {
  loading = true;
  applications: ApplicationModel[];
  emails: string[] = []
  // Pie
  public pieChartOptions: ChartOptions = {
    responsive: true
  };
  public genderColors: Array < any > = [{
    backgroundColor: ['#03639e', '#e2599d']
  }];
  public ageColors: Array < any > = [{
    backgroundColor: ['#003f5c', '#374c80', '#7a5195', '#bc5090', '#ef5675', '#ff764a', '#ffa600']
  }];
  public raceColors: Array < any > = [{
    backgroundColor: ['#5c0033', '#8c183b', '#b9393a', '#de6131', '#f78f1e', '#ffc100', '#fff600']
  }];
  public genderLabels: Label[] = ['Male', 'Female'];
  public ageLabels: Label[] = ['17 or younger', '18-20', '21-29', '30-39', '40-49', '50-59', '60 or older'];
  public raceLabels: Label[] = ['White', 'Black or African-American', 'American Indian or Alaskan Native', 'Asian',
  'Native Hawaiian or other Pacific islander', 'From multiple races'];
  public coursesLabels: Label[] = [];
  public genderData: string[] = [];
  public ageData: string[] = [];
  public raceData: string[] = [];
  public coursesData: string[] = [];
  public pieChartType: ChartType = 'pie';
  public pieChartLegend = true;
  isBelowThreshold = (currentValue) => currentValue === 0;

  constructor(private positionService: PositionService, private activatedRoute: ActivatedRoute, private studentService: StudentService) {
    monkeyPatchChartJsTooltip();
    monkeyPatchChartJsLegend();
    this.positionService.getApplications(this.activatedRoute.snapshot.params.positionId).subscribe(data => {
        this.applications = data;
        for(let application of this.applications) {
          this.emails.push(application.email);
        }
        this.getStats(this.emails)
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }

  getStats(emails: string[]): void {
    this.studentService.getStats(emails).subscribe(data => {
      console.log(data)
        for (let course of Object.keys(data.courses)) {
          this.coursesLabels.push(course);
          this.coursesData.push(data.courses[course]);
        }
      this.genderData.push(data.male);
      this.genderData.push(data.female);
      this.ageData.push(data.ageTier1);
      this.ageData.push(data.ageTier2);
      this.ageData.push(data.ageTier3);
      this.ageData.push(data.ageTier4);
      this.ageData.push(data.ageTier5);
      this.ageData.push(data.ageTier6);
      this.ageData.push(data.ageTier7);
      this.raceData.push(data.white);
      this.raceData.push(data.baa);
      this.raceData.push(data.aian);
      this.raceData.push(data.asian);
      this.raceData.push(data.nhpi);
      this.raceData.push(data.mr);
      this.loading = false;
    },
      error => {
      console.log(error);
      this.loading = false;
      });
  }

  ngOnInit(): void {
  }

}
