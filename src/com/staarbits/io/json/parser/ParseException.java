/*
 * Copyright (c) 2019. StaarBits Network & Development says that this file is under the StaarBits Global Copyright (SGC).
 * Every file which contains this annotation as one of the first things written is under the SGC protocol.
 * The SGC (StaarBits Global Copyright) demonstrates that the file which has it cannot be copied and pasted as
 * an annotation file by anyone else who has not gotten the Owner rank at StaarBits. So... The most powerful rank
 * at the executive can spread this file. If someone uses this file without the permission given by the executive
 * administration, this same person will be able to be sued by the SEA (StaarBits Executive Administration); if
 * someone who works at StaarBits spreads this file, this person will as sooner as possible be removed from our
 * team and (s)he will also be able to response a lawsuit as well.
 */

package com.staarbits.io.json.parser;

/**
 * ParseException explains why and where the error occurs in source JSON text.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 *
 */
public class ParseException extends Exception {
	private static final long serialVersionUID = -7880698968187728547L;
	
	public static final int ERROR_UNEXPECTED_CHAR = 0;
	public static final int ERROR_UNEXPECTED_TOKEN = 1;
	public static final int ERROR_UNEXPECTED_EXCEPTION = 2;

	private int errorType;
	private Object unexpectedObject;
	private int position;
	
	public ParseException(int errorType){
		this(-1, errorType, null);
	}
	
	public ParseException(int errorType, Object unexpectedObject){
		this(-1, errorType, unexpectedObject);
	}
	
	public ParseException(int position, int errorType, Object unexpectedObject){
		this.position = position;
		this.errorType = errorType;
		this.unexpectedObject = unexpectedObject;
	}
	
	public int getErrorType() {
		return errorType;
	}
	
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	
	/**
	 * @see JSONParser#getPosition()
	 * 
	 * @return The character position (starting with 0) of the input where the error occurs.
	 */
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * @see Yytoken
	 * 
	 * @return One of the following base on the value of errorType:
	 * 		   	ERROR_UNEXPECTED_CHAR		java.lang.Character
	 * 			ERROR_UNEXPECTED_TOKEN		Yytoken
	 * 			ERROR_UNEXPECTED_EXCEPTION	java.lang.Exception
	 */
	public Object getUnexpectedObject() {
		return unexpectedObject;
	}
	
	public void setUnexpectedObject(Object unexpectedObject) {
		this.unexpectedObject = unexpectedObject;
	}
	
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		
		switch(errorType){
		case ERROR_UNEXPECTED_CHAR:
			sb.append("Unexpected character (").append(unexpectedObject).append(") at position ").append(position).append(".");
			break;
		case ERROR_UNEXPECTED_TOKEN:
			sb.append("Unexpected token ").append(unexpectedObject).append(" at position ").append(position).append(".");
			break;
		case ERROR_UNEXPECTED_EXCEPTION:
			sb.append("Unexpected exception at position ").append(position).append(": ").append(unexpectedObject);
			break;
		default:
			sb.append("Unkown error at position ").append(position).append(".");
			break;
		}
		return sb.toString();
	}
}
