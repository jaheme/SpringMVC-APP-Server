package com.tony.core.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.tony.core.status.BaseStatus;
import com.tony.core.status.IStatus;
import com.tony.core.utils.JsonUtil;


/**
 * 此对象结合 @ResponseBody 注解使用 或 使用Response.write <br>
 * 此方式返回的JSON实现方式性能较低，但可以规避方法参数是自定义对象出现在响应的JSON数据里
 * @author Tony.lai
 *
 */
public class JsonView {
	
	private int code;
	private String msg;
	private Object data;
	
	/** 只返回处理成功，不带数据体。 */
	public static JsonView ok() {
		JsonView json = new JsonView();
		json.code = BaseStatus.SUCCESS.getCode();
		json.msg = "";
		return json;
	}

	/** 返回成功，并需要返回的数据体  */
	public static JsonView ok(Object obj) {
		JsonView json = ok();
		json.data = obj;
		return json;
	}

	/** 只返回失败状态和消息，消息可自己指定。 */
	public static JsonView fail(String msg) {
		JsonView json = new JsonView();
		json.code = BaseStatus.FAIL.getCode();
		json.msg = msg == null ? BaseStatus.FAIL.getMsg() : msg;
		return json;
	}

	/** 只返回失败状态和消息。 */
	public static JsonView fail() {
		return fail(null);
	}
	
	/** 只返回指定的状态码和消息 */
	public static JsonView resp(IStatus status) {
		JsonView json = new JsonView();
		json.code = status.getCode();
		json.msg = status.getMsg();
		return json;
	}
	
	/** 只返回指定的状态码和消息， 消息可自指定。 */
	public static JsonView resp(IStatus status, String msg) {
		JsonView json = new JsonView();
		json.code = status.getCode();
		json.msg = msg == null ? status.getMsg() : msg;
		return json;
	}

	public void write(HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		writer.write(JsonUtil.toStr(this));
		writer.flush();
//		writer.close();	// will automatically close the stream after the servlet is finished running as part of the servlet request life-cycle.
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
