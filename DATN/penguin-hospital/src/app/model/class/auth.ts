export class LoginAuth {
  userName: string;
  password: string;

  constructor(userName: string = '', password: string = '') {
    this.userName = userName;
    this.password = password;
  }
}

export class RegisterAuth {
  userName: string;
  password: string;
  phoneNumber: string;
  email: string;
  address: string;

  constructor(
    userName: string = '',
    password: string = '',
    phoneNumber: string = '',
    email: string = '',
    address: string = ''
  ) {
    this.userName = userName;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.address = address;
  }


}



export class User {
  id: number = 0;
  username: string = '';
  password: string = '';
  role: Role = new Role(0,'','');
  address: string = '';
  phoneNumber: string = '';
  email: string = '';
  birthday: any = null;
  firstName: any = null;
  lastName: any = null;
  createdBy: any = null;
  updatedBy: any = null;
  createdDate: number[] = [];
  updatedDate: number[] = [];
  image: Image = new Image(0, '', null, null, null);
  enabled: boolean = true;
  authorities: Authority[] = [];
  accountNonExpired: boolean  = true;
  credentialsNonExpired: boolean  = true;
  accountNonLocked: boolean  = true;

}

export class Role {
  id: number;
  name: string;
  code: string;

  constructor(id: number, name: string, code: string) {
    this.id = id;
    this.name = name;
    this.code = code;
  }

  getRoleName(): string {
    return this.name;
  }
}
export class Image {
  id: number;
  url: string;
  pet: any;
  product: any;
  comment: any;

  constructor(id: number, url: string, pet: any, product: any, comment: any) {
    this.id = id;
    this.url = url;
    this.pet = pet;
    this.product = product;
    this.comment = comment;
  }

  getImageUrl(): string {
    return this.url;
  }
}
export class Authority {
  authority: string;

  constructor(authority: string) {
    this.authority = authority;
  }

  getAuthority(): string {
    return this.authority;
  }
}
