import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProductService, Product } from '../product.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css'],
  imports: [RouterModule, CommonModule],
})
export class ProductDetailsComponent implements OnInit {
  product: Product | undefined;

  constructor(private route: ActivatedRoute, private productService: ProductService) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.productService.getProductById(+id).subscribe({
      next: (product) => (this.product = product),
      error: (err) => alert('Failed to fetch product details: ' + err.message),
    });
  }
}