import { Component, OnInit } from '@angular/core';
import { GrimPartService } from './grim-part.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit {
  title = 'app works!';
  navbarCollapsed = true;
  parts: any;

  constructor(private partService : GrimPartService){
  }

  ngOnInit(): void {
    this.parts = this.partService.getParts();
  }
}
