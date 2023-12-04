import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatePickerModalComponent } from './date-picker-modal.component';

describe('DatePickerModalComponent', () => {
  let component: DatePickerModalComponent;
  let fixture: ComponentFixture<DatePickerModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DatePickerModalComponent]
    });
    fixture = TestBed.createComponent(DatePickerModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
