export interface Project {
  id?: number;
  name?: string;
  code?: string;
  shortDescription?: string;
  companyName?: string;
  location?: string;
  status?: 'PLANNING' | 'IN_PROGRESS' | 'ON_HOLD' | 'COMPLETED' | 'CANCELLED';
  teamSize?: number;
  technologies?: string[];
  startDate?: string;
}
