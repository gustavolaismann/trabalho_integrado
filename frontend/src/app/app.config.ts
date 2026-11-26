import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { erroInterceptor } from './interceptors/erro.interceptor';
import { authInterceptor } from './interceptors/auth.interceptor';
import { routes } from './app.routes';

// Configuração global: rotas e HttpClient com os dois interceptors.
export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor, erroInterceptor])),
  ],
};
