import { Injectable, Component } from '@angular/core';
import { GrimPart } from './grim-part';
import { SECTIONS as CuisineSections } from './part/cuisine/cuisine.component';

export const GRIMPARTS: GrimPart[] = [
  { name: "Grim Core", info: "The core part containing shared classes and the instruction manual", shortName: "core", sections: [] },
  { name: "Grim Cuisine", info: "Cuisine contains food and health related items", shortName: "cuisine", sections: CuisineSections },
  { name: "Grim Decor", info: "Decor is for decorating and eye candy", shortName: "decor", sections: [] },
  { name: "Grim Industry", info: "Industry allows for more complex machinery or items", shortName: "industry", sections: [] },
  { name: "Grim Tools", info: "Tools adds in many different weapons and tools", shortName: "tools", sections: [] },
  { name: "Grim Util", info: "Util provides small utilities like automatically replacing items or FusRoDah!", shortName: "util", sections: [] },
  { name: "Grim World", info: "World contains special world generation and mobs", shortName: "world", sections: [] }
];

@Injectable()
export class GrimPartService {

  constructor() {
  }

  public getParts(): GrimPart[] {
    return GRIMPARTS;
  }

  public buildPartArray(numColumns: number) {
    var arrOfarr = [];
    for (var i = 0; i < GRIMPARTS.length; i += numColumns) {
      var row = [];
      for (var x = 0; x < numColumns; x++) {
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

  public getPartFromName(partName: string): GrimPart {
    for (let part of GRIMPARTS) {
      if (part.name == partName || part.shortName == partName)
        return part;
    }

    //Return default part (GrimCore)
    return GRIMPARTS[0];
  }

  public matchSection(part: GrimPart, shortName: string): { name: string, shortName: string, component: Component } {
    if (part.sections.length > 0) {
      for (let section of part.sections) {
        if (section.shortName == shortName)
          //Return matched component
          return section;
      }
    }

    //Return null to indicate we failed at matching
    return null;
  }
}
