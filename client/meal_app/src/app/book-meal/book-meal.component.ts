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

  dateFilter = (date: Date) => {
    const curretnDate = moment().startOf('D').toISOString();
    const isSunday = moment(curretnDate).isoWeekday() === 0;
    const isFridayday = moment(curretnDate).isoWeekday() === 5;
    const isSaturday = moment(curretnDate).isoWeekday() === 6;
  };
}
