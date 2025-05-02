import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  credentials = {
    cpf: '00000000000',
    password: 'admin',
    role: 'ADMIN',
  };

  roles = [{ id: 'ADMIN', name: 'Administrador' }, {id: 'USER', name: 'Usuário'} ];

  constructor(private userService: UserService, private router: Router) {
    console.log('Roles: ', this.roles);
  }

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

  onRoleChange(event: Event) {
    const selectedRole = (event.target as HTMLSelectElement).value;
    console.log('Role changed to:', selectedRole);
  }
}
