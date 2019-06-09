import { NgModule } from '@angular/core';
import { Ng2SmartTableModule } from 'ng2-smart-table';

import { ThemeModule } from '../../@theme/theme.module';
import { SysMgrRouteModule, routedComponents } from './sysmgr-routes.module';

@NgModule({
  imports: [
    ThemeModule,
    SysMgrRouteModule,
    Ng2SmartTableModule,
  ],
  declarations: [
    ...routedComponents,
  ],
})
export class SysMgrModule {
  
}
