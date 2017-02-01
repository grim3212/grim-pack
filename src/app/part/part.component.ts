import { Component, ViewContainerRef, ViewChild, ReflectiveInjector, ComponentFactoryResolver, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { GrimPartService } from '../grim-part.service';
import { GrimPart } from '../grim-part';
import { CoreComponent }   from './core/core.component';
import { CuisineComponent }   from './cuisine/cuisine.component';
import { DecorComponent }   from './decor/decor.component';
import { IndustryComponent }   from './industry/industry.component';
import { ToolsComponent }   from './tools/tools.component';
import { UtilComponent }   from './util/util.component';
import { WorldComponent }   from './world/world.component';

@Component({
  selector: 'app-part',
  entryComponents: [CoreComponent,CuisineComponent,DecorComponent,IndustryComponent,ToolsComponent,UtilComponent,WorldComponent],
  template: `<div #partContainer></div>`,
  styleUrls: ['./part.component.css']
})
export class PartComponent implements OnInit {
  currentComponent = null;
  @ViewChild('partContainer', { read: ViewContainerRef }) partContainer: ViewContainerRef;

  private currentPart: GrimPart;

  constructor(private route: ActivatedRoute, private partService : GrimPartService, private resolver: ComponentFactoryResolver) {
  }

  ngOnInit() {

    console.log(this.route.firstChild.data);

    this.route.firstChild.data.subscribe(data => {
      console.log(data);
    });
  }

}
