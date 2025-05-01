import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, Routes } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { UserListComponent } from './app/user-list/user-list.component';
import { UserFormComponent } from './app/user-form/user-form.component';
import { UserDetailsComponent } from './app/user-details/user-details.component';
import { AuthInterceptor } from './app/auth.interceptor';
import { LoginComponent } from './app/login/login.component';
import { AuthGuard } from './app/auth.guard';
import { ProductDetailsComponent } from './app/product-details/product-details.component';
import { ProductFormComponent } from './app/product-form/product-form.component';
import { ProductListComponent } from './app/product-list/product-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'users', component: UserListComponent, canActivate: [AuthGuard] },
  { path: 'users/create', component: UserFormComponent, canActivate: [AuthGuard] },
  { path: 'users/edit/:id', component: UserFormComponent, canActivate: [AuthGuard] },
  { path: 'users/:id', component: UserDetailsComponent, canActivate: [AuthGuard] },
  { path: 'products', component: ProductListComponent , canActivate: [AuthGuard]},
  { path: 'products/create', component: ProductFormComponent , canActivate: [AuthGuard]},
  { path: 'products/edit/:id', component: ProductFormComponent , canActivate: [AuthGuard]},
  { path: 'products/:id', component: ProductDetailsComponent , canActivate: [AuthGuard]},
  { path: '**', redirectTo: 'login' },
];

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([AuthInterceptor])),
    provideRouter(routes),
  ],
}).catch((err) => console.error(err));