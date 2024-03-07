import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiciclettaVenditaComponent } from './bicicletta-vendita.component';

describe('BiciclettaDettagliComponent', () => {
  let component: BiciclettaVenditaComponent;
  let fixture: ComponentFixture<BiciclettaVenditaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BiciclettaVenditaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiciclettaVenditaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
