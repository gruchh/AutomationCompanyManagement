import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import {
  LucideAngularModule,
  LayoutDashboard,
  Calendar,
  Inbox,
  Settings,
  Menu,
  LogOut,
} from 'lucide-angular';

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

  menuItems = [
    { icon: LayoutDashboard, label: 'Dashboard', route: '/dashboard' },
    { icon: Inbox, label: 'Messages', route: '/dashboard/messages' },
    { icon: Calendar, label: 'Schedule', route: '/dashboard/schedule' },
    { icon: Settings, label: 'Settings', route: '/dashboard/settings' },
  ];

  readonly Menu = Menu;
  readonly LogOut = LogOut;
}
