import { Component, ElementRef, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { HttpServiceService } from '../http-service.service';
import { DataValidator } from '../utility/data-validator';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {

  @ViewChildren("checkboxes") checkboxes: QueryList<ElementRef>;
  deleteRecordList: any = [];
  isMasterSel: boolean = false;

  form = {
    pageNo: 0,
    data: { id: null },
    searchParams: {},
    list: [],
    preload: []
  }

  constructor(private httpService: HttpServiceService, private router: Router) { }

  ngOnInit() {
    this.preload();
    this.search();
  }

  preload() {
    var self = this;
    this.httpService.get('http://localhost:8080/User/preload', function (res) {
      self.form.preload = res.result;
    });
  }

  search() {
    var self = this;
    this.httpService.post('http://localhost:8080/User/search/' + self.form.pageNo, self.form.searchParams, function (res) {
      self.form.list = res.result.data;
    });
  }

  next() {
    this.form.pageNo++;
    this.search();
  }

  previous() {
    this.form.pageNo--;
    this.search();
  }

  add() {
    this.router.navigateByUrl('/user');
  }

  delete() {
    var self = this;
    this.deleteRecordList.length = 0;
    this.checkboxes.forEach((element) => {
      if (element.nativeElement.checked) {
        this.deleteRecordList.push(element.nativeElement.id);
      }
    });
    this.httpService.post('http://localhost:8080/User/deleteMany/' + this.deleteRecordList + '?pageNo=' + self.form.pageNo, self.form.searchParams, function (res) {
      self.form.list = res.result.data;
    });
  }

  edit(page) {
    this.router.navigateByUrl(page);
  }
}
