import { Component, AfterViewInit } from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements AfterViewInit {
  ngAfterViewInit() {
    // Initialize FullCalendar
    $('#calendar').fullCalendar({
      plugins: ['dayGrid', 'bootstrap'],
      themeSystem: 'bootstrap',
      defaultView: 'month', // Show the whole month
      events: [
        // Add your calendar events here
        {
          title: 'Event 1',
          start: '2023-11-01',
        },
        // Add more events as needed
      ],
    });
  }
  openCalendar() {
    // Add any logic to open the calendar
    console.log('Open Calendar');
  }

  openBookingList() {
    // Add any logic to open the booking list
    console.log('Open Booking List');
  }
}
