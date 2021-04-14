import {SkillModel} from "./skill.model";
import {UserModel} from "./user.model";

export class PositionModel {
  positionId: number;
  title: string;
  description: string;
  location: string;
  date: string;
  salary: number;
  url: string;
  clicks: number;
  priority: boolean;
  archive: boolean;
  company: UserModel;
  requirements: SkillModel[];
  notifications: number;
}
