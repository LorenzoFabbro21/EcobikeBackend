import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EbikeComponent } from './ebike.component';

describe('EbikeComponent', () => {
  let component: EbikeComponent;
  let fixture: ComponentFixture<EbikeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EbikeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EbikeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
