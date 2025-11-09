import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { MessageDto, MessageDtoCategoryEnum, MessageDtoPriorityEnum } from '../../employees/service/generated/employee/model/message-dto';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = '/api/messages';
  private messageSubject = new Subject<MessageDto>();
  private webSocket?: WebSocket;

  http = inject(HttpClient);

  private initializeWebSocket(): void {
    this.webSocket = new WebSocket('ws://localhost:8080/ws/messages');

    this.webSocket.onmessage = (event) => {
      const messageDTO = JSON.parse(event.data);
      const message = this.mapDTOToMessage(messageDTO);
      this.messageSubject.next(message);
    };
  }

  getMessages(): Observable<MessageDto[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map(dtos => dtos.map(dto => this.mapDTOToMessage(dto)))
    );
  }

  getMessageStream(): Observable<MessageDto> {
    return this.messageSubject.asObservable();
  }

  markAsRead(id: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/read`, {});
  }

  markAllAsRead(): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/read-all`, {});
  }

  deleteMessage(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  sendMessage(recipientId: number, subject: string, content: string, category: MessageDtoCategoryEnum, priority: MessageDtoCategoryEnum): Observable<Message> {
    return this.http.post<any>(this.apiUrl, {
      recipientId,
      subject,
      content,
      category,
      priority
    }).pipe(
      map(dto => this.mapDTOToMessage(dto))
    );
  }

  private mapDTOToMessage(dto: any): MessageDto {
    return {
      id: dto.id,
      type: dto.senderId ? 'user' : 'system',
      category: this.determineCategory(dto.subject),
      title: dto.subject,
      message: dto.content,
      time: this.formatTime(dto.sentAt),
      isRead: dto.isRead,
      priority: this.determinePriority(dto.subject),
      senderName: dto.senderName,
      senderId: dto.senderId,
      recipientId: dto.recipientId,
      sentAt: dto.sentAt,
      readAt: dto.readAt
    };
  }

  private determineCategory(subject: string): MessageDtoCategoryEnum {
    const lower = subject.toLowerCase();
    if (lower.includes('campaign') || lower.includes('email')) return 'campaign';
    if (lower.includes('analytics') || lower.includes('rate')) return 'analytics';
    if (lower.includes('alert') || lower.includes('urgent')) return 'alert';
    if (lower.includes('budget') || lower.includes('cost')) return 'budget';
    if (lower.includes('reminder') || lower.includes('scheduled')) return 'reminder';
    if (lower.includes('leads') || lower.includes('subscribers')) return 'leads';
    if (lower.includes('expiring') || lower.includes('expires')) return 'expiring';
    return 'general';
  }

  private determinePriority(subject: string): MessageDtoPriorityEnum {
    const lower = subject.toLowerCase();
    if (lower.includes('urgent') || lower.includes('critical') || lower.includes('alert')) return 'high';
    if (lower.includes('important') || lower.includes('reminder')) return 'medium';
    return 'low';
  }

  private formatTime(dateString: string): string {
    const date = new Date(dateString);
    const now = new Date();
    const diffMs = now.getTime() - date.getTime();
    const diffMins = Math.floor(diffMs / 60000);

    if (diffMins < 60) return `${diffMins} minutes ago`;
    if (diffMins < 1440) return `${Math.floor(diffMins / 60)} hours ago`;

    return date.toLocaleDateString('en-US', {
      month: 'long',
      day: 'numeric',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}
