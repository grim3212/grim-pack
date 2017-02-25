import { Component } from '@angular/core';
import {DecorationComponent, FurnitureComponent, HangingComponent, ColorizerComponent, FireplacesComponent, SlopesComponent} from './sections/decor-sections.component'

export const SECTIONS: {name:string, shortName: string, component: Component}[] = [
  {name: "Colorizer", shortName: "colorizer", component: ColorizerComponent},
  {name: "Decoration", shortName: "decoration", component: DecorationComponent},
  {name: "Fireplaces", shortName: "fireplaces", component: FireplacesComponent},
  {name: "Furniture", shortName: "furniture", component: FurnitureComponent},
  {name: "Hanging", shortName: "hanging", component: HangingComponent},
  {name: "Slopes", shortName: "slopes", component: SlopesComponent}
];

@Component({
  selector: 'app-decor',
  templateUrl: './decor.component.html',
  styleUrls: ['./decor.component.css']
})
export class DecorComponent {

  constructor() { }

  get sections(){
    return SECTIONS;
  }
}
