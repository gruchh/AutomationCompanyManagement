import { Component, inject } from '@angular/core';
import Keycloak from 'keycloak-js';
import { User } from '../../core/models/user';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [],
  templateUrl: './profile.html',
})
export class Profile {
  private readonly keycloak = inject(Keycloak);

  user: User | undefined;

  async ngOnInit() {
    if (this.keycloak?.authenticated) {
      const profile = await this.keycloak.loadUserProfile();

      this.user = {
        name: `${profile?.firstName} ${profile.lastName}`,
        email: profile?.email,
        username: profile?.username,
      };
    }
  }
}
