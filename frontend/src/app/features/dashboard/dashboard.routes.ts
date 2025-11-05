import { Routes } from '@angular/router';
import { Dashboard } from './dashboard';
import { Employees } from './employees/employees';
import { Projects } from './projects/projects';
import { Messages } from './messages/messages';

export const DASHBOARD_ROUTES: Routes = [
  {
    path: '',
    component: Dashboard,
  },
  {
    path: 'employees',
    component: Employees,
  },
  {
    path: 'projects',
    component: Projects,
  },
  {
    path: 'messages',
    component: Messages,
  },
];
