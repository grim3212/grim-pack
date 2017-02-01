import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent }   from './home/home.component';
import { AboutComponent }   from './about/about.component';
import { PartComponent }   from './part/part.component';
import { CoreComponent }   from './part/core/core.component';
import { CuisineComponent }   from './part/cuisine/cuisine.component';
import { DecorComponent }   from './part/decor/decor.component';
import { IndustryComponent }   from './part/industry/industry.component';
import { ToolsComponent }   from './part/tools/tools.component';
import { UtilComponent }   from './part/util/util.component';
import { WorldComponent }   from './part/world/world.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home',  component: HomeComponent },
  { path: 'about',  component: AboutComponent },
  { path: 'part',  component: PartComponent,
    children: [
      { path: '', redirectTo: 'core', pathMatch: 'full' },
      { path: 'core', component: CoreComponent },
      { path: 'cuisine', component: CuisineComponent },
      { path: 'decor', component: DecorComponent },
      { path: 'industry', component: IndustryComponent },
      { path: 'tools', component: ToolsComponent },
      { path: 'util', component: UtilComponent },
      { path: 'world', component: WorldComponent }
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
