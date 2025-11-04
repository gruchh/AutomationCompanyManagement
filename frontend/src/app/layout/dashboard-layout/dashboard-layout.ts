import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { DashboardSidebar } from '../../features/dashboard/components/dashoboard-sidebar/dashoboard-sidebar';
import { DashboardNavbar } from '../../features/dashboard/components/dashboard-navbar/dashboard-navbar';
import { LucideAngularModule, LogOut } from 'lucide-angular';
import { MENU_ITEMS } from '../../shared/config/menu.config';

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, DashboardSidebar, DashboardNavbar, LucideAngularModule],
  templateUrl: './dashboard-layout.html',
})
export class DashboardLayout {
  isMobileMenuOpen = signal(false);
  menuItems = MENU_ITEMS;

  readonly LogOut = LogOut;
}
