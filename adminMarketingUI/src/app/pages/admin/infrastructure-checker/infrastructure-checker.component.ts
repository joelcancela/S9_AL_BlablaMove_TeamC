import {Component, OnInit} from '@angular/core';
import {HeartbeatService} from './heartbeat.service';
import {RunningStatus} from './RunningStatus';

@Component({
  selector: 'infrastructure-checker',
  templateUrl: './infrastructure-checker.component.html',
  styleUrls: ['./infrastructure-checker.component.scss']
})
export class InfrastructureCheckerComponent implements OnInit {

  backend_running = RunningStatus.Stopped;
  fr_core_delivery_running = RunningStatus.Unknown;
  fr_core_KPI_running = RunningStatus.Unknown;
  fr_core_user_running = RunningStatus.Unknown;
  uk_core_delivery_running = RunningStatus.Unknown;
  uk_core_KPI_running = RunningStatus.Unknown;
  uk_core_user_running = RunningStatus.Unknown;

  constructor(private heartBeatService: HeartbeatService) {
    setInterval(() => {
      this.refreshHeartbeatStatus();
    }, 10000);
  }

  ngOnInit() {
  }

  refreshHeartbeatStatus() {
    this.heartBeatService.getHeartbeats().then(result => {
      this.backend_running = RunningStatus.Stopped;
      this.fr_core_delivery_running = RunningStatus.Unknown;
      this.fr_core_KPI_running = RunningStatus.Unknown;
      this.fr_core_user_running = RunningStatus.Unknown;
      this.uk_core_delivery_running = RunningStatus.Unknown;
      this.uk_core_KPI_running = RunningStatus.Unknown;
      this.uk_core_user_running = RunningStatus.Unknown;
      const arrayResult = result as Array<any>;
      const this_ = this;
      if (arrayResult.length > 0) {
        this.backend_running = RunningStatus.Running;
        arrayResult.forEach(function (element) {
          if (element.service_name === 'Core Delivery') {
            // TODO: region
            const time = Date.parse(element.time);
            const diffSecs = Math.round(Date.now() - time) / 1000;
            console.log(diffSecs);
            if (diffSecs > 10) {
              this_.fr_core_delivery_running = RunningStatus.Stopped;
              this_.uk_core_delivery_running = RunningStatus.Stopped;
            } else {
              this_.fr_core_delivery_running = RunningStatus.Running;
              this_.uk_core_delivery_running = RunningStatus.Running;
            }
          } else if (element.service_name === 'Core User') {
            // TODO: region
            const time = Date.parse(element.time);
            const diffSecs = Math.round(Date.now() - time) / 1000;
            console.log(diffSecs);
            if (diffSecs > 10) {
              this_.fr_core_user_running = RunningStatus.Stopped;
              this_.uk_core_user_running = RunningStatus.Stopped;
            } else {
              this_.fr_core_user_running = RunningStatus.Running;
              this_.uk_core_user_running = RunningStatus.Running;
            }
          } else if (element.service_name === 'Core KPI') {
            // TODO: region
            const time = Date.parse(element.time);
            const diffSecs = Math.round(Date.now() - time) / 1000;
            console.log(diffSecs);
            if (diffSecs > 10) {
              this_.fr_core_KPI_running = RunningStatus.Stopped;
              this_.uk_core_KPI_running = RunningStatus.Stopped;
            } else {
              this_.fr_core_KPI_running = RunningStatus.Running;
              this_.uk_core_KPI_running = RunningStatus.Running;
            }
          }
        });
      }
    }, error => {
      this.backend_running = RunningStatus.Stopped;
      this.fr_core_delivery_running = RunningStatus.Unknown;
      this.fr_core_KPI_running = RunningStatus.Unknown;
      this.fr_core_user_running = RunningStatus.Unknown;
      this.uk_core_delivery_running = RunningStatus.Unknown;
      this.uk_core_KPI_running = RunningStatus.Unknown;
      this.uk_core_user_running = RunningStatus.Unknown;
    });
  }

  getStatusName(status: RunningStatus) {
    return RunningStatus[status];
  }

}
