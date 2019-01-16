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

/*
 * $Id: Yytoken.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-15
 */
package com.staarbits.io.json.parser;

/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class Yytoken {
	public static final int TYPE_VALUE=0;//JSON primitive value: string,number,boolean,null
	public static final int TYPE_LEFT_BRACE=1;
	public static final int TYPE_RIGHT_BRACE=2;
	public static final int TYPE_LEFT_SQUARE=3;
	public static final int TYPE_RIGHT_SQUARE=4;
	public static final int TYPE_COMMA=5;
	public static final int TYPE_COLON=6;
	public static final int TYPE_EOF=-1;//end of file
	
	public int type=0;
	public Object value=null;
	
	public Yytoken(int type,Object value){
		this.type=type;
		this.value=value;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		switch(type){
		case TYPE_VALUE:
			sb.append("VALUE(").append(value).append(")");
			break;
		case TYPE_LEFT_BRACE:
			sb.append("LEFT BRACE({)");
			break;
		case TYPE_RIGHT_BRACE:
			sb.append("RIGHT BRACE(})");
			break;
		case TYPE_LEFT_SQUARE:
			sb.append("LEFT SQUARE([)");
			break;
		case TYPE_RIGHT_SQUARE:
			sb.append("RIGHT SQUARE(])");
			break;
		case TYPE_COMMA:
			sb.append("COMMA(,)");
			break;
		case TYPE_COLON:
			sb.append("COLON(:)");
			break;
		case TYPE_EOF:
			sb.append("END OF FILE");
			break;
		}
		return sb.toString();
	}
}
