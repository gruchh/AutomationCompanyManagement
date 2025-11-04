import { Component, effect, inject } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { RouterLink } from '@angular/router';
import { LucideAngularModule, LayoutDashboard, TowerControl, Search, ShieldCheck } from 'lucide-angular';

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
  filterByTechnology(arg0: string) {
    throw new Error('Method not implemented.');
  }
  onSearch($event: Event) {
    throw new Error('Method not implemented.');
  }
  private auth = inject(AuthService);

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
