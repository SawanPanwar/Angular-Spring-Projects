import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class HttpServiceService {

  constructor(private httpClient: HttpClient) { }

  get(endpoint, callback) {
    return this.httpClient.get(endpoint).subscribe((data) => {
      console.log('get data => ', data);
      callback(data);
    });
  }

  post(endpoint, bean, callback) {
    return this.httpClient.post(endpoint, bean).subscribe((data) => {
      console.log('post data => ', data);
      callback(data);
    });
  }

  getPathVariable(route: ActivatedRoute, callback) {
    route.params.subscribe(params => {
      callback(params)
    });
  }
}
