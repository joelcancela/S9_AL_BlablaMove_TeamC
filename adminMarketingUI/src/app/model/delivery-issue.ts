export class DeliveryIssue {
  private _issueType: string;
  private _delivery_uuid: string;
  private _time: Date;

  constructor(issueType: string, delivery_uuid: string, time: Date) {
    this._delivery_uuid = delivery_uuid;
    this._issueType = issueType;
    this._time = time;
  }

  get issueType(): string {
    return this._issueType;
  }

  set issueType(value: string) {
    this._issueType = value;
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
