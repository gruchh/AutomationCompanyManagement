import { Component } from '@angular/core';
import { Filters } from './components/feature-filters/filters';
import { ProjectList } from './components/project-list/project-list';
import { MapComponent } from './components/feature-map/map';

@Component({
  selector: 'app-mainpage',
  standalone: true,
  imports: [Filters, ProjectList, MapComponent],
  templateUrl: './home.html',
})
export class Home {}