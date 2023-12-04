import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeQuickMealComponent } from './home-quick-meal.component';

describe('HomeQuickMealComponent', () => {
  let component: HomeQuickMealComponent;
  let fixture: ComponentFixture<HomeQuickMealComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeQuickMealComponent]
    });
    fixture = TestBed.createComponent(HomeQuickMealComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
