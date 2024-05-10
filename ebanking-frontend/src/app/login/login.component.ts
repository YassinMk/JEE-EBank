import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  formLogin!:FormGroup;
  constructor(private fb: FormBuilder,private auth:AuthService, private route:Router) { }

  ngOnInit(): void {
    this.formLogin = this.fb.group({
      username: this.fb.control(''),
      password: this.fb.control('')
    });

  }

  handleLogin() {
    let username = this.formLogin.value.username;
    let password = this.formLogin.value.password;
    this.auth.login(username,password).subscribe({
      next: (data) => {
        this.auth.laodProfile(data);
        this.route.navigateByUrl("/admin")
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
