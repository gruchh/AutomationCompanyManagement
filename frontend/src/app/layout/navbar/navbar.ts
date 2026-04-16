import { Component, effect, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import {
  LucideAngularModule,
  LayoutDashboard,
  TowerControl,
  Search,
  ShieldCheck,
} from 'lucide-angular';

import { AuthService } from '../../core/services/auth.service';
import { ProjectFilterStore } from '../../core/store/project-filter.store';
import { ProjectCardDto } from '../../features/dashboard/projects/service/generated';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, LucideAngularModule],
  templateUrl: './navbar.html',
})
export class Navbar {
  private auth = inject(AuthService);
  private filterStore = inject(ProjectFilterStore);

  user = this.auth.user;
  isLoggedIn = this.auth.isLoggedIn;

  readonly LayoutDashboard = LayoutDashboard;
  readonly TowerControl = TowerControl;
  readonly Search = Search;
  readonly ShieldCheck = ShieldCheck;

  readonly Tech = ProjectCardDto.TechnologiesEnum;

  constructor() {
    effect(() => {
      if (this.isLoggedIn()) {
        this.auth.loadUserProfile();
      }
    });
  }

  login() {
    this.auth.login();
  }

  logout() {
    this.auth.logout();
  }

  onSearch(event: Event) {
    const value = (event.target as HTMLInputElement).value;

    this.filterStore.filters.update((f) => ({
      ...f,
      searchQuery: value,
    }));
  }

filterByTechnology(tech: ProjectCardDto.TechnologiesEnum) {
  this.filterStore.filters.update((f) => ({
    ...f,
    technologies: [tech],
  }));
}

  performSearch() {}
}