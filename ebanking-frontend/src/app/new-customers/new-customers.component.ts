import {Component, OnInit} from '@angular/core';
import {Form, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomersService} from "../services/customers.service";
import {Customer} from "../model/customer.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-customers',
  templateUrl: './new-customers.component.html',
  styleUrls: ['./new-customers.component.css']
})
export class NewCustomersComponent implements OnInit{
  newCustomerFormGroup!:FormGroup;
  constructor(private fb :FormBuilder , private customerService: CustomersService , private router:Router) {
  }
  ngOnInit():void{
    this.newCustomerFormGroup= this.fb.group(
      {
        name:this.fb.control(null,[Validators.required , Validators.minLength(4)]),
        email:this.fb.control(null,[Validators.required , Validators.email]),
      }
    )
  }

  handleSaveCustomer() {
    let customer :Customer  = this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
      next: data=>{
        alert("Customer has been successfully saved");
        this.newCustomerFormGroup.reset();
        this.router.navigateByUrl("/customers");
      },
      error : err=>{
        console.log(err);
      }
    })
  }
}
