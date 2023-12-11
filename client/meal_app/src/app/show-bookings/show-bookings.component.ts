import { Component, OnDestroy } from '@angular/core';
import * as qrcode from 'qrcode-generator';

@Component({
  selector: 'app-show-bookings',
  templateUrl: './show-bookings.component.html',
  styleUrls: ['./show-bookings.component.css'],
})
export class ShowBookingsComponent {
  isBookingsVisible: boolean = true;
  qrImage: string = '';
  bookings: any[] = [
    {
      id: 1,
      date: '10/09/2023',
      meal: 'Dosa',
      time: '12:50',
      transactionID: 'asndksad',
    },
    {
      id: 2,
      date: '10/09/2023',
      meal: 'Dosa',
      time: '12:50',
      transactionID: '1sadmpsalo',
    },
    {
      id: 3,
      date: '10/09/2023',
      meal: 'Dosa',
      time: '12:50',
      transactionID: 'aksldmsad',
    },
  ];

  private timer: any;
  isButtonDisabled: boolean = false;
  remainingMinutes: number = 15;
  remainingSeconds: number = 0;

  toggleQR() {
    this.isBookingsVisible = !this.isBookingsVisible;

    const myQr = qrcode(14, 'L');
    myQr.addData('1234567');
    myQr.make();

    this.qrImage = myQr.createImgTag();

    this.isButtonDisabled = true;
    this.timer = setInterval(() => {
      if (this.remainingSeconds > 0) {
        this.remainingSeconds--;
      } else {
        if (this.remainingMinutes > 0) {
          this.remainingMinutes--;
          this.remainingSeconds = 59;
        } else {
          this.isButtonDisabled = false;
          clearInterval(this.timer);
        }
      }
    }, 1000);
  }

  ngOnDestroy() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }
}
