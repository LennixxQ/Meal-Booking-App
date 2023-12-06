import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  @Output() sideBarToggled = new EventEmitter<boolean>();
  menuStatus: boolean = false;

  constructor() {}
  ngOnInit(): void {}
  sideBarToggle() {
    this.menuStatus = !this.menuStatus;
    this.sideBarToggled.emit(this.menuStatus);
  }
}
