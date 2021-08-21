import dayjs from 'dayjs';
import { Placement } from 'app/shared/model/enumerations/placement.model';
import { Stage } from 'app/shared/model/enumerations/stage.model';

export interface ICompany {
  id?: number;
  companyName?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  companyDetails?: string | null;
  placementType?: Placement | null;
  salaryPackage?: string | null;
  stage?: Stage | null;
}

export const defaultValue: Readonly<ICompany> = {};
