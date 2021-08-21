import dayjs from 'dayjs';

export interface IRound {
  id?: number;
  startDate?: string | null;
  endDate?: string | null;
  duration?: string | null;
  skillsRequired?: string | null;
  link?: string | null;
}

export const defaultValue: Readonly<IRound> = {};
