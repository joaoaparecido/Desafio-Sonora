import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductService, Product, City } from '../product.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';

const uberlandiaCityId = 3170206; // Uberlândia city ID
@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css'],
  imports: [RouterModule, FormsModule, CommonModule],  
})
export class ProductFormComponent implements OnInit {
  product: Partial<Product> = { productName: '', productValue: 0, stock: 0, cityId: uberlandiaCityId };
  cities: City[] = [];
  states: string[] = [
    'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO',
  ];
  isEditMode = false;
  selectedState: string = 'MG';
  private readonly uberlandiaCityId = 3170206;


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
        next: (product) => this.setupProduct(product),
        error: (err) => alert('Failed to fetch product: ' + err.message),
      });
    } else {
      this.loadCities(this.selectedState)// Default to SP if cityUf is not set;
    }
  }  

  private setupProduct(product: Product): void {
     this.product = product;
     console.log('Product loaded:', product); // Debugging: Check the loaded product
     this.selectedState = product.cityUf || 'MG';
     this.loadCities(this.selectedState); 
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
  
  onStateChange(event: Event) {
    const state = (event.target as HTMLSelectElement).value;
    console.log('Selected State:', state);
    this.selectedState = state;
    this.loadCities(state); // Reload cities based on the selected state
  }

  loadCities(state: string) {
    this.productService.getCitiesByState(state).subscribe({
      next: (cities) => {
        this.cities = cities;
        console.log('Loaded cities:', cities); // Debugging: Check the fetched cities
      },
      error: (err) => alert('Failed to load cities: ' + err.message),
    });
  }
}