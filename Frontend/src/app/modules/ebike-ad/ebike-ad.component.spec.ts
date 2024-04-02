import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EbikeAdComponent } from './ebike-ad.component';

describe('EbikeAdComponent', () => {
  let component: EbikeAdComponent;
  let fixture: ComponentFixture<EbikeAdComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EbikeAdComponent]
    })
    .compileComponents;
    
    fixture = TestBed.createComponent(EbikeAdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});


