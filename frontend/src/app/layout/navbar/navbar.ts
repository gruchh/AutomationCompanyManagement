import { LoginModalService } from './../../core/services/modal-service';
import { Component, inject } from '@angular/core';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.html',
})
export class Navbar {

  loginModalService = inject(LoginModalService);

  openLoginModal() {
    this.loginModalService.open();
  }
}
