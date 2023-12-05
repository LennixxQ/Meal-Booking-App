import { CUSTOM_ELEMENTS_SCHEMA, Component, Inject } from '@angular/core';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import * as moment from 'moment';
import { DialogData, HomeComponent } from '../home/home.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-book-meal',
  templateUrl: './book-meal.component.html',
  styleUrls: ['./book-meal.component.css'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
  ],
})
export class BookMealComponent {
  constructor(
    public dialogRef: MatDialogRef<HomeComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  dateFilter(date: Date | null): boolean {
    if (!date) {
      return false; // handle null case if needed
    }

    const day = date.getDate();
    const month = date.getMonth() + 1; // Month is zero-based, so add 1

    const today = new Date();
    today.setHours(0, 0, 0, 0); // Used to disable the past dates

    // Calculate the timestamp for 3 months from now
    const threeMonthsFromNow = new Date();
    threeMonthsFromNow.setMonth(threeMonthsFromNow.getMonth() + 3);
    threeMonthsFromNow.setHours(0, 0, 0, 0);

    //disable past dates
    if (date <= today) {
      return false;
    }
    // Disable weekends (Saturday and Sunday)
    if (date.getDay() === 0 || date.getDay() === 6) {
      return false;
    }

    // Disable specific dates (25th December, 31st December, 15th January, 26th January)
    if (
      (month === 12 && (day === 25 || day === 31)) ||
      (month === 1 && (day === 15 || day === 26))
    ) {
      return false;
    }

    //return true;
    if (date >= today && date <= threeMonthsFromNow) {
      return true;
    } else {
      return false; // Disable selection for dates outside the specified range
    }
  }
}
