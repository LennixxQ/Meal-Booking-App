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

  showNotificationList = false;
  notifications: string[] = [];
  counter = 0;

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

  toggleNotificationList() {
    this.showNotificationList = !this.showNotificationList;

    const notificationMessage = this.generateRandomNotification();

    this.notifications.unshift(notificationMessage);

    this.notifications = this.notifications.slice(0, 5);
  }

  generateRandomNotification(): string {
    const messages = [
      'Your meal has been booked!',
      'Quick meal is booked',
      'Your meal has been cancelled',
    ];
    const index = this.counter % messages.length;
    this.counter++;
    return messages[index];
  }

  removeNotification(index: number) {
    this.notifications.splice(index, 1);
  }
}
