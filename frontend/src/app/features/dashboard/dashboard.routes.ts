import { Routes } from '@angular/router';
import { canActivateAuthRole } from '../../core/guards/auth.guard';
import { Dashboard } from './dashboard';

export const DASHBOARD_ROUTES: Routes = [
  {
    path: '',
    component: Dashboard,
    children: [{ path: '', component: Dashboard }],
    canActivate: [canActivateAuthRole],
    data: { role: 'admin' },
  },
];
