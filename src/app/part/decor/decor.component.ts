import { Component } from '@angular/core';
import {DecorationComponent, FurnitureComponent, HangingComponent} from './sections/decor-sections.component'

export const SECTIONS: {name:string, shortName: string, component: Component}[] = [
  {name: "Decoration", shortName: "decoration", component: DecorationComponent},
  {name: "Furniture", shortName: "furniture", component: FurnitureComponent},
  {name: "Hanging", shortName: "hanging", component: HangingComponent}
];

@Component({
  selector: 'app-decor',
  templateUrl: './decor.component.html',
  styleUrls: ['./decor.component.css']
})
export class DecorComponent {

  constructor() { }

}
