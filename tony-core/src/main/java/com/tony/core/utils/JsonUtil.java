package com.tony.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JSON数据与对象转化的工具类
 * @author jahe.me
 *
 */
public abstract class JsonUtil {

	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

	public static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
	public static String map2String(Map<?, ?> map) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, map);
		} catch (Exception e) {
			log.error("将 map 转换为 json 字符串时发生异常", e);
			return null;
		}
		return writer.toString();
	}

	public static Map<?, ?> string2Map(String string) {
		StringReader reader = new StringReader(string);
		try {
			return mapper.readValue(reader, HashMap.class);
		} catch (IOException e) {
			log.error("将 json 字符串转换为 HashMap 时发生异常", e);
			return null;
		}
	}


	public static TreeMap<?, ?> string2TreeMap(String string) {
		StringReader reader = new StringReader(string);
		try {
			return mapper.readValue(reader, TreeMap.class);
		} catch (IOException e) {
			log.error("将 json 字符串转换为 HashMap 时发生异常", e);
			return null;
		}
	}


	public static String list2String(Collection<Map<String, Object>> map) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, map);
		} catch (Exception e) {
			log.error("将 collection 转换为 json 字符串时发生异常", e);
			return null;
		}
		return writer.toString();
	}


	/**
	 * 将一个 JavaBean 对象转化为一个  Map 
	 * @param bean 要转化的JavaBean 对象 
	 * @return 转化出来的  Map 对象 
	 * @throws IntrospectionException 如果分析类属性失败 
	 * @throws IllegalAccessException 如果实例化 JavaBean 失败 
	 * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
	 */
	public static Map<String,Object> convertBeanToTreeMap(Object bean) throws IntrospectionException {
		Map<String,Object> treeMap = new TreeMap<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
			try {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						treeMap.put(propertyName, result);
					} else {
						treeMap.put(propertyName, "");
					}
				}
			} catch (Exception e) {
				log.error("将 Object bean 转换为 TreeMap<key,Object> 格式时发生异常", e);
			}
		}
		return treeMap;
	}

	public static <T> T toT(String str, Class<T> clazz) {
		try {
			return mapper.readValue(str, clazz);
		} catch (JsonParseException e) {
			log.error("toT error", e);
		} catch (JsonMappingException e) {
			log.error("toT error", e);
		} catch (IOException e) {
			log.error("toT error", e);
		}
		return null;
	}

	public static String toStr(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("toStr error", e);
		}
		return null;
	}
}
