import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewCustomersComponent} from "./new-customers/new-customers.component";
import {CustomerAccountsComponent} from "./customer-accounts/customer-accounts.component";
import {LoginComponent} from "./login/login.component";
import {AdminTemplateComponent} from "./admin-template/admin-template.component";
import {AuthentificationGuard} from "./guards/authentification.guard";
import {AuthorizationGuard} from "./guards/authorization.guard";
import {NotAuthorizedComponent} from "./not-authorized/not-authorized.component";

const routes: Routes = [
  {path:"login", component: LoginComponent},
  {path:"",redirectTo:"login",pathMatch:"full"},
  {path:"admin",component:AdminTemplateComponent,canActivate:[AuthentificationGuard],children:[
      {path:"customers",component:CustomersComponent},
      {path:"accounts",component:AccountsComponent},
      {path:"new-customers",component:NewCustomersComponent,canActivate:[AuthorizationGuard],data:{role:"ADMIN"}},
      {path:"customer-accounts/:id",component:CustomerAccountsComponent},
      {path:"notAuthorized",component:NotAuthorizedComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
