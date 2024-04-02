import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettagliBikeToSellComponent } from './dettagli-bike-to-sell.component';

describe('DettagliBikeComponent', () => {
  let component: DettagliBikeToSellComponent;
  let fixture: ComponentFixture<DettagliBikeToSellComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DettagliBikeToSellComponent]
    });
    fixture = TestBed.createComponent(DettagliBikeToSellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
