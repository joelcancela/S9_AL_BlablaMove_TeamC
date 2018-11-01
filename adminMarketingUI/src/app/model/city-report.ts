import {City} from './city';

export class CityReport {
  private _city: City;
  private _transactionCount: number;

  constructor(city: City, transactionCount: number) {
    this._city = city;
    this._transactionCount = transactionCount;
  }

  get city(): City {
    return this._city;
  }

  set city(value: City) {
    this._city = value;
  }

  get transactionCount(): number {
    return this._transactionCount;
  }

  set transactionCount(value: number) {
    this._transactionCount = value;
  }
}
