import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'ngx-heatmap',
  templateUrl: './heatmap.component.html',
  styleUrls: ['./heatmap.component.scss'],
})
export class HeatmapComponent implements OnInit {

  options = {
    layers: [
      L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' }),
    ],
    zoom: 10,
    center: L.latLng({ lat: 43.700000, lng: 7.250000 }),
  };

  constructor() { }

  ngOnInit() {
  }

}
