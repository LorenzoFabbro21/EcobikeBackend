export class LoggedUser implements User {

    constructor () {
      return;
    }
  
    /** Logged username */
    username?: string;
    /** Logged name-surname */
    name?: string;

    type?: string;
    token?: string;
  
  }
  
  export interface User {
     /** Logged username */
     username?: string;
     /** Logged name-surname */
     name?: string;
     type?: string;
     token?: string
  }
  