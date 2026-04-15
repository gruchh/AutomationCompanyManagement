import { Component, effect, inject, signal } from "@angular/core";
import { ProjectFilterStore } from "../../../../core/store/project-filter.store";

import {
  ProjectCardDto,
  ProjectFilterDto,
  ProjectControllerApi
} from "../../../dashboard/projects/service/generated";
import { ProjectCard } from "../project-card/project-card";

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [ProjectCard],
  templateUrl: './project-list.html',
})
export class ProjectList {

  private projectApi = inject(ProjectControllerApi);
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

    this.projectApi.getProjectCards(filter)
      .subscribe({
        next: (data: ProjectCardDto[]) => {
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