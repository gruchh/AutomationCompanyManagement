import { Component, computed, inject } from '@angular/core';
import { Navbar } from './layout/navbar/navbar';
import { LoginModal } from './shared/login-modal/login-modal';
import { LoginModalService } from './core/services/modal-service';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Navbar, LoginModal, RouterOutlet],
  templateUrl: './app.html',
})
export class App {
  loginModalService = inject(LoginModalService);
  isLoginModalOpen = computed(() => this.loginModalService.isOpen()());

  onLogin($event: { username: string; password: string }) {
    console.log('Zalogowano jako:', $event.username);
    this.loginModalService.close();
  }

  closeLoginModal() {
    this.loginModalService.close();
  }
}
