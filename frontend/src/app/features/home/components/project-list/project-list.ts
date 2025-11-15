import { Component, signal, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectCard } from '../project-card/project-card';
import { Project } from '../../models/project.model';
import { ProjectManagementApi } from '../../../dashboard/projects/generated/employee/api/project-management.service';
import { ProjectFilterDto } from '../../../dashboard/projects/generated/employee/model/project-filter-dto';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [ProjectCard, CommonModule],
  templateUrl: './project-list.html'
})
export class ProjectList implements OnInit {
  private projectApi = inject(ProjectManagementApi);

  public projects = signal<Project[]>([]);
  public loading = signal<boolean>(true);
  public error = signal<string | null>(null);

  ngOnInit() {
    this.loadProjects();
  }

  private loadProjects() {
    this.loading.set(true);
    this.error.set(null);

    const filter: ProjectFilterDto = {
      sortBy: 'startDate',
      sortDirection: 'desc'
    };

    this.projectApi.filterPublicProjectCards(filter).subscribe({
      next: (data) => {
        this.projects.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading projects:', err);
        this.error.set('Nie udało się załadować projektów. Spróbuj ponownie później.');
        this.loading.set(false);
      }
    });
  }

  public retry() {
    this.loadProjects();
  }
}
