import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoleggioComponent } from './noleggio.component';

describe('NoleggioComponent', () => {
  let component: NoleggioComponent;
  let fixture: ComponentFixture<NoleggioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoleggioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoleggioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
