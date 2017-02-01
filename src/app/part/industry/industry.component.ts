import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-industry',
  templateUrl: './industry.component.html',
  styleUrls: ['./industry.component.css']
})
export class IndustryComponent implements OnInit {

  name: string;

  constructor() {
    this.name = "Grim industry";
  }

  ngOnInit() {
  }

}
