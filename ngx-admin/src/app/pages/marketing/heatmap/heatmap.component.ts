import { Component, OnInit } from '@angular/core';
import {tileLayer, latLng, Layer, marker, icon, Map} from 'leaflet';

@Component({
  selector: 'ngx-heatmap',
  templateUrl: './heatmap.component.html',
  styleUrls: ['./heatmap.component.scss'],
})
export class HeatmapComponent implements OnInit {

  layer = tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' });

  options = {
    layers: [
      this.layer,
    ],
    zoom: 10,
    center: latLng({ lat: 43.700000, lng: 7.250000 }),
  };

  markers: Layer[] = [];

  markerw = marker(
    [43.700000, 7.250000],
    {
      icon: icon({
        iconSize: [ 25, 41 ],
        iconAnchor: [ 13, 41 ],
        iconUrl: 'assets/marker/marker.png',
      }),
    },
  );

  zoomend(map: Map) {
    const zoom = map.layer._tileZoom;
    this.markerw.iconSize = [0.48 * zoom, 0.48 * zoom];
  }

  addMarker() {
    const newMarker = marker(
      [ 43.700000, 7.250000 ],
      {
        icon: icon({
          iconAnchor: [ 43.700000, 7.250000 ],
          iconUrl: 'assets/marker/marker.png',
        }),
      },
    );
    this.markers.push(newMarker);
  }

  removeMarker() {
    this.markers.pop();
  }

  constructor() { }

  ngOnInit() {
    this.markers.push(this.markerw);
  }

}
