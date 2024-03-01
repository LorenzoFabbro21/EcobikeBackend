import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiciclettaDettagliComponent } from './bicicletta-dettagli.component';

describe('BiciclettaDettagliComponent', () => {
  let component: BiciclettaDettagliComponent;
  let fixture: ComponentFixture<BiciclettaDettagliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BiciclettaDettagliComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiciclettaDettagliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
