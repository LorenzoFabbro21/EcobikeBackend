import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalBuyComponent } from './personal-buy.component';

describe('PersonalBuyComponent', () => {
  let component: PersonalBuyComponent;
  let fixture: ComponentFixture<PersonalBuyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonalBuyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonalBuyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
