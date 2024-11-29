import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const check_token = localStorage.getItem('token');
  const check_role = localStorage.getItem('role');

  if (check_token != null && check_role != null) {
    const requiredRole = route.data['role'];

    if (requiredRole && check_role !== requiredRole) {
      router.navigateByUrl('unauthorized');
      return false;
    }

    return true;
  } else {
    router.navigateByUrl('login');
    return false;
  }
};
