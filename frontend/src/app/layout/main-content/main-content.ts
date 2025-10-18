import { Component } from '@angular/core';
import { Filters } from '../../features/main-page/feature-filters/filters';
import { ProjectList } from '../../features/main-page/project-list/project-list';
import { Map } from '../../features/main-page/feature-map/map';


@Component({
  selector: 'app-main-content',
  imports: [Filters, ProjectList, Map],
  templateUrl: './main-content.html'
})
export class MainContent {

}
