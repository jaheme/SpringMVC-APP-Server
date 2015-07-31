package com.tony.logic.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tony.core.http.JsonView;
import com.tony.core.http.ModelAndViewJson;
import com.tony.core.utils.DateUtil;
import com.tony.logic.module.test.TestService;
import com.tony.logic.module.test.model.Req;
import com.tony.logic.module.test.model.Test;

// @RestController 性能太低
@Controller
@RequestMapping("/test")
public class TestController {
	

	@Autowired
	private TestService testService;

	/**
	 * 此方式返回Json格式数据，参数如果是自定义对象，也会一起进入返回的json，此问题尚未解决。
	 * 所以建议方法参数还是用基本数据类型。
	 * @param r
	 * @return
	 */
	@RequestMapping("/obj")
	public Object testObj(Req r) {
		testService.test();
		Test t = new Test();
		t.setAge(10);
		t.setBirthday(DateUtil.currentStr());
//		t.setReq(reqForm);
		if (null == r.getName()) {
			t.setName("req null");
			return ModelAndViewJson.ok(t);
		}
		t.setName(r.getName());

		return ModelAndViewJson.ok(t);
	
	}

	@RequestMapping("/t")
	public Object testObj(String name) {
//		testService.test();
		Test t = new Test();
		t.setAge(9);
		t.setBirthday(DateUtil.currentStr());
		t.setName(name==null ? "中国" : name);
//		Integer.parseInt(t.getName());	// 测试未捕获异常
		return ModelAndViewJson.ok(t);
	
	}
	
	/**
	 * 此实现方式性能较低。
	 * @param age
	 * @param name
	 * @return
	 */
	@RequestMapping("/res")
	@ResponseBody
	public Object test(Req r) {
		Test t = new Test();
		t.setAge(10);
		t.setBirthday(DateUtil.currentStr());
		t.setName(r.getName()==null ? "中国" : r.getName());
		
		return JsonView.ok(t);
	
	}
	
	/**
	 * 直接转成JSON回写的前端。性能较/res高，不及/t高
	 * @param r
	 * @param resp
	 * @throws IOException 
	 */
	@RequestMapping("/w")
	public void writeResp(Req r, HttpServletResponse resp) throws IOException {
		Test t = new Test();
		t.setAge(10);
		t.setBirthday(DateUtil.currentStr());
		t.setName(r.getName()==null ? "中国" : r.getName());
		
		JsonView.ok(t).write(resp);
	}
}
