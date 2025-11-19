import { Component, OnInit, OnDestroy, signal, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { MessageDto, MessageDtoCategoryEnum, MessageDtoPriorityEnum, MessageDtoTypeEnum } from '../employees/service/generated/employee';
import { MessageService } from './service/message.service';

@Component({
  selector: 'app-messages',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './messages.html',
})
export class Messages implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  notifications = signal<MessageDto[]>([]);
  filter = signal<'all' | 'unread' | 'system' | 'users'>('all');
  searchQuery = signal<string>('');

  messageService = inject(MessageService);

  unreadCount = computed(() =>
    this.notifications().filter(n => !n.isRead).length
  );

  filteredNotifications = computed(() => {
    const query = this.searchQuery().toLowerCase();
    return this.notifications().filter(n => {
      const matchesFilter =
        this.filter() === 'all' ||
        (this.filter() === 'unread' && !n.isRead) ||
        (this.filter() === 'system' && n.type === MessageDtoTypeEnum.SYSTEM) ||
        (this.filter() === 'users' && n.type === MessageDtoTypeEnum.USER);

      const matchesSearch =
        (n.subject?.toLowerCase().includes(query) ?? false) ||
        (n.content?.toLowerCase().includes(query) ?? false) ||
        (n.senderName?.toLowerCase().includes(query) ?? false);

      return matchesFilter && matchesSearch;
    });
  });

  ngOnInit(): void {
    this.loadMessages();
    this.subscribeToNewMessages();
    this.requestNotificationPermission();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    this.messageService.disconnect();
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

  markAsRead(id?: number): void {
    if (!id) return;

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

  deleteNotification(id?: number): void {
    if (!id) return;

    this.messageService.deleteMessage(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.notifications.update(messages =>
          messages.filter(m => m.id !== id)
        );
      });
  }

  getCategoryColor(category?: MessageDtoCategoryEnum): string {
    if (!category) return 'bg-gray-500';

    const colors: Record<MessageDtoCategoryEnum, string> = {
      [MessageDtoCategoryEnum.CAMPAIGN]: 'bg-blue-500',
      [MessageDtoCategoryEnum.ANALYTICS]: 'bg-green-500',
      [MessageDtoCategoryEnum.ALERT]: 'bg-red-500',
      [MessageDtoCategoryEnum.BUDGET]: 'bg-yellow-500',
      [MessageDtoCategoryEnum.REMINDER]: 'bg-purple-500',
      [MessageDtoCategoryEnum.LEADS]: 'bg-teal-500',
      [MessageDtoCategoryEnum.EXPIRING]: 'bg-orange-500',
      [MessageDtoCategoryEnum.GENERAL]: 'bg-gray-500'
    };
    return colors[category] || 'bg-gray-500';
  }

  getCategoryIcon(category?: MessageDtoCategoryEnum): string {
    if (!category) return '💬';

    const icons: Record<MessageDtoCategoryEnum, string> = {
      [MessageDtoCategoryEnum.CAMPAIGN]: '📧',
      [MessageDtoCategoryEnum.ANALYTICS]: '📈',
      [MessageDtoCategoryEnum.ALERT]: '⚠️',
      [MessageDtoCategoryEnum.BUDGET]: '💰',
      [MessageDtoCategoryEnum.REMINDER]: '📅',
      [MessageDtoCategoryEnum.LEADS]: '👥',
      [MessageDtoCategoryEnum.EXPIRING]: '📦',
      [MessageDtoCategoryEnum.GENERAL]: '💬'
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

  formatTime(dateString?: string): string {
    if (!dateString) return 'Unknown time';

    const date = new Date(dateString);
    const now = new Date();
    const diffMs = now.getTime() - date.getTime();
    const diffMins = Math.floor(diffMs / 60000);

    if (diffMins < 1) return 'Just now';
    if (diffMins < 60) return `${diffMins} minute${diffMins === 1 ? '' : 's'} ago`;
    if (diffMins < 1440) {
      const hours = Math.floor(diffMins / 60);
      return `${hours} hour${hours === 1 ? '' : 's'} ago`;
    }

    return date.toLocaleDateString('en-US', {
      month: 'long',
      day: 'numeric',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  showNotificationToast(message: MessageDto): void {
    if ('Notification' in window && Notification.permission === 'granted') {
      new Notification(message.subject || 'New message', {
        body: message.content || '',
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
    if (filter === 'system') return this.notifications().filter(n => n.type === MessageDtoTypeEnum.SYSTEM).length;
    if (filter === 'users') return this.notifications().filter(n => n.type === MessageDtoTypeEnum.USER).length;
    return 0;
  }
}
