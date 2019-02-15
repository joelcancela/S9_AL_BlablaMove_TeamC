export class RouteCreated {

  private _route_uuid: string;
  private _initialCity: string;
  private _endCity: string;
  private _time: Date;

  constructor(endCity: string, route_uuid: string, initialCity: string, time: Date) {
    this._initialCity = initialCity;
    this._endCity = endCity;
    this._route_uuid = route_uuid;
    this._time = time;
  }

  get route_uuid(): string {
    return this._route_uuid;
  }

  set route_uuid(value: string) {
    this._route_uuid = value;
  }

  get initialCity(): string {
    return this._initialCity;
  }

  set initialCity(value: string) {
    this._initialCity = value;
  }

  get endCity(): string {
    return this._endCity;
  }

  set endCity(value: string) {
    this._endCity = value;
  }

  get time(): Date {
    return this._time;
  }

  set time(value: Date) {
    this._time = value;
  }

}
