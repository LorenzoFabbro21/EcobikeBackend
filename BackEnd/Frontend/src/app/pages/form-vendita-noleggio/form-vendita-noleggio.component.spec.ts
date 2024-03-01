import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormVenditaNoleggioComponent } from './form-vendita-noleggio.component';

describe('FormVenditaNoleggioComponent', () => {
  let component: FormVenditaNoleggioComponent;
  let fixture: ComponentFixture<FormVenditaNoleggioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormVenditaNoleggioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormVenditaNoleggioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
