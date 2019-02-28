import {Component, OnInit} from '@angular/core';
import {tileLayer, latLng, Layer, marker, icon, Map, circle} from 'leaflet';
import {OpenstreetmapService} from '../../../services/openstreetmap.service';
import {DashboardMarketingService} from '../../../services/dashboard-marketing.service';
import {CityReport} from '../../../model/city-report';
import {City} from '../../../model/city';

@Component({
  selector: 'ngx-heatmap',
  templateUrl: './heatmap.component.html',
  styleUrls: ['./heatmap.component.scss'],
})
export class HeatmapComponent implements OnInit {

  layer = tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {maxZoom: 18, attribution: '...'});

  options = {
    layers: [
      this.layer,
    ],
    zoom: 8,
    center: latLng({lat: 43.700000, lng: 7.100000}),
  };

  markers: Layer[] = [];

  constructor(private openStreetMapService: OpenstreetmapService, private marketingService: DashboardMarketingService) {
  }

  ngOnInit() {
    const parentThis = this;
    this.marketingService.getMostActiveCities().subscribe(cities => cities.forEach(function (cityReport) {
      parentThis.addHeatmapForCity(cityReport);
    }));
    this.marketingService.getActiveCities().subscribe(cities => cities.forEach(function (city) {
      parentThis.addMarkerForCity(city);
    }));
    setInterval(function () {// TODO: test auto-refresh
      parentThis.markers.length = 0;
      parentThis.marketingService.getMostActiveCities().subscribe(cities => cities.forEach(function (cityReport) {
        parentThis.addHeatmapForCity(cityReport);
      }));
      parentThis.marketingService.getActiveCities().subscribe(cities => cities.forEach(function (city) {
        parentThis.addMarkerForCity(city);
      }));
    }, 3000);
  }

  addMarkerForCity(city: City) {
    const parentThis = this;
    this.openStreetMapService.getGPSCoordinates(city.name).subscribe(cityInfo => {
      const cityFinalInfo = cityInfo[0]; // sale
      const cityMarker = marker(
        [cityFinalInfo.lat, cityFinalInfo.lon],
        {
          icon: icon({
            iconSize: [25, 25],
            iconAnchor: [25, 25],
            iconUrl: 'assets/marker/marker.png',
            zIndexOffset: 1000,
          }),
        },
      );
      parentThis.markers.push(cityMarker);
    });
  }

  addHeatmapForCity(cityReport: CityReport) {
    console.log(cityReport);
    const parentThis = this;
    this.openStreetMapService.getGPSCoordinates(cityReport.city.name).subscribe(cityInfo => {
      const cityFinalInfo = cityInfo[0]; // sale
      const cityCircle = circle([cityFinalInfo.lat, cityFinalInfo.lon], {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: cityReport.transactionCount * 5,
      });
      parentThis.markers.push(cityCircle);
    });
  }

}
