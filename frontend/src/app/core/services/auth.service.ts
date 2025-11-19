import Keycloak from 'keycloak-js';
import { effect, inject, Injectable, signal } from '@angular/core';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType } from 'keycloak-angular';
import { User } from '../models/user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly keycloak = inject(Keycloak);
  private readonly keycloakSignal = inject(KEYCLOAK_EVENT_SIGNAL);
  user = signal<User | null>(null);
  isLoggedIn = signal(false);

  constructor() {
    effect(() => {
      const event = this.keycloakSignal();
      if (event.type === KeycloakEventType.Ready) {
        this.isLoggedIn.set(this.keycloak.authenticated);
      } else if (event.type === KeycloakEventType.AuthLogout) {
        this.isLoggedIn.set(false);
        this.user.set(null);
      }
    });
  }

  async loadUserProfile() {
    if (this.keycloak.authenticated) {
      const profile = await this.keycloak.loadUserProfile();
      this.user.set({
        name: `${profile?.firstName} ${profile?.lastName}`,
        email: profile?.email,
        username: profile?.username,
      });
    }
  }

  login() {
    this.keycloak.login();
  }

  logout() {
    this.isLoggedIn.set(false);
    this.user.set(null);
    this.keycloak.logout({ redirectUri: window.location.origin });
  }
}
