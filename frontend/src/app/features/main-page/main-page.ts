import { Component } from '@angular/core';
import { SidebarIcons } from '../../layout/sidebar-icons/sidebar-icons';
import { MainContent } from '../../layout/main-content/main-content';

@Component({
  selector: 'app-mainpage',
  standalone: true,
  imports: [SidebarIcons, MainContent],
  templateUrl: './main-page.html',
})
export class Mainpage {

}
