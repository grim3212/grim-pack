import { Component } from '@angular/core';
import { GrimPartService } from './grim-part.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  navbarCollapsed = true;

  constructor(private partService: GrimPartService) {
  }

  get year(): number {
    var d = new Date();
    return d.getFullYear();
  }
}
