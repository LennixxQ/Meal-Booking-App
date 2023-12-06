import {
  CUSTOM_ELEMENTS_SCHEMA,
  Component,
  Inject,
  NgModule,
  OnInit,
} from '@angular/core';
import {
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
import { BookMealComponent } from '../book-meal/book-meal.component';
import * as moment from 'moment';
import { MatDialog } from '@angular/material/dialog';

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

  dayMeals: any[] = [
    {
      day: 'Monday',
      meal: 'Pav Bhaji',
      imageSource:
        'https://www.cubesnjuliennes.com/wp-content/uploads/2020/07/Instant-Pot-Mumbai-Pav-Bhaji.jpg',
    },
    {
      day: 'Tuesday',
      meal: 'Pasta',
      imageSource: 'assets/images/pasta.jpg',
    },
    {
      day: 'Wednesday',
      meal: 'Ramen',
      imageSource: 'assets/images/ramennn.jpg',
    },
    {
      day: 'Thursday',
      meal: 'Thali',
      imageSource: 'assets/images/thaliiiiii.jpg',
    },
    {
      day: 'Friday',
      meal: 'Dosa',
      imageSource: 'assets/images/dosa.jpg',
    },
  ];

  currentDayMeal: any;

  constructor(public dialog: MatDialog) {
    this.checkTimer = this.checkTimer.bind(this);
  }

  ngOnInit(): void {
    const days = [
      'Sunday',
      'Monday',
      'Tuesday',
      'Wednesday',
      'Thursday',
      'Friday',
      'Saturday',
    ];
    const currentDay = new Date().getDay();
    this.currentDayMeal = this.dayMeals.find(
      (meal) => meal.day === days[currentDay]
    );

    this.checkTimer();

    const checkFunction = () => {
      this.checkTimer();
    };

    setInterval(function () {
      checkFunction();
    }, 60 * 1000);
  }

  //checks if 8 pm=disable and again enables next day at 8am

  checkTimer() {
    const openingTime = moment().startOf('day').add(8, 'hour');
    const endTime = moment().startOf('day').add(20, 'hour');
    const currTime = moment();

    if (moment(currTime).isBefore(openingTime)) {
      this.shouldShowButton = false;
    } else if (moment(currTime).isAfter(endTime)) {
      this.shouldShowButton = false;
    } else {
      this.shouldShowButton = true;
    }
  }

  //quickmeal popup
  openMealDialog(): void {
    const dialogRef = this.dialog.open(QuickMealComponent, {
      width: '550px',
      data: { name: this.name, animal: this.animal },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.animal = result;
    });
  }

  //bookmeal popup
  openDialogbox(): void {
    const dialogRef = this.dialog.open(BookMealComponent, {
      width: '550px',
      data: { name: this.name, animal: this.animal },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.animal = result;
    });
  }

  //homepage static calendar

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
