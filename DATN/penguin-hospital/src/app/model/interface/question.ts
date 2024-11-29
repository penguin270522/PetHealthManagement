export interface QuestionPage{
  message:string;
  result:boolean;
  simpleResponese: SimpleResponese;
}
export interface SimpleResponese{
  results : QuestionOutput[];
  limit:number;
  page:number;
  totalItem:number;
  totalPage:number;
  message:string;
}
export interface QuestionOutput{
  id: number;
  urlUser:string;
  typeQuestion:string;
  statusQuestion:string;
  title:string;
  content:string;
  urlImage:string [];
  codeQuestion:string,
  fullName:string,
  createQuestionDate: string
}

export interface QuestionInput{
  doctorId: number,
  typeQuestion: number,
  title: string,
  content: string
}
