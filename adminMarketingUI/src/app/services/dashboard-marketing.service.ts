import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CityReport} from '../model/city-report';
import {City} from '../model/city';
import { environment } from '../../environments/environment';
import {DeliveryIssue} from '../model/delivery-issue';
import {RouteCreated} from '../model/route-created';
import {RouteCanceled} from '../model/route-canceled';
import { DeliveredItem } from "../model/delivered-item";

@Injectable({
  providedIn: 'root',
})
export class DashboardMarketingService {

  API_URL = environment.DASHBOARD_API_URL + '/marketing';
  GET_ACTIVE_CITIES = this.API_URL + '/cities';
  GET_MOST_ACTIVE_CITIES = this.API_URL + '/mostActiveCities';
  GET_24H_LAST_DELIVERY_ISSUES = this.API_URL + '/last24hDeliveryIssues';
  GET_24H_LAST_DELIVERED_ITEMS = this.API_URL + '/last24hDeliveredItems';
  GET_24H_LAST_CREATED_ROUTES = this.API_URL + '/last24hCreatedRoutes';
  GET_24H_LAST_CANCELED_ROUTES = this.API_URL + '/last24hCanceledRoutes';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {
  }

  getActiveCities(): Observable<City[]> {
    return this.http.get<City[]>(this.GET_ACTIVE_CITIES, this.httpOptions);
  }

  getMostActiveCities(): Observable<CityReport[]> {
    return this.http.get<CityReport[]>(this.GET_MOST_ACTIVE_CITIES, this.httpOptions);
  }

  getLast24hDeliveryIssues(): Observable<DeliveryIssue[]> {
    return this.http.get<DeliveryIssue[]>(this.GET_24H_LAST_DELIVERY_ISSUES, this.httpOptions);
  }

  getLast24hCreatedRoutes(): Observable<RouteCreated[]> {
    return this.http.get<RouteCreated[]>(this.GET_24H_LAST_CREATED_ROUTES, this.httpOptions);
  }

  getLast24hCanceledRoutes(): Observable<RouteCanceled[]> {
    return this.http.get<RouteCanceled[]>(this.GET_24H_LAST_CANCELED_ROUTES, this.httpOptions);
  }

  getLast24hDeliveredItems(): Observable<DeliveredItem[]> {
    return this.http.get<DeliveredItem[]>(this.GET_24H_LAST_DELIVERED_ITEMS, this.httpOptions);
  }

}
