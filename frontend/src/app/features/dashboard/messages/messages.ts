import { Component, OnInit, OnDestroy, signal, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { MessageDTODto } from '../../../core/api/generated/employee';

@Component({
  selector: 'app-messages',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './messages.html',
})
export class Messages implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  notifications = signal<MessageDTODto[]>([]);
  filter = signal<'all' | 'unread' | 'system' | 'users'>('all');
  searchQuery = signal<string>('');

  messageSerrvice = inject(MessageService);

  unreadCount = computed(() =>
    this.notifications().filter(n => !n.isRead).length
  );

  filteredNotifications = computed(() => {
    const query = this.searchQuery().toLowerCase();
    return this.notifications().filter(n => {
      const matchesFilter =
        this.filter() === 'all' ||
        (this.filter() === 'unread' && !n.isRead) ||
        (this.filter() === 'system' && n.type === 'system') ||
        (this.filter() === 'users' && n.type === 'user');

      const matchesSearch =
        n.title.toLowerCase().includes(query) ||
        n.message.toLowerCase().includes(query) ||
        n.senderName?.toLowerCase().includes(query);

      return matchesFilter && matchesSearch;
    });
  });

  ngOnInit(): void {
    this.loadMessages();
    this.subscribeToNewMessages();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadMessages(): void {
    this.messageService.getMessages()
      .pipe(takeUntil(this.destroy$))
      .subscribe(messages => {
        this.notifications.set(messages);
      });
  }

  subscribeToNewMessages(): void {
    this.messageService.getMessageStream()
      .pipe(takeUntil(this.destroy$))
      .subscribe(newMessage => {
        this.notifications.update(messages => [newMessage, ...messages]);
        this.showNotificationToast(newMessage);
      });
  }

  markAsRead(id: number): void {
    this.messageService.markAsRead(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.notifications.update(messages =>
          messages.map(m => m.id === id ? { ...m, isRead: true } : m)
        );
      });
  }

  markAllAsRead(): void {
    this.messageService.markAllAsRead()
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.notifications.update(messages =>
          messages.map(m => ({ ...m, isRead: true }))
        );
      });
  }

  deleteNotification(id: number): void {
    this.messageService.deleteMessage(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.notifications.update(messages =>
          messages.filter(m => m.id !== id)
        );
      });
  }

  getCategoryColor(category: MessageCategory): string {
    const colors: Record<MessageCategory, string> = {
      campaign: 'bg-blue-500',
      analytics: 'bg-green-500',
      alert: 'bg-red-500',
      budget: 'bg-yellow-500',
      reminder: 'bg-purple-500',
      leads: 'bg-teal-500',
      expiring: 'bg-orange-500',
      general: 'bg-gray-500'
    };
    return colors[category] || 'bg-gray-500';
  }

  getCategoryIcon(category: MessageCategory): string {
    const icons: Record<MessageCategory, string> = {
      campaign: '📧',
      analytics: '📈',
      alert: '⚠️',
      budget: '💰',
      reminder: '📅',
      leads: '👥',
      expiring: '📦',
      general: '💬'
    };
    return icons[category] || '💬';
  }

  getInitials(name?: string): string {
    if (!name) return 'SY';
    return name.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2);
  }

  setFilter(filter: 'all' | 'unread' | 'system' | 'users'): void {
    this.filter.set(filter);
  }

  updateSearchQuery(query: string): void {
    this.searchQuery.set(query);
  }

  showNotificationToast(message: Message): void {
    if ('Notification' in window && Notification.permission === 'granted') {
      new Notification(message.title, {
        body: message.message,
        icon: '/assets/icons/notification.png'
      });
    }
  }

  requestNotificationPermission(): void {
    if ('Notification' in window && Notification.permission === 'default') {
      Notification.requestPermission();
    }
  }

  getFilterCount(filter: 'all' | 'unread' | 'system' | 'users'): number {
    if (filter === 'all') return this.notifications().length;
    if (filter === 'unread') return this.unreadCount();
    if (filter === 'system') return this.notifications().filter(n => n.type === 'system').length;
    if (filter === 'users') return this.notifications().filter(n => n.type === 'user').length;
    return 0;
  }
}

