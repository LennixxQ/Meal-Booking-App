import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { DatePickerComponent } from './date-picker/date-picker.component';
import { QuickMealComponent } from './quick-meal/quick-meal.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatRadioModule } from '@angular/material/radio';
import { MatInputModule } from '@angular/material/input';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { MatDialogModule } from '@angular/material/dialog';
import { DatePickerModalComponent } from './date-picker-modal/date-picker-modal.component';
import { MatButtonModule } from '@angular/material/button';
import { NgIf } from '@angular/common';
import { BookMealComponent } from './book-meal/book-meal.component';
import { MatIconModule } from '@angular/material/icon';
import { SideBarComponent } from './side-bar/side-bar.component';
import { ShowBookingsComponent } from './show-bookings/show-bookings.component';
import { NavbarcontainerComponent } from './navbarcontainer/navbarcontainer.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AboutUsComponent } from './about-us/about-us.component';
import { PrivacyPolicyComponent } from './privacy-policy/privacy-policy.component';
import { TermsConditionComponent } from './terms-condition/terms-condition.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    FooterComponent,
    ForgetPasswordComponent,
    DatePickerComponent,
    ChangePasswordComponent,
    SideBarComponent,
    ShowBookingsComponent,
    NavbarcontainerComponent,
    AboutUsComponent,
    PrivacyPolicyComponent,
    TermsConditionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatRadioModule,
    MatInputModule,
    DatePickerModalComponent,
    MatDialogModule,
    QuickMealComponent,
    HomeComponent,
    FormsModule,
    NgIf,
    MatButtonModule,
    MatFormFieldModule,
    MatCardModule,
    MatIconModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent },
      { path: 'login', component: LoginComponent },
      { path: 'change-password', component: ChangePasswordComponent },
      { path: 'about-us', component: AboutUsComponent },
    ]),
  ],
  providers: [QuickMealComponent, DatePickerModalComponent],
  bootstrap: [AppComponent],
})
export class AppModule {}
