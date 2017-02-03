export class GrimPart {
  name: string;
  info: string;
  shortName: string;
  sections: Array<string>;

  constructor(partName: string, partInfo: string, partShortName: string, partSections: Array<string>){
    this.name = partName;
    this.info = partInfo;
    this.shortName = partShortName;
    this.sections = partSections;
  }

}
