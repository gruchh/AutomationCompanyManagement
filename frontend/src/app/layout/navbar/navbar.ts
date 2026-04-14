import { Component, effect, inject } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { RouterLink } from '@angular/router';
import {
  LucideAngularModule,
  LayoutDashboard,
  TowerControl,
  Search,
  ShieldCheck,
} from 'lucide-angular';
import { ProjectFilterStore } from '../../core/store/project-filter.store';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, LucideAngularModule],
  templateUrl: './navbar.html',
})
export class Navbar {
  performSearch() {
    throw new Error('Method not implemented.');
  }

  filterByTechnology(tech: string) {
    this.filterStore.filters.update((f) => ({
      ...f,
      technologies: [tech],
    }));
  }

  onSearch(event: Event) {
    const value = (event.target as HTMLInputElement).value;

    this.filterStore.filters.update((f) => ({
      ...f,
      searchQuery: value,
    }));
  }

  private auth = inject(AuthService);
  private filterStore = inject(ProjectFilterStore);

  user = this.auth.user;
  isLoggedIn = this.auth.isLoggedIn;

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

  readonly LayoutDashboard = LayoutDashboard;
  readonly TowerControl = TowerControl;
  readonly Search = Search;
  readonly ShieldCheck = ShieldCheck;
}
