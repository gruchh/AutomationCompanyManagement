import { effect, inject, signal } from "@angular/core";
import { ProjectFilterDto } from "../../../dashboard/projects/generated/employee/model/project-filter-dto";
import { ProjectCardDto, ProjectManagementApi } from "../../../dashboard/projects/generated/employee";
import { ProjectFilterStore } from "../../../../core/store/project-filter.store";

export class ProjectList {
  private projectApi = inject(ProjectManagementApi);
  private filterStore = inject(ProjectFilterStore);

  public projects = signal<ProjectCardDto[]>([]);
  public loading = signal<boolean>(true);
  public error = signal<string | null>(null);

  constructor() {
    effect(() => {
      const filters = this.filterStore.filters();
      this.loadProjects(filters);
    });
  }

  private loadProjects(filter: ProjectFilterDto) {
    this.loading.set(true);
    this.error.set(null);

    this.projectApi.filterPublicProjectCards(filter).subscribe({
      next: (data) => {
        this.projects.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error(err);
        this.error.set('Nie udało się załadować projektów.');
        this.loading.set(false);
      },
    });
  }

  retry() {
    this.loadProjects(this.filterStore.filters());
  }
}