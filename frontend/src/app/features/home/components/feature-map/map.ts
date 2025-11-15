import { Component, OnInit, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: `./map.html`,
})
export class Map implements AfterViewInit {
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

    this.addCityMarkers();
  }

  private addCityMarkers(): void {
    const gdansk = L.marker([54.352, 18.6466]).addTo(this.map!);
    gdansk.bindPopup(`
      <div class="p-2">
        <h3 class="text-lg font-bold text-blue-600 mb-1">Gdańsk</h3>
        <p class="text-gray-600 text-sm">Miasto portowe nad Bałtykiem</p>
      </div>
    `);

    const katowice = L.marker([50.2649, 19.0238]).addTo(this.map!);
    katowice.bindPopup(`
      <div class="p-2">
        <h3 class="text-lg font-bold text-blue-600 mb-1">Katowice</h3>
        <p class="text-gray-600 text-sm">Stolica Górnego Śląska</p>
      </div>
    `);

    const warszawa = L.marker([52.2297, 21.0122]).addTo(this.map!);
    warszawa.bindPopup(`
      <div class="p-2">
        <h3 class="text-lg font-bold text-red-600 mb-1">Warszawa</h3>
        <p class="text-gray-600 text-sm">Stolica Polski</p>
      </div>
    `);

    const krakow = L.marker([50.0647, 19.945]).addTo(this.map!);
    krakow.bindPopup(`
      <div class="p-2">
        <h3 class="text-lg font-bold text-blue-600 mb-1">Kraków</h3>
        <p class="text-gray-600 text-sm">Dawna stolica Polski</p>
      </div>
    `);

    const wroclaw = L.marker([51.1079, 17.0385]).addTo(this.map!);
    wroclaw.bindPopup(`
      <div class="p-2">
        <h3 class="text-lg font-bold text-blue-600 mb-1">Wrocław</h3>
        <p class="text-gray-600 text-sm">Stolica Dolnego Śląska</p>
      </div>
    `);
  }
}
