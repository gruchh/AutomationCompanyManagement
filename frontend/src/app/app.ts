import { Component, computed, inject } from '@angular/core';
import { Navbar } from './layout/navbar/navbar';
import { SidebarIcons } from './layout/sidebar-icons/sidebar-icons';
import { MainContent } from './layout/main-content/main-content';
import { LoginModal } from './shared/login-modal/login-modal';
import { LoginModalService } from './core/services/modal-service';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Navbar, SidebarIcons, MainContent, LoginModal],
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
