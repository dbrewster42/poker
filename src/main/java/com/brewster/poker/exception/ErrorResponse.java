package com.brewster.poker.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorResponse {
     private int status;
     private String errReason;
     private String message;
     private String path;

     public ErrorResponse(int status, String errReason, String errMessage, String path) {
          this.status = status;
          this.errReason = errReason;
          this.message = errMessage;
          this.path = path;
     }

     @Override
     public String toString(){
          return "Message: "  + message + " " + errReason + " Status Code: " + status + " at " + path;
     }

     public int getStatus() {
          return status;
     }

     public String getErrReason() {
          return errReason;
     }

     public String getMessage() {
          return message;
     }

     public String getPath() {
          return path;
     }
}
