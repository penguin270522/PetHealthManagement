export interface BaseDTOSerivce{
  message:string,
  result:boolean,
  results:ServiceMedical[]
}
export interface BaseDTOQuestion{
  message:string,
  result:boolean,
  questionOutput:QuestionOutput;
}

export interface QuestionOutput {
  id: number
  urlUser: string
  statusQuestion: string
  typeQuestion: string
  title: string
  content: string
  commentOutput: any
  urlImage: any
  codeQuestion: string,
  fullName:string,
  createQuestionDate:string
}

export interface ServiceMedical{
  id: number,
  name: string,
  feeService : number,
  code:string
}
