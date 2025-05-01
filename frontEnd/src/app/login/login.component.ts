import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  credentials = {
    cpf: '00000000000', // Default CPF value
    password: 'admin',  // Default password value
  };

  constructor(private userService: UserService, private router: Router) {}

  login() {
    this.userService.login(this.credentials).subscribe({
      next: (response) => {
        localStorage.setItem('authToken', response.token); // Store JWT token
        this.router.navigate(['/users']); // Redirect to users page
      },
      error: (err) => {
        alert('Login failed: ' + err.error.message);
      },
    });
  }
}
