import { Component, OnInit } from '@angular/core';
import { GrimPartService } from '../grim-part.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private partService : GrimPartService) { }

  ngOnInit() {
  }

}
