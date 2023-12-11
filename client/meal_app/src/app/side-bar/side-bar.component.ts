import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css'],
})
export class SideBarComponent implements OnInit {
  @Input() sideBarStatus: boolean = false;
  list = [
    {
      number: '1',
      name: 'Home',
      icon: 'fa-solid fa-right-from-bracket',
    },

    {
      number: '3',
      name: 'About-Us',
      icon: 'fa-sharp fa-solid fa-address-card',
    },
    {
      number: '4',
      name: 'Terms & Conditions',
      icon: 'fa-solid fa-file',
    },
    {
      number: '5',
      name: 'Privacy Policy',
      icon: 'fa-solid fa-book',
    },
  ];

  constructor() {}
  ngOnInit(): void {}
}
