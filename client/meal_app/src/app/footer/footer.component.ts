import { Component } from '@angular/core';
import { Router, Routes } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
})
export class FooterComponent {
  shouldShowFooter = true;

  constructor(private router: Router) {
    this.router.events.subscribe((res) => {
      if (this.router.url.includes('login')) {
        this.shouldShowFooter = false;
      } else if (this.router.url.includes('change-password')) {
        this.shouldShowFooter = false;
      } else {
        this.shouldShowFooter = true;
      }
    });
  }
}
