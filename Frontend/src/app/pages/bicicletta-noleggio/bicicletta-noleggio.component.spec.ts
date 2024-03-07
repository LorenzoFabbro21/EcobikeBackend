import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiciclettaNoleggioComponent } from './bicicletta-noleggio.component';

describe('BiciclettaNoleggioComponent', () => {
  let component: BiciclettaNoleggioComponent;
  let fixture: ComponentFixture<BiciclettaNoleggioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BiciclettaNoleggioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiciclettaNoleggioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
