import { Component, Input } from '@angular/core';
import { GrimPartService } from '../grim-part.service';

@Component({
  selector: 'app-nav-side-bar',
  templateUrl: './nav-side-bar.component.html',
  styleUrls: ['./nav-side-bar.component.css']
})
export class NavSideBarComponent {

  @Input()
  partName: string = "";

  constructor(private partService: GrimPartService) { }

}
