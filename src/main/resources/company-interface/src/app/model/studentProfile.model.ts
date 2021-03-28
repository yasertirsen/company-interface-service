import {SkillModel} from "./skill.model";
import {CourseModel} from "./course.model";
import {ProjectModel} from "./project.model";
import {ExperienceModel} from "./experience.model";

export interface StudentProfileModel {
  profileId: number;
  bio: string;
  course: CourseModel;
  externalSkills: SkillModel[];
  projects: ProjectModel[];
  experiences: ExperienceModel[];
  averageGrade: number;
  startCourse: string;
  endCourse: string;
  gender: string;
  race: string;
  age: string;
}
