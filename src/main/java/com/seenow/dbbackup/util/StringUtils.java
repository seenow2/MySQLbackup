package com.seenow.dbbackup.util;

public class StringUtils
{
	public static String removeTForTime(String value)
	{
		if (value != null)
		{
			value = value.replace("T", " ");
		}
		return value;
	}

	/**
	 * @description:  去掉行首的顿号
	 * @param: [str]
	 * @return: String
	 * @author: seenow
	 * @date: 2021-07-13 12:36:49
	 */
	public static String removePreChar(String str){
		if(isEmpty(str)){
			return "";
		}
		if(str.substring(0,1).equals("、")){
			return str.substring(1);
		}else{
			return str;
		}
	}

	/**
	 * 判断字符串是否为null和""
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str)
	{
		if (str instanceof String)
		{
			return null == str || "".equals(str.toString().trim());
		} else {
			return null == str;
		}
	}


	/**
	 * 如果字符串为empty，则返回""
	 * @param str
	 * @return
	 */
	public static String filterString(String str)
	{
		return isEmpty(str)?"" : str.trim();
	}

	/**
	 * 如果字符串为empty，则返回""
	 * @param str
	 * @return
	 */
	public static String filterString(Object str)
	{
		return ( null == str )? "" : str.toString().trim();
	}
}
