export class ContactRequest {
  subject!: string;
  message!: string;
  emailTo!: string;
  name!: string;


  constructor(subject: string, message: string, emailTo: string, name: string) {
    this.subject = subject;
    this.message = message;
    this.emailTo = emailTo;
    this.name = name;
  }
}
