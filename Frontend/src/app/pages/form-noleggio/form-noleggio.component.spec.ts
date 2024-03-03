import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormNoleggioComponent } from './form-noleggio.component';

describe('FormVenditaNoleggioComponent', () => {
  let component: FormNoleggioComponent;
  let fixture: ComponentFixture<FormNoleggioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormNoleggioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormNoleggioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
