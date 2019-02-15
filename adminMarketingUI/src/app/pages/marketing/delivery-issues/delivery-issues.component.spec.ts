import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryIssuesComponent } from './delivery-issues.component';

describe('DeliveryIssuesComponent', () => {
  let component: DeliveryIssuesComponent;
  let fixture: ComponentFixture<DeliveryIssuesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeliveryIssuesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeliveryIssuesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
