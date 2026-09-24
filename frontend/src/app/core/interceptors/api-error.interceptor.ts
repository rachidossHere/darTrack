import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';

export const apiErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const handledError = (error: HttpErrorResponse) => {
    const message = error.error?.message ?? 'Une erreur est survenue.';
    console.error('API error:', message);
    return throwError(() => new Error(message));
  };

  return next(req).pipe(catchError(handledError));
};
