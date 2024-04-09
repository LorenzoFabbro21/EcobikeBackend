import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalRentComponent } from './personal-rent.component';

describe('PersonalRentComponent', () => {
  let component: PersonalRentComponent;
  let fixture: ComponentFixture<PersonalRentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonalRentComponent]
    });
    fixture = TestBed.createComponent(PersonalRentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
