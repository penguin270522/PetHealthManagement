import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { QuestionService } from '../../../../services/question.service';
import { CommentService } from '../../../../services/comment.service';
import { QuestionInput, QuestionOutput, QuestionPage } from '../../../../model/interface/question';
import { CommentInput, CommentOutPut, Result } from '../../../../model/interface/comment';
import { BaseDTOQuestion } from '../../../../model/interface/baseDTO';
import { BaseDTO } from '../../../../model/interface/auth';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProfileService } from '../../../../services/profile.service';

@Component({
  selector: 'app-question',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule,FormsModule],
  templateUrl: './question.component.html',
  styleUrl: './question.component.scss'
})
export class QuestionComponent implements OnInit {
  userId = localStorage.getItem('user_id');
  avatar: string = localStorage.getItem('avatarUser')?? ''
  fullName: string = localStorage.getItem('fullNameUser') ?? ''
  ngOnInit(): void {
    this.onLoadingQuestion();
    debugger;
    const storedAvatar = localStorage.getItem('avatarProfileUser');
    const storedFullName = localStorage.getItem('fullNameProfile');
    if (storedAvatar) {
      this.avatar = storedAvatar;
    }
    if (storedFullName) {
      this.fullName = storedFullName;
    }
    this.userProfileService.currentAvatar.subscribe(avatar => {
      if (avatar) {
        this.avatar = avatar;
        localStorage.setItem('avatarProfileUser', avatar);
      }
    });

    this.userProfileService.currentFullName.subscribe(fullName => {
      if (fullName) {
        this.fullName = fullName;
        localStorage.setItem('fullNameProfile', fullName);
      }
    });
  }
  questionService = inject(QuestionService)
  userProfileService = inject(ProfileService)
  commentService = inject(CommentService)
  questionDetail!:QuestionOutput;
  commentParen!:Result[];
  commentChilrend!: QuestionInput[];
  showDetailQuestion:boolean = false;
  tradeDetailQuestion(){
    this.showDetailQuestion = !this.showDetailQuestion
  }
  showImageQuestion:boolean = false;
  detailQuestion(questionId: number) {
    debugger;
    this.tradeDetailQuestion()
    this.questionService.getQuestionDetail(questionId).subscribe((response: BaseDTOQuestion) => {
      if (response.result) {
        this.questionDetail = response.questionOutput;
        if(this.questionDetail.urlImage != null){
          this.showImageQuestion = true
        }
      } else {
        console.error(response.message);
      }
    });
    this.commentService.getAllCommentQuestionWithId(questionId).subscribe((response: CommentOutPut) => {
      if (response.result) {
        this.commentParen = response.results;

      } else {
        console.error(response.message);
      }
    });
  }

  toggleChildComment(item: Result): void {
    item.showChildComment = !item.showChildComment;
  }
  commentInput:CommentInput = new CommentInput();
  content:string='';
  createCommentQuestion(questionId:number){
    debugger
    this.commentInput.questionId = questionId;
    this.commentInput.content = this.content;
    this.commentService.createComment(this.commentInput).subscribe((any:BaseDTO)=>{
      if(any.result){
        this.loadingComment(questionId);
        this.content = '';
      }else{
        console.log(any.message);
      }
    })
  }
  contentChildren:string = '';
  createCommentChildren(parentCommentId:number,questionId:number){
    debugger
    this.commentInput.parentCommentId = parentCommentId;
    this.commentInput.content = this.contentChildren;
    this.commentService.createComment(this.commentInput).subscribe((any:BaseDTO)=>{
      if(any.result){
        this.loadingComment(questionId);
        this.contentChildren = '';
      }else{
        console.log(any.message);
      }
    })
  }
  loadingComment(questionId:number){
    this.commentService.getAllCommentQuestionWithId(questionId).subscribe((response: CommentOutPut) => {
      if (response.result) {
        this.commentParen = response.results;
      } else {
        console.error(response.message);
      }
    });
  }
  limit = 20;
  page = 1;
  questionOutPut: QuestionOutput[] = [];
  onLoadingQuestion(){
    debugger;
    if(this.userId){
      this.questionService.getAllQuestion(this.limit, this.page,this.userId,undefined,undefined,undefined).subscribe((any:QuestionPage)=>{
        if(any.result){
          this.questionOutPut = any.simpleResponese.results;
        }
      })
    }
  }

  deleteQuestion(id:number){
    debugger;
    this.questionService.deleteQuestion(id).subscribe((any:BaseDTOQuestion)=>{
      if(any.result){
        this.onLoadingQuestion();
        this.showDetailQuestion = false;
        this.showSuccessMessageInvocie();
      }
    })
  }

  showSuccessInvoice : boolean = false;
  progressWidth: number = 0;
  showSuccessMessageInvocie() {
    this.showSuccessInvoice = true;
    this.progressWidth = 0;
    const interval = setInterval(() => {
      if (this.progressWidth < 100) {
        this.progressWidth += 2;
      } else {
        clearInterval(interval);
        this.showSuccessInvoice = false;
      }
    }, 100);
  }
}
