import { Component, OnInit } from '@angular/core';
import { HeartbeatService } from './heartbeat.service';
import { RunningStatus } from './RunningStatus';

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
  uk_indicator = "europe-west2-c";
  fr_indicator = "europe-west1-b";
  seconds_to_update = 10;

  constructor(private heartBeatService: HeartbeatService) {
    this.refreshHeartbeatStatus();
    setInterval(() => {
      this.refreshHeartbeatStatus();
    }, 10000);
    setInterval(() => {
      if (this.seconds_to_update == 0) {
        this.seconds_to_update = 10;
      }
      this.seconds_to_update--;
    }, 1000)
  }

  ngOnInit() {
  }

  refreshHeartbeatStatus() {
    const this_ = this;
    this.heartBeatService.getHeartbeats().then(result => {
      this_.backend_running = RunningStatus.Stopped;
      this_.fr_core_delivery_running = RunningStatus.Unknown;
      this_.fr_core_KPI_running = RunningStatus.Unknown;
      this_.fr_core_user_running = RunningStatus.Unknown;
      this_.uk_core_delivery_running = RunningStatus.Unknown;
      this_.uk_core_KPI_running = RunningStatus.Unknown;
      this_.uk_core_user_running = RunningStatus.Unknown;
      const arrayResult = result as Array<any>;
      if (arrayResult.length > 0) {
        this_.backend_running = RunningStatus.Running;
        arrayResult.forEach(function (element) {
          if (element.service_name === 'Core Delivery') {
            const time = Date.parse(element.time);
            const diffSecs = Math.round(Date.now() - time) / 1000;
            if (diffSecs > 10) {
              if (element.region != undefined) {
                if (element.region == this_.fr_indicator) {
                  this_.fr_core_delivery_running = RunningStatus.Stopped;

                } else if (element.region == this_.uk_indicator) {
                  this_.uk_core_delivery_running = RunningStatus.Stopped;

                } else {
                  this_.fr_core_delivery_running = RunningStatus.Stopped;
                  this_.uk_core_delivery_running = RunningStatus.Stopped;
                }
              }
            } else {
              if (element.region != undefined) {
                if (element.region == this_.fr_indicator) {
                  this_.fr_core_delivery_running = RunningStatus.Running;

                } else if (element.region == this_.uk_indicator) {
                  this_.uk_core_delivery_running = RunningStatus.Running;

                } else {
                  this_.fr_core_delivery_running = RunningStatus.Running;
                  this_.uk_core_delivery_running = RunningStatus.Running;
                }
              }
            }
          } else if (element.service_name === 'Core User') {
            const time = Date.parse(element.time);
            const diffSecs = Math.round(Date.now() - time) / 1000;
            if (diffSecs > 10) {
              if (element.region != undefined) {
                if (element.region == this_.fr_indicator) {
                  this_.fr_core_user_running = RunningStatus.Stopped;

                } else if (element.region == this_.uk_indicator) {
                  this_.uk_core_user_running = RunningStatus.Stopped;

                } else {
                  this_.fr_core_user_running = RunningStatus.Stopped;
                  this_.uk_core_user_running = RunningStatus.Stopped;
                }
              }
            } else {
              if (element.region != undefined) {
                if (element.region == this_.fr_indicator) {
                  this_.fr_core_user_running = RunningStatus.Running;

                } else if (element.region == this_.uk_indicator) {
                  this_.uk_core_user_running = RunningStatus.Running;

                } else {
                  this_.fr_core_user_running = RunningStatus.Running;
                  this_.uk_core_user_running = RunningStatus.Running;
                }
              }
            }
          } else if (element.service_name === 'Core KPI') {
            const time = Date.parse(element.time);
            const diffSecs = Math.round(Date.now() - time) / 1000;
            if (diffSecs > 10) {
              if (element.region != undefined) {
                if (element.region == this_.fr_indicator) {
                  this_.fr_core_KPI_running = RunningStatus.Stopped;

                } else if (element.region == this_.uk_indicator) {
                  this_.uk_core_KPI_running = RunningStatus.Stopped;

                } else {
                  this_.fr_core_KPI_running = RunningStatus.Stopped;
                  this_.uk_core_KPI_running = RunningStatus.Stopped;
                }
              }
            } else {
              if (element.region != undefined) {
                if (element.region == this_.fr_indicator) {
                  this_.fr_core_KPI_running = RunningStatus.Running;

                } else if (element.region == this_.uk_indicator) {
                  this_.uk_core_KPI_running = RunningStatus.Running;

                } else {
                  this_.fr_core_KPI_running = RunningStatus.Running;
                  this_.uk_core_KPI_running = RunningStatus.Running;
                }
              }
            }
          }
        });
      }
    }, error => {
      this_.backend_running = RunningStatus.Stopped;
      this_.fr_core_delivery_running = RunningStatus.Unknown;
      this_.fr_core_KPI_running = RunningStatus.Unknown;
      this_.fr_core_user_running = RunningStatus.Unknown;
      this_.uk_core_delivery_running = RunningStatus.Unknown;
      this_.uk_core_KPI_running = RunningStatus.Unknown;
      this_.uk_core_user_running = RunningStatus.Unknown;
    });
  }

  getStatusName(status: RunningStatus) {
    return RunningStatus[status];
  }

}
