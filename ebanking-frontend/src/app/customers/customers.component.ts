import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomersService} from "../services/customers.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  public customers!: Observable<Array<Customer>>
  public  errorMessage!: string;
  searchFormGroup!: FormGroup;

  constructor(private http: HttpClient, private customerService: CustomersService, private fb :FormBuilder ) {
  }

  ngOnInit() {
    this.searchFormGroup = this.fb.group({
      keyword : this.fb.control("")
      }

    );
    this.handleSearchCustomer();
  }

  handleSearchCustomer() {
    let kw = this.searchFormGroup!.value.keyword;
    this.customers = this.customerService.searchCustomers(kw).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );

  }

  handleDeleteCustomer(c: Customer) {
    this.customerService.deleteCustomer(c.id).subscribe({
      next: (res )=>{
        this.customers = this.customers.pipe(
          map(customers => customers.filter(customer => customer.id !== c.id))
        )
      },
      error: (err) => {
        console.log(err);
      }
    });
    }
}

