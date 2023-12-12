import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  @Output() sideBarToggled = new EventEmitter<boolean>();
  menuStatus: boolean = false;

  shouldShowheader = true;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((res) => {
      if (this.router.url.includes('login')) {
        this.shouldShowheader = false;
      } else if (this.router.url.includes('change-password')) {
        this.shouldShowheader = false;
      } else {
        this.shouldShowheader = true;
      }
    });
  }

  sideBarToggle() {
    this.menuStatus = !this.menuStatus;
    this.sideBarToggled.emit(this.menuStatus);
  }
}
