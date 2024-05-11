import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {jwtDecode} from "jwt-decode";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated:boolean = false;
  roles:any;
  username:any;
  accessToken!:any;


  constructor(private http:HttpClient, private route:Router) { }
  public login(username:string,password:string){
    let options  ={
      headers: new HttpHeaders().set('Content-Type','application/x-www-form-urlencoded')
    }
    let params = new HttpParams().set('username',username).set('password',password);
    return this.http.post(environment.backendHost +"/auth/login",params,options);
  }

  laodProfile(data :any) {
    this.accessToken = data['access_token'];
    this.isAuthenticated = true;
    let decodedJwt:any = jwtDecode(this.accessToken);
    this.username = decodedJwt.sub;
    this.roles = decodedJwt.scope;
    window.localStorage.setItem("jwt-token",this.accessToken);

  }

  logout() {
    this.isAuthenticated = false;
    this.accessToken = undefined;
    this.username = undefined;
    this.roles = undefined;
  }

  loadJwtTokenFromLocalStorage() {
    let token = window.localStorage.getItem("jwt-token");
    if (token) {
      this.laodProfile({"access_token": token})
      this.route.navigateByUrl("/admin/customers");
    }else{
      this.route.navigateByUrl("/login");
    }
  }
}
