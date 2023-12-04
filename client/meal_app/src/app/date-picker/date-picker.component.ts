import { Component } from '@angular/core';

@Component({
  selector: 'app-date-picker',
  templateUrl: './date-picker.component.html',
  styleUrls: ['./date-picker.component.css'],
})
export class DatePickerComponent {
  //   selected: Date | null = null;
  //   holidayDates: Date[] = [new Date('2023-12-25'), new Date('2024-01-01')]; // Array of custom holiday dates
  //   dateFilter = (date: Date): boolean => {
  //     const day = date.getDay();
  //     const isHoliday = this.holidayDates.some((holiday) =>
  //       this.isSameDate(date, holiday)
  //     );
  //     // Disable weekends and custom holidays
  //     return day !== 0 && day !== 6 && !isHoliday;
  //   };
  //   // Function to compare if two dates are the same (ignoring time component)
  //   private isSameDate(date1: Date, date2: Date): boolean {
  //     return (
  //       date1.getFullYear() === date2.getFullYear() &&
  //       date1.getMonth() === date2.getMonth() &&
  //       date1.getDate() === date2.getDate()
  //     );
  //   }
}
