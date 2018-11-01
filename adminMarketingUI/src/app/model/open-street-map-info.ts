export class OpenStreetMapInfo {
  private _lat: number;
  private _lon: number;
  private _display_name: string;

  constructor(lat: number, lon: number, display_name: string) {
    this._lat = lat;
    this._lon = lon;
    this._display_name = display_name;
  }

  get lat(): number {
    return this._lat;
  }

  set lat(value: number) {
    this._lat = value;
  }

  get lon(): number {
    return this._lon;
  }

  set lon(value: number) {
    this._lon = value;
  }

  get display_name(): string {
    return this._display_name;
  }

  set display_name(value: string) {
    this._display_name = value;
  }
}
