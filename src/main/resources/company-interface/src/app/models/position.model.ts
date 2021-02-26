import {SkillModel} from "./skill.model";
import {UserModel} from "./user.model";

export interface PositionModel {
  positionId: number;
  title: string;
  description: string;
  location: string;
  date: string;
  salary: number;
  url: string;
  clicks: number;
  company: UserModel;
  requirements: SkillModel[];
}
