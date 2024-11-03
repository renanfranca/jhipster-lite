import { provideHttpClient } from '@angular/common/http';
import { enableProdMode, importProvidersFrom } from '@angular/core';
import { REMOVE_STYLES_ON_COMPONENT_DESTROY, bootstrapApplication } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';

import { AppComponent } from './app/app.component';
import { routes } from './app/app.route';

import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    provideRouter(routes),
    importProvidersFrom([BrowserAnimationsModule]),
    { provide: REMOVE_STYLES_ON_COMPONENT_DESTROY, useValue: true },
  ],
}).catch((err: unknown) => console.error(err));
