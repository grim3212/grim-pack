import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { GrimPartService } from './grim-part.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule }     from './app-routing.module';
import { AboutComponent } from './about/about.component';
import { PartComponent } from './part/part.component';
import { CoreComponent } from './part/core/core.component';
import { CuisineComponent } from './part/cuisine/cuisine.component';
import { DecorComponent } from './part/decor/decor.component';
import { IndustryComponent } from './part/industry/industry.component';
import { ToolsComponent } from './part/tools/tools.component';
import { UtilComponent } from './part/util/util.component';
import { WorldComponent } from './part/world/world.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    PartComponent,
    CoreComponent,
    CuisineComponent,
    DecorComponent,
    IndustryComponent,
    ToolsComponent,
    UtilComponent,
    WorldComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    NgbModule.forRoot(),
    AppRoutingModule
  ],
  providers: [GrimPartService],
  bootstrap: [AppComponent]
})
export class AppModule { }
