import {UserModel} from "./user.model";
import {SkillModel} from "./skill.model";

export interface PositionModel {
  positionId: number;
  title: string;
  description: string;
  location: string;
  date: string;
  salary: number;
  clicks: number;
  company: UserModel;
  requirements: SkillModel[];
}
