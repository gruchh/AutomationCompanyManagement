import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DashboardSidebar } from '../../features/dashboard/components/dashoboard-sidebar/dashoboard-sidebar';
import { DashboardNavbar } from "../../features/dashboard/components/dashboard-navbar/dashboard-navbar";

@Component({
  selector: 'app-dashboard-layout',
  imports: [RouterOutlet, DashboardSidebar, DashboardNavbar],
  templateUrl: './dashboard-layout.html',
})
export class DashboardLayout {

}
