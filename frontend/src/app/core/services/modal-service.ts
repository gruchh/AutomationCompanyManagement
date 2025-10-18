import { Injectable, ViewContainerRef, Type, signal } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  public isModalOpen = signal(false);

  public open(): void {
    this.isModalOpen.set(true);
  }

  public close(): void {
    this.isModalOpen.set(false);
  }
}