import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PositionModel} from "../../models/position.model";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {SkillModel} from "../../models/skill.model";
import {MatAutocomplete, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatChipInputEvent} from "@angular/material/chips";
import {map} from "rxjs/operators";
import {MatSelectChange} from "@angular/material/select";

@Component({
  selector: 'app-edit-job-dialog',
  templateUrl: './edit-job-dialog.component.html',
  styleUrls: ['./edit-job-dialog.component.css']
})
export class EditJobDialogComponent implements OnInit {
  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  skillsCtrl = new FormControl();
  filteredSkills: Observable<SkillModel[]>;
  skills: SkillModel[] = [];
  allSkills: SkillModel[] = [];

  @ViewChild('skillInput') skillInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(public editJob: MatDialogRef<EditJobDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public position: PositionModel) {
    this.filteredSkills = this.skillsCtrl.valueChanges.pipe(
      map((skill: string | null) => skill ? this._filter(skill) : this.allSkills.slice()));
    this.skills = this.position.requirements;
  }

  ngOnInit(): void {
  }

  selectedType(event: MatSelectChange) {
    this.position.priority = event.value === 'P';
  }

  onNoClick(): void {
    this.editJob.close();
  }

  onSave(): void {
    this.editJob.close(this.position);
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
