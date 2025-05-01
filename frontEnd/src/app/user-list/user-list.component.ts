import { Component, OnInit } from '@angular/core';
import { UserService, User } from '../user.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class UserListComponent implements OnInit {
  users: User[] = [];

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.userService.getUsers().subscribe({
      next: (users) => (this.users = users),
      error: (err) => alert('Failed to fetch users: ' + err.message),
    });
  }

  deleteUser(id: number) {
    this.userService.deleteUser(id).subscribe({
      next: () => {
        alert('Usuário deletado com sucesso!');
        this.users = this.users.filter((user) => user.id !== id);
      },
      error: (err) => alert('Failed to delete user: ' + err.message),
    });
  }

  navigateToCreate() {
    this.router.navigate(['/users/create']);
  }
}
