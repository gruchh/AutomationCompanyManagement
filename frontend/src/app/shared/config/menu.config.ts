import { type LucideIconData, LayoutDashboard, Inbox, Calendar } from 'lucide-angular';

export interface MenuItem {
  icon: LucideIconData;
  label: string;
  route: string;
}

export const MENU_ITEMS: MenuItem[] = [
  { icon: LayoutDashboard, label: 'Dashboard', route: '/dashboard' },
  { icon: Inbox, label: 'Employees', route: '/dashboard/employees' },
  { icon: Calendar, label: 'Projects', route: '/dashboard/projects' },
];
