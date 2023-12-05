import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-meal-card',
  templateUrl: './meal-card.component.html',
  styleUrls: ['./meal-card.component.css'],
})
export class MealCardComponent implements OnInit {
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
      imageSource: 'assets/images/ramen.jpg',
    },
    {
      day: 'Thursday',
      meal: 'Punjabi Thali',
      imageSource: 'assets/images/thali.jpg',
    },
    {
      day: 'Friday',
      meal: 'Dosa',
      imageSource: 'assets/images/dosa.jpg',
    },
  ];

  currentDayMeal: any;

  constructor() {}

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
  }
}
