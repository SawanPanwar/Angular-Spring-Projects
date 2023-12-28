import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpServiceService } from '../http-service.service';
import { DataValidator } from '../utility/data-validator';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  public form = {
    data: { id: null },
    message: '',
    preload: []
  };
  fileToUpload: File = null;
  inputerror = {};

  constructor(private httpClient: HttpClient, private httpService: HttpServiceService, private dataValidator: DataValidator, public route: ActivatedRoute) {
    var self = this;
    httpService.getPathVariable(route, function (params) {
      self.form.data.id = params["id"];
    })
  }

  ngOnInit() {
    this.preload();
    if (this.form.data.id && this.form.data.id > 0) {
      this.display();
    }
  }

  display() {
    var self = this;
    this.httpService.get('http://localhost:8080/User/get/' + self.form.data.id, function (res) {
      self.form.data = res.result.data;
    });
  }

  preload() {
    var self = this;
    this.httpService.get('http://localhost:8080/User/preload', function (res) {
      self.form.preload = res.result;
    });
  }

  onFileSelect(files: FileList) {
    this.fileToUpload = files.item(0);
    console.log(this.fileToUpload);
  }

  myFile() {
    var self = this;
    const formData = new FormData();
    formData.append('file', this.fileToUpload);
    return this.httpClient.post("http://localhost:8080/User/profilePic/" + this.form.data.id, formData).subscribe(data => {
      console.log(this.fileToUpload);
    }, error => {
      console.log(error);
    });;
  }

  submit() {

    var self = this;

    this.httpService.post('http://localhost:8080/User/save', this.form.data, function (res) {
      self.form.message = '';
      self.inputerror = {};
      if (self.dataValidator.isNotNullObject(res.result.inputerror)) {
        self.inputerror = res.result.inputerror;
      }
      self.form.data.id = res.result.data;
      self.myFile();
      if (self.dataValidator.isNotNullObject(res.result.message)) {
        self.form.message = res.result.message;
      }
    });
  }
}
