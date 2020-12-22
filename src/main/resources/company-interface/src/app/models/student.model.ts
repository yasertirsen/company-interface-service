export interface StudentModel {
  studentId: number;
  firstName: string;
  surname: string;
  password: string;
  email: string;
  username: string;
  phone: string;
  socialUrl: string;
  created: string;
  enabled: boolean;
  role: string;
  authorities: string[];
  isLocked: boolean;
  resumePath: string;
}
