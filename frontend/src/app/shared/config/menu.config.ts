import { type LucideIconData, LayoutDashboard, Inbox, Calendar, MessageCircle } from 'lucide-angular';

export interface MenuItem {
  icon: LucideIconData;
  label: string;
  route: string;
}

export const MENU_ITEMS: MenuItem[] = [
  { icon: LayoutDashboard, label: 'Dashboard', route: '/dashboard' },
  { icon: Inbox, label: 'Pracownicy', route: '/dashboard/employees' },
  { icon: Calendar, label: 'Projekty', route: '/dashboard/projects' },
  { icon: MessageCircle, label: 'Wiadomości', route: '/dashboard/messages' },
];
