import { Component, AfterViewInit, inject } from '@angular/core';
import * as L from 'leaflet';
import { finalize } from 'rxjs/operators';
import { ProjectManagementApi } from '../../../dashboard/projects/generated/employee/api/project-management.service';
import {
  ProjectMapPointDto,
  ProjectMapPointDtoStatusEnum,
} from '../../../dashboard/projects/generated/employee';

@Component({
  selector: 'app-map',
  templateUrl: './map.html',
})
export class Map implements AfterViewInit {
  private readonly ProjectManagementApi = inject(ProjectManagementApi);
  private map: L.Map | undefined;

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.initMap();
    }, 100);
  }

  private initMap(): void {
    this.map = L.map('map', {
      zoomControl: true,
      attributionControl: true,
    }).setView([52.0, 19.5], 6);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors',
      maxZoom: 19,
    }).addTo(this.map);

    const iconUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png';
    const iconRetinaUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png';
    const shadowUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png';

    const iconDefault = L.icon({
      iconRetinaUrl,
      iconUrl,
      shadowUrl,
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      tooltipAnchor: [16, -28],
      shadowSize: [41, 41],
    });
    L.Marker.prototype.options.icon = iconDefault;

    this.loadProjectMarkers();
  }

  private loadProjectMarkers(): void {
    this.ProjectManagementApi.getProjectsMapData([
      ProjectMapPointDtoStatusEnum.PLANNING,
      ProjectMapPointDtoStatusEnum.IN_PROGRESS,
      ProjectMapPointDtoStatusEnum.ON_HOLD,
      ProjectMapPointDtoStatusEnum.COMPLETED,
      ProjectMapPointDtoStatusEnum.CANCELLED,
    ])
      .pipe(finalize(() => console.log('Projekty załadowane')))
      .subscribe({
        next: (projects: ProjectMapPointDto[]) => {
          console.log('Otrzymane projekty z API:', projects);
          this.addProjectMarkers(projects);
        },
        error: (err: any) => console.error('Błąd ładowania projektów:', err),
      });
  }

  private addProjectMarkers(projects: ProjectMapPointDto[]): void {
    if (!this.map) return;

    projects.forEach((project) => {
      if (project.latitude && project.longitude) {
        const marker = L.marker([project.latitude, project.longitude]).addTo(this.map!);
        marker.bindPopup(`
          <div class="p-2">
            <h3 class="text-lg font-bold mb-1">${project.name || 'Projekt'}</h3>
            <p class="text-gray-600 text-sm">Kod: ${project.code || '-'}</p>
            <p class="text-gray-600 text-sm">Status: ${project.status}</p>
            <p class="text-gray-600 text-sm">Lokalizacja: ${project.location || '-'}</p>
          </div>
        `);
      }
    });
  }
}
