export class LoggedUser implements User {

    constructor () {
      return;
    }
    id?: number;
    name?: string;
    lastName?: string;
    token?: string;
    mail?:string;
    phoneNumber?: string;
    image?: string;
    exp?:number;
    type?:string;
    
  }
  
  export interface User {
    id?: number;
    name?: string;
    lastName?: string;
    mail?: string;
    phoneNumber?: string;
    image?: string
  }
  