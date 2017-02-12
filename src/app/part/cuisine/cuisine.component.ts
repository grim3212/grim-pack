import { Component } from '@angular/core';
import {ChocolateComponent, CookingComponent, DragonFruitComponent, HealthComponent, SodaComponent} from './sections/sections.component';

export const SECTIONS: {name:string, shortName: string, component: Component}[] = [
  {name: "Chocolate", shortName: "chocolate", component: ChocolateComponent},
  {name: "Cooking", shortName: "cooking", component: CookingComponent},
  {name: "Dragon Fruit", shortName: "dragon-fruit", component: DragonFruitComponent},
  {name: "Health", shortName: "health", component: HealthComponent},
  {name: "Soda", shortName: "soda", component: SodaComponent}
];

@Component({
  selector: 'app-cuisine',
  templateUrl: './cuisine.component.html',
  styleUrls: ['./cuisine.component.css']
})
export class CuisineComponent {

  constructor() { }

}
