import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DashboardSidebar } from '../../features/dashboard/components/dashoboard-sidebar/dashoboard-sidebar';

@Component({
  selector: 'app-dashboard-layout',
  imports: [RouterOutlet, DashboardSidebar],
  templateUrl: './dashboard-layout.html',
})
export class DashboardLayout {

}
