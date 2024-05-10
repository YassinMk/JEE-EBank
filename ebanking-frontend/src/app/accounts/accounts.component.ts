import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountsService} from "../services/accounts.service";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails} from "../model/account.model";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accountFormBuilder!:FormGroup;
  operationFormGroup!:FormGroup;
  currentPage:number = 0;
  pageSize:number = 5;
  accountObservable!:Observable<AccountDetails>;
  public errorMessage :string="";
  constructor(private fb:FormBuilder ,private accountService:AccountsService , public authService:AuthService) {
  }
  ngOnInit() {
    this.accountFormBuilder = this.fb.group({
      accountId:this.fb.control('')
    })
    this.operationFormGroup = this.fb.group({
      operationType:this.fb.control(''),
      amount:this.fb.control(0),
      description:this.fb.control(null),
      accountDestination:this.fb.control(null)

    })
  }

  handleSearchAccount() {
    let accountId = this.accountFormBuilder.value.accountId;
    this.accountObservable=this.accountService.getAccounts(accountId,this.currentPage,this.pageSize).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccount();
  }


  handleAccountOperation() {
    let accountId: string = this.accountFormBuilder.value.accountId;
    let operationType: string = this.operationFormGroup.value.operationType;
    let amount: number = this.operationFormGroup.value.amount;
    let description: string = this.operationFormGroup.value.description;
    let accountDestination: string = this.operationFormGroup.value.accountDestination;
    console.log('Operation Type:', operationType); // Add this line

    if (operationType == "DEBIT") {
      this.accountService.debit(accountId, amount,description).subscribe({
        next: (res) => {
          alert("Debit operation successful");
          this.handleSearchAccount();
        },
        error: (err) => {
          console.log(err);
        }
      });
    } else if (operationType == "CREDIT") {
      this.accountService.credit(accountId, amount,description).subscribe({
        next: (res) => {
          alert("Credit operation successful");
          this.handleSearchAccount();
        },
        error: (err) => {
          console.log(err);
        }
      });
    } else if (operationType == "TRANSFER") {
      this.accountService.transfer(accountId, accountDestination, amount,description).subscribe({
        next: (res) => {
          alert("Transfer operation successful");
          this.handleSearchAccount();
        },
        error: (err) => {
          console.log(err);
        }
      });
    }
  }
}
