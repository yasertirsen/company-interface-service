import {StudentModel} from "./student.model";
import {PositionModel} from "./position.model";

export interface ProfileModel {
  profileId: number;
  hiredStudents: StudentModel[];
  positions: PositionModel[];
}
