import { Component } from '@angular/core';

@Component({
  selector: 'app-navbarcontainer',
  templateUrl: './navbarcontainer.component.html',
  styleUrls: ['./navbarcontainer.component.css'],
})
export class NavbarcontainerComponent {
  title = 'meal_app';
  sideBarStatus: boolean = false;
}
