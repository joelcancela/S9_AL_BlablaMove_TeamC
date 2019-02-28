import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfrastructureCheckerComponent } from './infrastructure-checker.component';

describe('InfrastructureCheckerComponent', () => {
  let component: InfrastructureCheckerComponent;
  let fixture: ComponentFixture<InfrastructureCheckerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfrastructureCheckerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfrastructureCheckerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
