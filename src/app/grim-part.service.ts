import { Injectable } from '@angular/core';
import { GrimPart } from './grim-part'

export const GRIMPARTS: GrimPart[] = [
  {name: "Grim Core", info: "The core part containing shared classes and the instruction manual"},
  {name: "Grim Cuisine", info: "Cuisine contains food and health related items"},
  {name: "Grim Decor", info: "Decor is for decorating and eye candy"},
  {name: "Grim Industry", info: "Industry allows for more complex machinery or items"},
  {name: "Grim Tools", info: "Tools adds in many different weapons and tools"},
  {name: "Grim Util", info: "Util provides small utilities like automatically replacing items or FusRoDah!"},
  {name: "Grim World", info: "World contains special world generation and mobs"}
];

@Injectable()
export class GrimPartService {

  private grimpackParts: GrimPart[];

    constructor(){
    }

  public getParts(): GrimPart[] {
    return GRIMPARTS;
  }

  public getPartFromName(partName: string) : GrimPart {
      for(let part of this.grimpackParts){
        if(part.name == partName)
          return part;
      }

      //Return default part (GrimCore)
      return this.grimpackParts[0];
  }
}
