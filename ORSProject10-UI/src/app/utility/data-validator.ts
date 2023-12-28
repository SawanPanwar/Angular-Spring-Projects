import { Injectable } from '@angular/core';

@Injectable()
export class DataValidator {

  constructor() {
  }

  isNotNullObject(val: any) {
    let flag = true;
    try {
      if (val) {
        flag = true;
      } else {
        flag = false;
      }
    } catch (error) {
      flag = false;
    }
    return flag;
  }
}