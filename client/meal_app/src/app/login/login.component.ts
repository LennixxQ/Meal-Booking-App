import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string;
  password;
  constructor() {
    this.username = '';
    this.password = '';
  }
  login() {
    console.log('Username:', this.username);
    console.log('Password:', this.password);

    alert('Login successful!');
  }

  show = true;

  ngOnInit() {
    this.password = 'password';
  }

  onClick() {
    if (this.password === 'password') {
      this.password = 'text';
      this.show = true;
    } else {
      this.password = 'password';
      this.show = false;
    }
  }
}
