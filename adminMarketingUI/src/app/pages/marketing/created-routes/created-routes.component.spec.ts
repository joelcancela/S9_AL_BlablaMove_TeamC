import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatedRoutesComponent } from './created-routes.component';

describe('CreatedRoutesComponent', () => {
  let component: CreatedRoutesComponent;
  let fixture: ComponentFixture<CreatedRoutesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatedRoutesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatedRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
