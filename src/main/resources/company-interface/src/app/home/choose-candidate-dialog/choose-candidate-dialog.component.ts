import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PositionModel} from "../../model/position.model";
import {PositionService} from "../../service/position.service";
import {ApplicationModel} from "../../model/application.model";
import {StudentModel} from "../../model/student.model";
import {StudentService} from "../../service/student.service";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-choose-candidate-dialog',
  templateUrl: './choose-candidate-dialog.component.html',
  styleUrls: ['./choose-candidate-dialog.component.css']
})
export class ChooseCandidateDialogComponent implements OnInit {
  applications: ApplicationModel[] = [];
  candidates: StudentModel[] = [];
  selectedCandidate: number;

  constructor(public candidate: MatDialogRef<ChooseCandidateDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public position: PositionModel,
              private positionService: PositionService,
              private studentService: StudentService,
              private userService: UserService) {
    this.positionService.getApplications(this.position.positionId).subscribe(data => {
        this.applications = data;
        for(let application of this.applications) {
          this.studentService.getStudent(application.email).subscribe(data => {
            this.candidates.push(data);
          }, error => {
            console.log(error);
          });
        }
      });
  }

  onDone() {
    if(this.selectedCandidate !== undefined) {
      let user = JSON.parse(localStorage.getItem('currentUser'));
      user.profile.hiredStudents.push(this.selectedCandidate);
      this.userService.updateUser(user).subscribe(data => {
        console.log(data);
      });
    }
    this.candidate.close('done');
  }

  ngOnInit(): void {
  }

}
