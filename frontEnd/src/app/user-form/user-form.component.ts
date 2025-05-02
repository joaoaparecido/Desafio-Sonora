import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UserService, User } from '../user.service';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ]
})
export class UserFormComponent implements OnInit {
  user: Partial<User> = { name: '', cpf: '', role: '', password: '' };
  isEditMode = false;
  roles = [{ id: 'ADMIN', name: 'Administrador' }, {id: 'USER', name: 'Usuário'} ];

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.userService.getUserById(+id).subscribe({
        next: (user) => {
          this.user = user;
          this.user.password = ''; // Do not prefill the password field
        },
        error: (err) => alert('Failed to fetch user: ' + err.message),
      });
    }
  }

  saveUser() {
    if (this.isEditMode) {
      this.userService.updateUser(this.user.id!, this.user).subscribe({
        next: () => this.router.navigate(['/users']),
        error: (err) => alert('Failed to update user: ' + err.message),
      });
    } else {
      this.userService.createUser(this.user).subscribe({
        next: () => this.router.navigate(['/users']),
        error: (err) => alert('Failed to create user: ' + err.message),
      });
    }
  }
}
