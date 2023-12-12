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
      meal: 'Dosa',
      transactionID: 'TTCNI022000800521',
      empName: 'Nandini Shah',
    },
    {
      id: 2,
      meal: 'Ramen',
      transactionID: 'TTCNI022000800500',
      empName: 'Nandini Shah',
    },
    {
      id: 3,
      meal: 'Pasta',
      transactionID: 'TTCNI022000800594',
      empName: 'Nandini Shah',
    },

    {
      id: 4,
      meal: 'Thali',
      transactionID: 'TTCNI022000800522',
      empName: 'Nandini Shah',
    },

    {
      id: 5,
      meal: 'Ramen',
      transactionID: 'TTCNI022000800509',
      empName: 'Nandini Shah',
    },

    {
      id: 6,
      meal: 'PavBhaji',
      transactionID: 'TTCNI022000800511',
      empName: 'Nandini Shah',
    },

    {
      id: 7,
      meal: 'Thali',
      transactionID: 'TTCNI022000800534',
      empName: 'Nandini Shah',
    },

    {
      id: 8,
      meal: 'Pasta',
      transactionID: 'TTCNI022000800531',
      empName: 'Nandini Shah',
    },

    {
      id: 9,
      meal: 'PavBhaji',
      transactionID: 'TTCNI022000800587',
      empName: 'Nandini Shah',
    },

    {
      id: 10,
      meal: 'Ramen',
      transactionID: 'TTCNI022000800556',
      empName: 'Nandini Shah',
    },

    {
      id: 11,
      meal: 'PavBhaji',
      transactionID: 'TTCNI022000800583',
      empName: 'Nandini Shah',
    },

    {
      id: 12,
      meal: 'Pasta',
      transactionID: 'TTCNI022000800588',
      empName: 'Nandini Shah',
    },

    {
      id: 13,
      meal: 'Thali',
      transactionID: 'TTCNI022000800532',
      empName: 'Nandini Shah',
    },
  ];

  private timer: any;
  isButtonDisabled: boolean = false;
  remainingMinutes: number = 15;
  remainingSeconds: number = 0;

  toggleQR(name: string, id: string, transactionID: string) {
    this.isBookingsVisible = !this.isBookingsVisible;
    const currentDate = new Date().toLocaleDateString();
    const employeeName = name;
    const employeeId = id;
    const couponNumber = transactionID;

    const couponInfo = `Date and Day: ${currentDate}\nEmployee Name: ${employeeName}\nEmployee ID: ${employeeId}\nCoupon Number: ${couponNumber}\nRedeem Successful`;
    const myQr = qrcode(14, 'L');
    console.log(name);
    myQr.addData(couponInfo);
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
          this.closeQRDialog();
        }
      }
    }, 1000);
  }

  closeQRDialog() {
    this.isButtonDisabled = false;
    clearInterval(this.timer);
    this.isBookingsVisible = true;
    this.qrImage = '';
    this.remainingMinutes = 1;
    this.remainingSeconds = 0;
  }

  ngOnDestroy() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }
}
