import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormVenditaComponent } from './form-vendita.component';

describe('FormVenditaComponent', () => {
  let component: FormVenditaComponent;
  let fixture: ComponentFixture<FormVenditaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormVenditaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormVenditaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
