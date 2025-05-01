import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ProductService, Product } from '../product.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
  imports: [RouterModule, CommonModule],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService, private router: Router) {}

  ngOnInit() {
    this.productService.getProducts().subscribe({
      next: (products) => (this.products = products),
      error: (err) => alert('Failed to fetch products: ' + err.message),
    });
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe({
      next: () => {
        this.products = this.products.filter((product) => product.productCode !== id);
      },
      error: (err) => alert('Failed to delete product: ' + err.message),
    });
  }

  navigateToCreate() {
    this.router.navigate(['/products/create']);
  }
}