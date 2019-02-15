export class RouteCanceled {

  private _route_uuid: string;
  private _time: Date;

  constructor(route_uuid: string, time: Date) {
    this._route_uuid = route_uuid;
    this._time = time;
  }

  get route_uuid(): string {
    return this._route_uuid;
  }

  set route_uuid(value: string) {
    this._route_uuid = value;
  }

  get time(): Date {
    return this._time;
  }

  set time(value: Date) {
    this._time = value;
  }
}
