import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {OpenStreetMapInfo} from '../model/open-street-map-info';

@Injectable({
  providedIn: 'root',
})
export class OpenstreetmapService {

  OPEN_STREET_MAP_URL = 'https://nominatim.openstreetmap.org/search?q=';
  OPEN_STREET_MAP_ARGS = '&format=json&limit=1';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {
  }

  getGPSCoordinates(city: string): Observable<OpenStreetMapInfo[]> {
    return this.http.get<OpenStreetMapInfo[]>(this.OPEN_STREET_MAP_URL + city + this.OPEN_STREET_MAP_ARGS, this.httpOptions);
  }
}
