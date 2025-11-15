import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarIcons } from '../sidebar-icons/sidebar-icons';
import { Navbar } from '../navbar/navbar';

@Component({
  selector: 'app-home-layout',
  standalone: true,
  imports: [RouterOutlet, SidebarIcons, Navbar],
  templateUrl: './home-layout.html',
})
export class HomeLayout {

}
