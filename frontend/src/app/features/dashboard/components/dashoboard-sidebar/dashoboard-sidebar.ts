import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
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
  isMobileMenuOpen = false;

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  menuItems = MENU_ITEMS;

  readonly Menu = Menu;
  readonly LogOut = LogOut;
  readonly Worm = Worm;
}
