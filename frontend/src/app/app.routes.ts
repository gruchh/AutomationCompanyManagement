import { Routes } from '@angular/router';
import { Home } from './features/home/home';
import { Forbidden } from './features/forbidden/forbidden';
import { canActivateAuthRole } from './core/guards/auth.guard';
import { HomeLayout } from './layout/home-layout/home-layout';
import { DashboardLayout } from './layout/dashboard-layout/dashboard-layout';

export const routes: Routes = [
  {
    path: '',
    component: HomeLayout,
    children: [
      {
        path: '',
        loadChildren: () => import('./features/home/home.routes').then((m) => m.HOME_ROUTES),
      },
    ],
  },
  {
    path: 'dashboard',
    component: DashboardLayout,
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./features/dashboard/dashboard.routes').then((m) => m.DASHBOARD_ROUTES),
      },
    ],
  },
  {
    path: 'profile',
    loadChildren: () => import('./features/profile/profile.routes').then((m) => m.PROFILE_ROUTES),
    canActivate: [canActivateAuthRole],
    data: { role: 'user' },
  },
  {
    path: 'forbidden',
    component: Forbidden,
  },
  { path: '**', redirectTo: '' },
];
