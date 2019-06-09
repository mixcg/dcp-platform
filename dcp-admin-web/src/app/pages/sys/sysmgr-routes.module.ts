import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SysMgrComponent } from './sysmgr.component';
import { UserMgrComponent } from './user-mgr/user-mgr.component';

const routes: Routes = [{
  path: '',
  component: SysMgrComponent,
  children: [{
    path: 'user-mgr',
    component: UserMgrComponent,
  }],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SysMgrRouteModule { }

export const routedComponents = [
  SysMgrComponent,
  UserMgrComponent,
];
