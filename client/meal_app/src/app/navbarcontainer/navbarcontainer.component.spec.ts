import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarcontainerComponent } from './navbarcontainer.component';

describe('NavbarcontainerComponent', () => {
  let component: NavbarcontainerComponent;
  let fixture: ComponentFixture<NavbarcontainerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarcontainerComponent]
    });
    fixture = TestBed.createComponent(NavbarcontainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
