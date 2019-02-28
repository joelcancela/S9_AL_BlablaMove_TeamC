export class DeliveredItem {
  private _itemType: string;
  private _delivery_uuid: string;
  private _time: Date;

  constructor(itemType: string, delivery_uuid: string, time: Date) {
    this._itemType = itemType;
    this._delivery_uuid = delivery_uuid;
    this._time = time;
  }

  get itemType(): string {
    return this._itemType;
  }

  set itemType(value: string) {
    this._itemType = value;
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
