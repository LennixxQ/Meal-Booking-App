import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string;
  password: string;
  constructor() {
    this.username = '';
    this.password = '';
  }
  login() {
    // Add your authentication logic here
    console.log('Username:', this.username);
    console.log('Password:', this.password);
    
    alert('Login successful!');
  }
}