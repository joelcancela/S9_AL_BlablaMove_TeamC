export class DeliveredItem {
  private _item_type: string;
  private _delivery_uuid: string;
  private _time: Date;

  constructor(item_type: string, delivery_uuid: string, time: Date) {
    this._item_type = item_type;
    this._delivery_uuid = delivery_uuid;
    this._time = time;
  }

  get item_type(): string {
    return this._item_type;
  }

  set item_type(value: string) {
    this._item_type = value;
  }

  get delivery_uuid(): string {
    return this._delivery_uuid;
  }

  set delivery_uuid(value: string) {
    this._delivery_uuid = value;
  }

  get time(): Date {
    return this._time;
  }

  set time(value: Date) {
    this._time = value;
  }
}
