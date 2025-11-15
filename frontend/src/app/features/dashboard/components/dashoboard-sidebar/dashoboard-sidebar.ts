import { AuthService } from './../../../../core/services/auth.service';
import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import {
  LucideAngularModule,
  Worm,
  Menu,
  LogOut,
} from 'lucide-angular';
import { MENU_ITEMS } from '../../../../shared/config/menu.config';

@Component({
  selector: 'app-dashboard-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, LucideAngularModule],
  templateUrl: './dashoboard-sidebar.html'
})
export class DashboardSidebar {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  isMobileMenuOpen = false;

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  logout() {
    this.auth.logout();
  }

  menuItems = MENU_ITEMS;

  readonly Menu = Menu;
  readonly LogOut = LogOut;
  readonly Worm = Worm;
}
