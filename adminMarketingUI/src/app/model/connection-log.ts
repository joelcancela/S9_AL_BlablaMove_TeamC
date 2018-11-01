export class ConnectionLog {
  private _totalUsersConnected: number;
  private _localDateTime: Date;

  constructor(totalUsersConnected: number, localDateTime: Date) {
    this._totalUsersConnected = totalUsersConnected;
    this._localDateTime = localDateTime;
  }

  get totalUsersConnected(): number {
    return this._totalUsersConnected;
  }

  set totalUsersConnected(value: number) {
    this._totalUsersConnected = value;
  }

  get localDateTime(): Date {
    return this._localDateTime;
  }

  set localDateTime(value: Date) {
    this._localDateTime = value;
  }
}
