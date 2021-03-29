import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StudentModel} from "../model/student.model";
import {StudentService} from "../service/student.service";

@Component({
  selector: 'app-alumni-profile',
  templateUrl: './alumni-profile.component.html',
  styleUrls: ['./alumni-profile.component.css']
})
export class AlumniProfileComponent implements OnInit {
  loading = true;
  user: StudentModel;

  constructor(private studentService: StudentService, private activatedRoute: ActivatedRoute) {
    this.studentService.getStudent(this.activatedRoute.snapshot.params.email).subscribe(data => {
      this.user = data;
        this.loading = false;
    });
  }

  ngOnInit(): void {
  }

}
