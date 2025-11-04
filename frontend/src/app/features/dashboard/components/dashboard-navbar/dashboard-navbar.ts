import { Component, input, output } from '@angular/core';
import { LucideAngularModule, Search, Bell, Menu } from 'lucide-angular';

@Component({
  selector: 'app-dashboard-navbar',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './dashboard-navbar.html',
})
export class DashboardNavbar {
  isMenuOpen = input.required<boolean>();
  isMenuOpenChange = output<boolean>();

  toggleMenu() {
    this.isMenuOpenChange.emit(!this.isMenuOpen());
  }

  closeMenu() {
    this.isMenuOpenChange.emit(false);
  }

  readonly Search = Search;
  readonly Bell = Bell;
  readonly Menu = Menu;
}
