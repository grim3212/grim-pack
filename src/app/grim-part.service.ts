import { Injectable } from '@angular/core';
import { GrimPart } from './grim-part'

export const GRIMPARTS: GrimPart[] = [
  {name: "Grim Core", info: "The core part containing shared classes and the instruction manual", shortName: "core"},
  {name: "Grim Cuisine", info: "Cuisine contains food and health related items", shortName: "cuisine"},
  {name: "Grim Decor", info: "Decor is for decorating and eye candy", shortName: "decor"},
  {name: "Grim Industry", info: "Industry allows for more complex machinery or items", shortName: "industry"},
  {name: "Grim Tools", info: "Tools adds in many different weapons and tools", shortName: "tools"},
  {name: "Grim Util", info: "Util provides small utilities like automatically replacing items or FusRoDah!", shortName: "util"},
  {name: "Grim World", info: "World contains special world generation and mobs", shortName: "world"}
];

@Injectable()
export class GrimPartService {

  private grimpackParts: GrimPart[];

    constructor(){
    }

  public getParts(): GrimPart[] {
    return GRIMPARTS;
  }

  public buildPartArray(numColumns: number){
        var arrOfarr = [];
        for(var i = 0; i < GRIMPARTS.length ; i+=numColumns) {
            var row = [];
            for(var x = 0; x < numColumns; x++) {
              var value = GRIMPARTS[i + x];
                if (!value) {
                    break;
                }
                row.push(value);
            }
            arrOfarr.push(row);
        }
         return arrOfarr;
  }

  public getPartFromName(partName: string) : GrimPart {
      for(let part of this.grimpackParts){
        if(part.name == partName || part.shortName == partName)
          return part;
      }

      //Return default part (GrimCore)
      return this.grimpackParts[0];
  }
}
