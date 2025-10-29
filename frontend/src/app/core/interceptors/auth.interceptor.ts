import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import Keycloak from 'keycloak-js';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const keycloak = inject(Keycloak);

  if (req.url.includes('/public') || req.url.includes('/assets')) {
    return next(req);
  }

  if (keycloak.authenticated && keycloak.token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${keycloak.token}`
      }
    });
    return next(cloned);
  }

  return next(req);
};
