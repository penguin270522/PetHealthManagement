export interface CommentOutPut {
  message: string
  result: boolean
  results: Result[]
}

export interface Result {
  id: number
  urlImageUser: string
  nameUser: string
  content: string
  listCommentChild: ListCommentChild[]
  showChildComment:boolean
}

export interface ListCommentChild {
  id: number
  urlUser: string
  nameUser: string
  content: string
}

export class CommentInput{
  content:string = ''
  postId:number = 0
  parentCommentId:number = 0
  questionId:number = 0
}
