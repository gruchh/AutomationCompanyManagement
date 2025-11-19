import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { MessageDto, MessageDtoCategoryEnum, MessageDtoPriorityEnum, MessageDtoTypeEnum } from '../../employees/service/generated/employee/model/message-dto';
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = `${environment.api.baseUrl}/messages`;
  private wsUrl = environment.api.baseUrl.replace('http', 'ws').replace('/api', '');
  private messageSubject = new Subject<MessageDto>();
  private webSocket?: WebSocket;

  http = inject(HttpClient);

  constructor() {
    this.initializeWebSocket();
  }

  private initializeWebSocket(): void {
    this.webSocket = new WebSocket(`${this.wsUrl}/ws/messages`);

    this.webSocket.onmessage = (event) => {
      const message: MessageDto = JSON.parse(event.data);
      this.messageSubject.next(message);
    };

    this.webSocket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    this.webSocket.onclose = () => {
      console.log('WebSocket connection closed');
      setTimeout(() => this.initializeWebSocket(), 5000);
    };
  }

  getMessages(): Observable<MessageDto[]> {
    return this.http.get<MessageDto[]>(this.apiUrl);
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

  sendMessage(
    recipientId: number,
    subject: string,
    content: string,
    category: MessageDtoCategoryEnum,
    priority: MessageDtoPriorityEnum
  ): Observable<MessageDto> {
    return this.http.post<MessageDto>(this.apiUrl, {
      recipientId,
      subject,
      content,
      category,
      priority
    });
  }

  disconnect(): void {
    if (this.webSocket) {
      this.webSocket.close();
    }
  }
}
