import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../service/user.service";
import {Router} from "@angular/router";
import {PositionModel} from "../model/position.model";
import {UserModel} from "../model/user.model";
import {SkillModel} from "../model/skill.model";
import {PositionService} from "../service/position.service";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {Observable} from "rxjs";
import {MatAutocomplete, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatChipInputEvent} from "@angular/material/chips";
import {map} from "rxjs/operators";
import {MatSelectChange} from "@angular/material/select";
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-add-job',
  templateUrl: './add-job.component.html',
  styleUrls: ['./add-job.component.css']
})
export class AddJobComponent implements OnInit {

  public editor = ClassicEditor;
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
  position = new PositionModel();

  @ViewChild('skillInput') skillInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(private _formBuilder: FormBuilder,
              private userService: UserService, private positionService: PositionService, private router: Router,
              private _snackBar: MatSnackBar) {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.position.company = this.user;
    this.loading = false;
    this.filteredSkills = this.skillsCtrl.valueChanges.pipe(
      map((skill: string | null) => skill ? this._filter(skill) : this.allSkills.slice()));
  }

  ngOnInit(): void {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
      url: ['', Validators.nullValidator(null)],
      salary: ['', Validators.nullValidator(null)]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

  onDone(): void {
    this.position.requirements = this.skills;
    this.addJob();
  }

  addJob(): void {
    this.positionService.addPosition(this.position).subscribe(position => {
      this._snackBar.open('Job added successfully', 'Close', {duration: 3000});
      this.router.navigateByUrl('/home');
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
