import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { GrimPartService } from '../grim-part.service';
import { GrimPart } from '../grim-part';

@Component({
  selector: 'app-part',
  templateUrl: './part.component.html',
  styleUrls: ['./part.component.css']
})
export class PartComponent implements OnInit {

  private currentPart: GrimPart;

  constructor(private route: ActivatedRoute, private partService : GrimPartService) {
  }

  ngOnInit() {

    console.log(this.route.firstChild.data);

    this.route.firstChild.data.subscribe(data => {
      console.log(data);
    });
  }

}
