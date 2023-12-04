import { Component, Inject } from '@angular/core';
import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatDialogModule,
} from '@angular/material/dialog';
import { NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { DialogData, HomeComponent } from '../home/home.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';
import * as moment from 'moment';
import { clippingParents } from '@popperjs/core';

@Component({
  selector: 'app-quick-meal',
  templateUrl: './quick-meal.component.html',
  styleUrls: ['./quick-meal.component.css'],
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
  ],
})
export class QuickMealComponent {
  selected: Date | null = null;
  today = new Date();

  constructor(
    public dialogRef: MatDialogRef<HomeComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  dateFilter = (date: Date) => {
    const curretnDate = moment().startOf('D').toISOString();
    const isSunday = moment(curretnDate).isoWeekday() === 0;
    const isFridayday = moment(curretnDate).isoWeekday() === 5;
    const isSaturday = moment(curretnDate).isoWeekday() === 6;

    // will open monday if current day is friday/saturday/sunday

    if (isFridayday || isSunday || isSaturday) {
      if (isFridayday) {
        if (
          moment(moment(curretnDate))
            .startOf('D')
            .add(3, 'day')
            .toISOString() === moment(moment(date)).startOf('D').toISOString()
        ) {
          return true;
        }
      }
      if (isSunday) {
        // sunday ==> monday
        if (
          moment(moment(curretnDate))
            .startOf('D')
            .add(1, 'day')
            .toISOString() === moment(moment(date)).startOf('D').toISOString()
        ) {
          return true;
        }
      }
      if (isSaturday) {
        if (
          moment(moment(curretnDate))
            .startOf('D')
            .add(2, 'day')
            .toISOString() === moment(moment(date)).startOf('D').toISOString()
        ) {
          return true;
        }
      }
    }

    // will close if saturday/sunday
    const day = date.getDay();
    if (day === 0 || day === 6) {
      return false;
    }

    // will opne today booking
    if (
      moment(date).startOf('D').toISOString() ===
      moment().startOf('D').toISOString()
    ) {
      return true;
    }
    // moment ().weekday() // number
    // 0 sunday

    // 6 saturday

    // Allow only today and the next day
    return (
      date >= this.today &&
      date <= this.getNextDay(moment().add(0, 'days').toDate())
    );
  };

  getNextDay(date: Date): Date {
    const nextDay = new Date(date);
    nextDay.setDate(date.getDate() + 1);
    return nextDay;
  }
}
