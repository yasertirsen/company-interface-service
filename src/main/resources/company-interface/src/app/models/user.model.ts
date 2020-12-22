import {ProfileModel} from "./profile.model";

export interface UserModel {
  companyId: number;
  email: string;
  password: string;
  name: string;
  companyUrl: string;
  address: string;
  recruiter: string;
  recruiterPhone: string;
  created: string;
  role: string;
  authorities: string[];
  isLocked: boolean;
  token: string;
  enabled: boolean;
  profile: ProfileModel;
}
