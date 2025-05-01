import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductService, Product, City } from '../product.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css'],
  imports: [RouterModule, FormsModule, CommonModule, MatOptionModule, MatSelectModule],  
})
export class ProductFormComponent implements OnInit {
  product: Partial<Product> = { productName: '', productValue: 0, stock: 0, cityId: 0 };
  cities: City[] = [];
  isEditMode = false;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.productService.getProductById(+id).subscribe({
        next: (product) => (this.product = product),
        error: (err) => alert('Failed to fetch product: ' + err.message),
      });
    }

    this.productService.getCities().subscribe({
      next: (cities) => {
        this.cities = cities;
     
      },
      error: (err) => alert('Failed to fetch cities: ' + err.message),
    });
  }

  saveProduct() {
    if (this.isEditMode) {
      this.productService.updateProduct(this.product.productCode!, this.product).subscribe({
        next: () => this.router.navigate(['/products']),
        error: (err) => alert('Failed to update product: ' + err.message),
      });
    } else {
      this.productService.createProduct(this.product).subscribe({
        next: () => this.router.navigate(['/products']),
        error: (err) => alert('Failed to create product: ' + err.message),
      });
    }
  }
}