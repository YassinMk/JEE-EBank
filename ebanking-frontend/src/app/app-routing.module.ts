import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewCustomersComponent} from "./new-customers/new-customers.component";

const routes: Routes = [
  {path:"customers",component:CustomersComponent},
  {path:"accounts",component:AccountsComponent},
  {path:"new-customers",component:NewCustomersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
