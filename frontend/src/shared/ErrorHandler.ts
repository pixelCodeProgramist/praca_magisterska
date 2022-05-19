export class ErrorHandler {
  constructor() {
  }
  handle(error: any, messageError: string) {
    if (error.status === 0) messageError = 'Nieznany błąd';
    else {

      if (typeof error.error === 'string' || error.error instanceof String) {
        messageError = error.error;
      }else {
        let errMap: Map<string, string> = new Map(Object.entries(error.error));
        let i = 0;
        errMap.forEach((value: string, key: string) => {

          if (errMap.size > 1)
            messageError += value;
          if (i<errMap.size-1) messageError += ', ';
          if(errMap.size == 1) messageError = value
          i++;
        });

      }



    }
    return messageError;
  }
}
