import {Component} from '@angular/core';
import {setTheme} from 'ngx-bootstrap';
import {v4 as uuid} from 'uuid';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor() {
    setTheme('bs3'); // bootstrap3
  }
  static uuid = uuid();
  title = 'Is BlablaMove down ?';
}
