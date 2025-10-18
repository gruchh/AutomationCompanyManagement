import { Component, output, input, viewChild, ElementRef, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login-modal.html',
})
export class LoginModal {
  isOpen = input<boolean>(false);
  closeModal = output<void>();
  login = output<{ username: string; password: string }>();

  modalContent = viewChild<ElementRef>('modalContent');

  credentials = {
    username: '',
    password: '',
  };

  rememberMe = false;

  private elementRef = ElementRef;

  constructor() {
    effect(() => {
      if (this.isOpen()) {
        document.body.style.overflow = 'hidden';
      } else {
        document.body.style.overflow = '';
      }
    });
  }

  onOverlayClick(event: MouseEvent): void {
    const clickedElement = event.target as HTMLElement;
    const modalElement = this.modalContent()?.nativeElement;

    if (modalElement && !modalElement.contains(clickedElement)) {
      this.close();
    }
  }

  close(): void {
    this.closeModal.emit();
  }

  onSubmit(): void {
    if (this.credentials.username && this.credentials.password) {
      this.login.emit(this.credentials);
    }
  }
}
