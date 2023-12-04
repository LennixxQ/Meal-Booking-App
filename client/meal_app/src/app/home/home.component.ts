import {
  CUSTOM_ELEMENTS_SCHEMA,
  Component,
  Inject,
  NgModule,
  OnInit,
} from '@angular/core';
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
import { QuickMealComponent } from '../quick-meal/quick-meal.component';
import * as moment from 'moment';
import { HomeQuickMealComponent } from '../home-quick-meal/home-quick-meal.component';
import {
  MatDatepicker,
  MatDatepickerModule,
} from '@angular/material/datepicker';
import { MatCardModule } from '@angular/material/card';

declare var $: any;

export interface DialogData {
  animal: string;
  name: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    NgIf,
    MatDialogModule,
    MatDatepickerModule,
    MatCardModule,
  ],
})
export class HomeComponent implements OnInit {
  animal: string = '';
  name: string = '';
  selected: any = null;
  holidayDates: Date[] = [new Date('2023-12-25'), new Date('2024-01-01')]; // Array of custom holiday dates

  shouldShowButton: boolean = true;

  constructor(public dialog: MatDialog) {
    this.checkTimer = this.checkTimer.bind(this);
  }

  ngOnInit(): void {
    this.checkTimer();

    const checkFunction = () => {
      this.checkTimer();
    };

    setInterval(function () {
      checkFunction();
    }, 60 * 1000);
  }

  checkTimer() {
    const openingTime = moment().startOf('day').add(0, 'hour');
    const endTime = moment().startOf('day').add(23, 'hour');
    const currTime = moment();

    if (moment(currTime).isBefore(openingTime)) {
      this.shouldShowButton = false;
    } else if (moment(currTime).isAfter(endTime)) {
      this.shouldShowButton = false;
    } else {
      this.shouldShowButton = true;
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(QuickMealComponent, {
      width: '550px',
      data: { name: this.name, animal: this.animal },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.animal = result;
    });
  }

  dateFilter = (date: Date): boolean => {
    const day = date.getDay();
    const isHoliday = this.holidayDates.some((holiday) =>
      this.isSameDate(date, holiday)
    );

    // Disable weekends and custom holidays
    return day !== 0 && day !== 6 && !isHoliday;
  };

  // Function to compare if two dates are the same (ignoring time component)
  private isSameDate(date1: Date, date2: Date): boolean {
    return (
      date1.getFullYear() === date2.getFullYear() &&
      date1.getMonth() === date2.getMonth() &&
      date1.getDate() === date2.getDate()
    );
  }
}
