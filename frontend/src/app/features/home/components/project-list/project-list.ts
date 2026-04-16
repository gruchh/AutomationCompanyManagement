import { Component, effect, inject, signal } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import { ProjectFilterStore } from '../../../../core/store/project-filter.store';
import {
  ProjectCardDto,
  ProjectFilterDto,
  ProjectControllerApi,
} from '../../../dashboard/projects/service/generated';
import { ProjectCard } from '../project-card/project-card';

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

  private filter$ = new Subject<ProjectFilterDto>();

  constructor() {
    this.filter$
      .pipe(
        debounceTime(300),
        switchMap((filter) =>
          this.projectApi.searchProjectCards(
            { projectFilterDto: filter },
            undefined,
            undefined,
            { httpHeaderAccept: 'application/json' }
          )
        )
      )
      .subscribe({
        next: (projects: ProjectCardDto[]) => {
          this.projects.set(projects ?? []);
          this.loading.set(false);
        },
        error: (err) => {
          console.error(err);
          this.error.set('Nie udało się załadować projektów.');
          this.loading.set(false);
        },
      });

    effect(() => {
      this.loading.set(true);
      this.error.set(null);
      this.filter$.next(this.cleanFilter(this.filterStore.filters()));
    });
  }

  public retry(): void {
    this.filter$.next(this.filterStore.filters());
  }

  private cleanFilter(filter: ProjectFilterDto): ProjectFilterDto {
    return {
      ...filter,
      statuses: filter.statuses?.length ? filter.statuses : undefined,
      technologies: filter.technologies?.length ? filter.technologies : undefined,
      priorities: filter.priorities?.length ? filter.priorities : undefined,
      searchQuery: filter.searchQuery || undefined,
    };
  }
}