import { BaseDTOQuestion } from './../../../model/interface/baseDTO';
import { QuestionInput, QuestionOutput, QuestionPage } from './../../../model/interface/question';
import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { TypeQuestionService } from '../../../services/type-question.service';
import { BaseDTOSerivce } from '../../../model/interface/baseDTO';
import { TypeQuestion } from '../../../model/interface/typeQuestion';
import { Doctor } from '../../../model/class/home';
import { HomeService } from '../../../services/home.service';
import { BaseDTO, UserReponse } from '../../../model/interface/auth';
import { FormBuilder, FormGroup, FormsModule, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { QuestionService } from '../../../services/question.service';
import { CommentInput, CommentOutPut, Result } from '../../../model/interface/comment';
import { CommentService } from '../../../services/comment.service';
import { ProfileService } from '../../../services/profile.service';

@Component({
  selector: 'app-comment',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule,FormsModule],
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.scss'
})
export class CommentComponent implements OnInit {
  typeQuestionService = inject(TypeQuestionService)
  homeService = inject(HomeService);
  questionService = inject(QuestionService)
  commentService = inject(CommentService)
  formQuestion!: FormGroup;
  limit: number = 4;
  page: number = 1;
  userId = localStorage.getItem('user_id');
  avatar: string = localStorage.getItem('avatarUser')?? ''
  fullName: string = localStorage.getItem('fullNameUser') ?? ''
  userProfileService = inject(ProfileService)
  constructor(private fb: FormBuilder){}
  ngOnInit(): void {
    this.onLoadingQuestion();
    this.getAllTypeQuestion();
    this.loadDoctor();
    this.formQuestion = this.fb.group({
      title:['',Validators.required],
      doctorId:['default', Validators.required],
      typeQuestion:['default', Validators.required],
      content:['', Validators.required]
    })
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

  typeQuestions!: TypeQuestion[];
  getAllTypeQuestion(){
    this.typeQuestionService.getAllTypeQuestion().subscribe((any:BaseDTOSerivce)=>{
      if(any.result){
        this.typeQuestions = any.results;
      }
    })
  }
  role: String = "ROLE_DOCTOR"
  doctorArray : Doctor[] =[];
  loadDoctor(){
    this.homeService.showDoctor(this.role).subscribe((any: UserReponse)=>{
      if(any.baseDTO.result){
        this.doctorArray = any.userList;
      }
    });
  }

  questionInput!: QuestionInput;
  showQuestionForm:boolean = false;
  tradeQuestionForm(){
    this.showQuestionForm = !this.showQuestionForm;
  }
  onSubmit(){
    if(this.formQuestion){
      this.questionInput = {...this.formQuestion.value};
      this.questionService.createQuestionHaveAccount(this.questionInput).subscribe((any:BaseDTOSerivce)=>{
        if(any.result){
          console.log(any.message);
          this.formQuestion.reset();
          this.formQuestion.patchValue({
            doctorId: 'default',
            typeQuestion: 'default'
          });
          this.showQuestionForm = false;
          this.onLoadingQuestion();
          this.showSuccessMessage();
        }else{
          console.log(any.message);
        }
      })
    }
  }

  showSuccess: boolean = false;
  progressWidth: number = 0;

  showSuccessMessage() {
    this.showSuccess = true;
    this.progressWidth = 0;

    const interval = setInterval(() => {
      if (this.progressWidth < 100) {
        this.progressWidth += 2;
      } else {
        clearInterval(interval);
        this.showSuccess = false;
      }
    }, 100);
  }

  questionOutPut!: QuestionOutput[];
  onLoadingQuestion(){
    debugger;
    if(this.userId){
      this.questionService.getAllQuestion(this.limit, this.page,undefined,this.userId,undefined,undefined).subscribe((any:QuestionPage)=>{
        if(any.result){
          this.questionOutPut = any.simpleResponese.results;
        }
      })
    }
  }
  questionImageFile: File | null = null;
  questionImageFilePreview: string | ArrayBuffer = 'https://img.freepik.com/free-vector/pet-logo-design-paw-vector-animal-shop-business_53876-136741.jpg';
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.questionImageFile = input.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        const result = e.target?.result;
        if (typeof result === 'string' || result instanceof ArrayBuffer) {
          this.questionImageFilePreview = result;
        }
      };
      reader.readAsDataURL(input.files[0]);
    }
    event.preventDefault();
  }
  triggerFileInput(): void {
    const fileInput = document.getElementById('file-input') as HTMLInputElement;
    fileInput.click();
  }

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
        this.detailQuestion(questionId);
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
}
