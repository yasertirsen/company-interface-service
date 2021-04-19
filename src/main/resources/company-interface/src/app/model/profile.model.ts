import {ReviewModel} from "./review.model";

export interface ProfileModel {
  profileId: number;
  hiredStudents: number[];
  reviews: ReviewModel[];
  bio: string;
}
