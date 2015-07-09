package com.tony.core.db;

/**
 * redis key 定义  尽量短。
 * @author jahe.me
 *
 */
public interface Keys {
	
	/** 后接用户登录的一的唯 一加密字符串，<br> 值 是用户id */
	public static final String KEY = "K:";

	/** 后接用户的id，<br> 值 是用户基本资料对象UserBase */
	public static final String BASE_INFO = "UB:";
	/** 后接用户的id，<br> 值 是用户资料对象User */
	public static final String UINFO = "UI:";
	
	/** 后接用户的id，<br> 值 是好友申请列表 */
	public static final String BOOK_MSG = "BM:";

	/** 行程的信息(基本+详情) <br>后接行程ID */
	public static final String PLAN_INFO = "PI:";
	/** 行程的用户列表 <br> SET后接行程ID，<br> 值 是用户ID列表 */
	public static final String PLAN_USER = "PU:";
	/** 行程的导图信息 <br> HASH后接行程ID <br> key是用户id，value是坐标  */
	public static final String PLAN_MAP = "PM:";
	/** 行程的基本信息 <br> 后接行程id <br>  */
	public static final String PLAN_MAP_INFO = "PMI:";
	/** 行程KEY对应的用户ID和行程ID <br> 后接行程的 key  */
	public static final String PLAN_KEY = "P:";
	
	

	public final short ONE_MINUTE = 60;
	public final short FIVE_MINUTE = 300;
	public final short TEN_MINUTE = 600;
	public final short HALF_HOUR = 1800; 	//60 * 30;
	public final short ONE_HOUR = 3600; 	//60 * 60;
	public final int HALF_DAY = 43200;	//60 * 60 * 12;
	public final int ONE_DAY = 86400;	//60 * 60 * 24;
	public final int ONE_WEEK = 604800;	//60 * 60 * 24 * 7;
	
}
