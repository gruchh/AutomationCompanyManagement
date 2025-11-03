import { Component } from '@angular/core';
import { LucideAngularModule, Search, Bell } from 'lucide-angular';

@Component({
  selector: 'app-dashboard-navbar',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './dashboard-navbar.html',
})
export class DashboardNavbar {

  readonly Search = Search;
  readonly Bell = Bell;

}
