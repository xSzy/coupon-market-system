package com.ptit.cms.model.other;

public class ResponseModel
{
	private Object data;
	private String status;
	private ErrorMessage error;
	
	public Object getData()
	{
		return data;
	}
	public void setData(Object data)
	{
		this.data = data;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public ErrorMessage getError()
	{
		return error;
	}
	public void setError(ErrorMessage error)
	{
		this.error = error;
	}
	
}
