import {Component} from '@angular/core';
import {Part} from './part';

export class GrimPart {
  name: string;
  info: string;
  shortName: string;
  sections: {name:string, shortName: string, component: Component}[];

  constructor(partName: string, partInfo: string, partShortName: string, partSections: {name:string, shortName: string, component: Component}[]){
    this.name = partName;
    this.info = partInfo;
    this.shortName = partShortName;
    this.sections = partSections;
  }

}
