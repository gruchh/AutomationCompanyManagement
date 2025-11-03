import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LucideAngularModule, LayoutDashboard, Calendar, Inbox, Settings, Menu } from 'lucide-angular';

@Component({
  selector: 'app-dashboard-sidebar',
  standalone: true,
  imports: [RouterLink, LucideAngularModule],
  templateUrl: './dashoboard-sidebar.html',
})
export class DashboardSidebar {
  isMobileMenuOpen = false;

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  menuItems = [
    { icon: LayoutDashboard, label: 'Home', route: '/dashboard' },
    { icon: Inbox, label: 'Inbox', route: '/dashboard/inbox' },
    { icon: Calendar, label: 'Calendar', route: '/dashboard/calendar' },
    { icon: Settings, label: 'Settings', route: '/dashboard/settings' },
  ];

  readonly Menu = Menu;
}
