import { Component } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { GrimPartService } from '../grim-part.service';
import { GrimPart } from '../grim-part';
import { CoreComponent }   from './core/core.component';
import { CuisineComponent }   from './cuisine/cuisine.component';
import { DecorComponent }   from './decor/decor.component';
import { IndustryComponent }   from './industry/industry.component';
import { ToolsComponent }   from './tools/tools.component';
import { UtilComponent }   from './util/util.component';
import { WorldComponent }   from './world/world.component';
import { EmptyComponent }   from './empty/empty.component';

@Component({
  selector: 'app-part',
  templateUrl: './part.component.html',
  styleUrls: ['./part.component.css']
})
export class PartComponent {
  currentComponent: Component;
  part: GrimPart;
  section: { name: string, shortName: string, component: Component };

  constructor(private route: ActivatedRoute, private partService: GrimPartService, private router: Router) {
    this.route.params.subscribe((param) => {
      let partName = param['partName'];
      let section = param['section'];

      if (partName) {
        this.part = this.partService.getPartFromName(partName);

        if (!section) {
          switch (partName) {
            case 'core':
              this.currentComponent = CoreComponent;
              break;
            case 'cuisine':
              this.currentComponent = CuisineComponent;
              break;
            case 'decor':
              this.currentComponent = DecorComponent;
              break;
            case 'industry':
              this.currentComponent = IndustryComponent;
              break;
            case 'tools':
              this.currentComponent = ToolsComponent;
              break;
            case 'util':
              this.currentComponent = UtilComponent;
              break
            case 'world':
              this.currentComponent = WorldComponent;
              break;
            default:
              this.router.navigate(['/404']);
              break;
          }
        } else {
          this.section = this.partService.matchSection(this.part, section);

          if (this.section) {
            this.currentComponent = this.section.component;
          } else {
            this.router.navigate(['/404']);
          }
        }
      } else {
        if (section) {
          this.router.navigate(['/404']);
        } else {
          this.currentComponent = EmptyComponent;
        }
      }
    });
  }
}
