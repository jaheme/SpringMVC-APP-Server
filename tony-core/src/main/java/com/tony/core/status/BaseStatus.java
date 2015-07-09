package com.tony.core.status;

/**
 * 1~9999(含) 已占用，用于通用的状态描述，比如db异常
 * @author Tony.lai
 *
 */
public enum BaseStatus implements IStatus {
	

	SUCCESS(1, "全部成功"),
	FAIL(30, "出现未捕获异常，请联系管理员."),
	
	PARAM_FAIL(100, "参数异常"),
	
	DB_SUCCESS(200, "DB操作成功"),
	DB_ERROR(201, "DB操作异常")
	;
	
	private final int num;

	private final String msg;
	
	private BaseStatus(int num, String msg) {
		this.num = num;
		this.msg = msg;
	}
	
	public int getCode(){
		return num;
	}
	
	public String getMsg() {
		return msg;
	}

}
