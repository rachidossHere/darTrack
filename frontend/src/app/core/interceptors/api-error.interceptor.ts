import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const apiErrorInterceptor: HttpInterceptorFn = (_request, next) =>
  next(_request).pipe(
    catchError((error: HttpErrorResponse) => throwError(() => error))
  );
