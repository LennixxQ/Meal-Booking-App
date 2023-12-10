import { Component } from '@angular/core';
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

  toggleQR() {
    this.isBookingsVisible = !this.isBookingsVisible;

    const myQr = qrcode(14, 'L');
    myQr.addData('aksldmsad');
    myQr.make();

    this.qrImage = myQr.createImgTag();
  }
}
