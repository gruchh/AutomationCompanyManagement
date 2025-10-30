import { Routes } from '@angular/router';
import { Mainpage } from './features/main-page/main-page';
import { ProfilePage } from './features/profile-page/profile-page';
import { canActivateAuthRole } from './core/guards/auth.guard';
import { ForbiddenPage } from './features/forbidden-page/forbidden-page';

export const routes: Routes = [
  { path: '', component: Mainpage },
  {
    path: 'profile',
    component: ProfilePage,
    canActivate: [canActivateAuthRole],
    data: { role: 'user' },
  },
  {
    path: 'forbidden',
    component: ForbiddenPage,
  },
];
