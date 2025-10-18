import { Component } from '@angular/core';
import { Navbar } from './layout/navbar/navbar';
import { SidebarIcons } from './layout/sidebar-icons/sidebar-icons';
import { MainContent } from './layout/main-content/main-content';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Navbar, SidebarIcons, MainContent],
  templateUrl: './app.html',
})
export class App {



}
