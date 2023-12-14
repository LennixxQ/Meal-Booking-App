import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router, Routes } from '@angular/router';
import { clippingParents } from '@popperjs/core';
import { catchError, throwError } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string;
  password;

  formFG = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  constructor(private htpClient: HttpClient, private router: Router) {
    this.username = '';
    this.password = '';
    this.navigateToHome = this.navigateToHome.bind(this);
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

  navigateToHome() {
    this.router.navigateByUrl('/home');
  }

  private handleError(error: HttpErrorResponse) {
    window.alert('Please enter valid credentials');

    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }

  handleSubmit() {
    if (this.formFG.valid === true) {
      console.log(this.formFG.value);

      try {
        this.htpClient
          .post('/mealBooking/auth/login', {
            email: this.formFG.value.email,
            password: this.formFG.value.password,
          })
          .pipe(catchError(this.handleError))
          .subscribe((response) => {
            const { jwtToken } = response as { jwtToken: string };

            if (jwtToken) {
              window.localStorage.setItem('jwt', jwtToken);
              this.navigateToHome();
            }
          });
      } catch (err) {
        window.alert('Please enter valid credentials');
      }
    } else {
      window.alert('Please submit proper data');
    }
  }
}
