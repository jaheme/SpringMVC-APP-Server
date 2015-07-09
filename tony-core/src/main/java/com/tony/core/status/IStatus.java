package com.tony.core.status;

/**
 * 状态码和状态信息。要求做到模块状态 码区分 <br>
 * 业务的状态码以万做为起始。<br>
 * 1~9999(含) 已占用，用于通用的状态描述，比如db异常
 * 
 * @see BaseStatus
 * 
 * @author jahe.me
 *
 */
public interface IStatus {
	
	public int getCode();
	
	public String getMsg();

}
