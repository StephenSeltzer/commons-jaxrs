package edu.psu.rest;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class JaxRsStatusAdapterType
{
  @XmlElement(name="code")
  private int code_;
  
  @XmlElement(name="message")
  private String message_;
  
  public JaxRsStatusAdapterType(Status status)
  {
    code_ = status.getStatusCode();
    message_  = status.name();
  }
  
  public void setStatus(Status status)
  {
    code_ = status.getStatusCode();
    message_ = status.name();
  }
  
  public Status getStatus()
  {
    return Status.fromStatusCode(code_);
  }
  
  public int getCode()
  {
    return code_;
  }
  
  public String getMessage()
  {
    return message_;
  }
}
