import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../service/user.service";
import {Router} from "@angular/router";
import {LocalStorageService} from "ngx-webstorage";
import {PositionModel} from "../models/position.model";
import {UserModel} from "../models/user.model";
import {SkillModel} from "../models/skill.model";
import {PositionService} from "../service/position.service";

@Component({
  selector: 'app-add-job',
  templateUrl: './add-job.component.html',
  styleUrls: ['./add-job.component.css']
})
export class AddJobComponent implements OnInit {

  isLinear = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  token: string;
  user: UserModel;
  requirementsText: string;
  requirementsNames: string[];
  position: PositionModel = {
    positionId: 0,
    title: '',
    description: '',
    location: '',
    date: null,
    salary: 0,
    clicks: 0,
    company: null,
    requirements: []
  };

  constructor(private _formBuilder: FormBuilder, private localStorage: LocalStorageService,
              private userService: UserService, private positionService: PositionService, private router: Router) {
    this.token = this.localStorage.retrieve('token');
    this.userService.getCurrentUser(this.token).subscribe(user => {
      this.user = user;
      this.position.company = this.user;
    });
  }

  ngOnInit(): void {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

  onDone(): void {
    this.requirementsNames = this.requirementsText.split(',');
    for(let i in this.requirementsNames) {
      let skill: SkillModel = {skillId: null, skillName: null, industry: null};
      skill.skillName = this.requirementsNames[i].replace(' ', '');
      this.position.requirements.push(skill);
    }
    this.addJob();
  }

  addJob(): void {
    this.positionService.addPosition(this.position).subscribe(position => {
      this.router.navigateByUrl('/home',
        {queryParams: {completed: 'true'}});
    });
  }

}
