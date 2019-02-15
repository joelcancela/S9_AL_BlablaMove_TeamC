import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanceledRoutesComponent } from './canceled-routes.component';

describe('CanceledRoutesComponent', () => {
  let component: CanceledRoutesComponent;
  let fixture: ComponentFixture<CanceledRoutesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanceledRoutesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanceledRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
