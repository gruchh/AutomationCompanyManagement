import { Component, effect, inject } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.html',
})
export class Navbar {
  private auth = inject(AuthService);

  user = this.auth.user;
  isLoggedIn = this.auth.isLoggedIn;

  constructor() {
    effect(() => {
      if (this.isLoggedIn()) {
        this.auth.loadUserProfile();
      }
    });
  }

  login() {
    this.auth.login();
  }

  logout() {
    this.auth.logout();
  }
}
