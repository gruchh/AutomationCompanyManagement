import { AfterViewInit, Component, inject } from '@angular/core';
import * as L from 'leaflet';
import { finalize } from 'rxjs/operators';
import {
  ProjectCardDto,
  ProjectControllerApi,
  ProjectFilterDto,
} from '../../../dashboard/projects/service/generated';

@Component({
  selector: 'app-map',
  standalone: true,
  templateUrl: './map.html',
})
export class MapComponent implements AfterViewInit {
  private api = inject(ProjectControllerApi);
  private map?: L.Map;

  ngAfterViewInit(): void {
    this.initMap();
  }

  private initMap(): void {
    this.map = L.map('map').setView([52.0, 19.5], 6);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap',
    }).addTo(this.map);

    this.loadMarkers();
  }
  
  private loadMarkers(): void {
    const filter: ProjectFilterDto = {
      sortBy: ProjectFilterDto.SortByEnum.StartDate,
      sortDirection: ProjectFilterDto.SortDirectionEnum.Desc,
    };

    this.api
      .searchProjectCards({ projectFilterDto: filter }) // ← opakuj w obiekt
      .pipe(finalize(() => console.log('Map loaded')))
      .subscribe({
        next: (projects: ProjectCardDto[]) => {
          const withLocation = projects.filter((p) => p.latitude != null && p.longitude != null);
          this.renderMarkers(withLocation);
        },
        error: (err) => console.error('Map error:', err),
      });
  }

  private renderMarkers(projects: ProjectCardDto[]): void {
    if (!this.map) return;

    const icon = L.icon({
      iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
      iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
      shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      shadowSize: [41, 41],
    });

    L.Marker.prototype.options.icon = icon;

    projects.forEach((p) => {
      L.marker([p.latitude!, p.longitude!]).addTo(this.map!).bindPopup(`
          <div style="padding:6px">
            <b>${p.name ?? 'Projekt'}</b><br/>
            <span>${p.location ?? '-'}</span><br/>
            <small>${p.status ?? '-'}</small>
          </div>
        `);
    });
  }
}
