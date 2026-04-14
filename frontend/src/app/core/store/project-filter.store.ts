import { Injectable, signal } from '@angular/core';
import { ProjectFilterDto } from '../../features/dashboard/projects/generated/employee';

@Injectable({ providedIn: 'root' })
export class ProjectFilterStore {

  filters = signal<ProjectFilterDto>({
    searchQuery: '',
    statuses: [],
    technologies: [],
    priorities: [],
    sortBy: 'startDate',
    sortDirection: 'desc'
  });

}