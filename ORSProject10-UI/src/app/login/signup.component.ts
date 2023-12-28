import { Component, OnInit } from '@angular/core';
import { HttpServiceService } from '../http-service.service';
import { DataValidator } from '../utility/data-validator';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html'
})
export class SignupComponent implements OnInit {

  public form = {
    data: { id: null },
    message: ''
  };

  inputerror = {};

  constructor(private httpService: HttpServiceService, private dataValidator: DataValidator) { }

  ngOnInit() {
  }

  signUp() {

    var self = this;

    this.httpService.post('http://localhost:8080/Auth/signUp', this.form.data, function (res) {

      self.form.message = '';
      self.inputerror = {};

      if (self.dataValidator.isNotNullObject(res.result.inputerror)) {
        self.inputerror = res.result.inputerror;
      }
      if (self.dataValidator.isNotNullObject(res.result.message)) {
        self.form.message = res.result.message;
      }
    });
  }
}
