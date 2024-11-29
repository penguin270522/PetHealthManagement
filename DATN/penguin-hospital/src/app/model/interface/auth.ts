export interface LoginAuth{
  userName: string,
  password: string
}
export interface RegisterAuth{
  userName: string,
  password: string,
  phoneNumber: string,
  email: string,
  address: string
}

export interface BaseDTO{
  message : string,
  result: boolean,
  url: any
}

export interface JwtLoginDTO{
  baseDTO: BaseDTO,
  token : string,
  url: string,
  role: string,
  user: User
}
export interface User {
  id: number
  username: string
  password: string
  role: any
  address: string
  phoneNumber: string
  email: string
  birthday: any
  fullName:string
  cmnd:string
  genderUser:string
  createdBy: any
  updatedBy: any
  createdDate: number[]
  updatedDate: number[]
  image: any
  enabled: boolean
  authorities: any
  accountNonExpired: boolean
  credentialsNonExpired: boolean
  accountNonLocked: boolean
}


export interface UserReponse{
  baseDTO : BaseDTO,
  userList : any
}

export interface Doctor{
  id: number,
  username: string
}
export interface UserResponse {
  user: User;
}
