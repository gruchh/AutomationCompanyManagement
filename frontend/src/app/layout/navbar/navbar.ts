import { Component, inject } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.html',
})
export class Navbar {
  private auth = inject(AuthService);
  isLoggedIn = this.auth.isLoggedIn;
  user = this.auth.user;

  login() {
    this.auth.login();
  }

  logout() {
    this.auth.logout();
  }
}
