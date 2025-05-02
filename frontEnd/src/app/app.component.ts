import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {

  constructor(private router: Router) {
  }
  logout() {
    // Clear the authentication token from localStorage
    localStorage.removeItem('authToken');

    // Redirect the user to the login page
    this.router.navigate(['/login']);
  }
}