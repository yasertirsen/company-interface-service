import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../service/user.service";
import {Router} from "@angular/router";
import {LocalStorageService} from "ngx-webstorage";
import {PositionModel} from "../models/position.model";
import {UserModel} from "../models/user.model";
import {SkillModel} from "../models/skill.model";
import {PositionService} from "../service/position.service";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {Observable} from "rxjs";
import {MatAutocomplete, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatChipInputEvent} from "@angular/material/chips";
import {map} from "rxjs/operators";
import {MatSelectChange} from "@angular/material/select";

@Component({
  selector: 'app-add-job',
  templateUrl: './add-job.component.html',
  styleUrls: ['./add-job.component.css']
})
export class AddJobComponent implements OnInit {

  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  skillsCtrl = new FormControl();
  filteredSkills: Observable<SkillModel[]>;
  skills: SkillModel[] = [];
  allSkills: SkillModel[] = [];
  isLinear = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  token: string;
  user: UserModel;
  loading = true;
  position: PositionModel = {
    positionId: 0,
    title: '',
    description: '',
    location: '',
    date: null,
    url: null,
    salary: 0,
    clicks: 0,
    priority: null,
    company: null,
    requirements: []
  };

  @ViewChild('skillInput') skillInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(private _formBuilder: FormBuilder,
              private userService: UserService, private positionService: PositionService, private router: Router) {
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.position.company = this.user;
      this.loading = false;
    });
    this.filteredSkills = this.skillsCtrl.valueChanges.pipe(
      map((skill: string | null) => skill ? this._filter(skill) : this.allSkills.slice()));
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
    for(let skill of this.skills) {
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

  selectedType(event: MatSelectChange) {
    this.position.priority = event.value === 'P';
  }

  addSkill(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.skills.push({skillId: null, skillName: value.trim(), industry: null});
    }

    if (input) {
      input.value = '';
    }

    this.skillsCtrl.setValue(null);
  }

  removeSkill(skill: SkillModel): void {
    const index = this.skills.indexOf(skill);

    if (index >= 0) {
      this.skills.splice(index, 1);
    }
  }

  selectedSkill(event: MatAutocompleteSelectedEvent): void {
    this.skills.push({skillId: null, skillName: event.option.viewValue, industry: null});
    this.skillInput.nativeElement.value = '';
    this.skillsCtrl.setValue(null);
  }

  private _filter(value: string): SkillModel[] {
    const filterValue = value.toLowerCase();

    return this.allSkills.filter(skill => skill.skillName.toLowerCase().indexOf(filterValue) === 0);
  }

}
