import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoginModalService {
  private isOpenSignal = signal(false);

  isOpen() {
    return this.isOpenSignal.asReadonly();
  }

  open() {
    this.isOpenSignal.set(true);
  }

  close() {
    this.isOpenSignal.set(false);
  }

  toggle() {
    this.isOpenSignal.update(v => !v);
  }
}
