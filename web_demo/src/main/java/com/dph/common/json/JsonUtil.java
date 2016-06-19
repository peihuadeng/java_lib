package com.dph.common.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json处理工具
 * 
 * @author root
 *
 */
public class JsonUtil {

	private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	/**
	 * 将bean转化成json字符串
	 * @param obj
	 * @return
	 */
	public static String bean2Str(Object obj) {
		ObjectMapper mapper = getObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			JsonGenerator gen = new JsonFactory().createGenerator(writer);
			mapper.writeValue(gen, obj);
			gen.close();
			String json = writer.toString();
			writer.close();
			return json;
		} catch (IOException e) {
			logger.debug("fail to convert bean to string, class: " + (obj == null ? obj : obj.getClass().getName()), e);
			return null;
		}
	}

	/**
	 * 将json字符串转化成目标类型对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T str2bean(String json, Class<T> clazz) {
		try {
			ObjectMapper mapper = getObjectMapper();
			T t = mapper.readValue(json, clazz);
			return t;
		} catch (IOException e) {
			logger.debug("fail to convert string to bean, string: " + json, e);
			return null;
		}
	}

	/**
	 * 将json字符串转换成object
	 * @param json
	 * @param obj
	 */
	public static Object str2bean(String json) {
		try {
			ObjectMapper mapper = getObjectMapper();
			Object obj = mapper.readValue(json, Object.class);
			return obj;
		} catch (IOException e) {
			logger.debug("fail to convert string to object, string: " + json, e);
			return null;
		}
	}

	/**
	 * 将json字符串转化成collection
	 * @param json
	 * @param valueTypeRef
	 * @return
	 */
	public static <T> T str2Collection(String json, TypeReference<T> valueTypeRef) {
		try {
			ObjectMapper mapper = getObjectMapper();
			T t = mapper.readValue(json, valueTypeRef);
			return t;
		} catch (IOException e) {
			logger.debug("fail to convert string to collection, string: " + json, e);
			return null;
		}
	}

	/**
	 * 将json字符串转化成list
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> str2list(String json, Class<T> clazz) {
		try {
			ObjectMapper mapper = getObjectMapper();
			JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);

			List<T> list = mapper.readValue(json, type);
			return list;
		} catch (Exception e) {
			logger.debug("fail to convert string to list, string: " + json, e);
			return null;
		}
	}

	/**
	 * 将json字符串转化成map
	 * @param json
	 * @param keyClass
	 * @param valueClass
	 * @return
	 */
	public static <KT, VT> Map<KT, VT> str2map(String json, Class<KT> keyClass, Class<VT> valueClass) {
		try {
			ObjectMapper mapper = getObjectMapper();
			JavaType type = mapper.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
			Map<KT, VT> map = mapper.readValue(json, type);
			
			return map;
		} catch (Exception e) {
			logger.debug("fail to convert string to map, string: " + json, e);
			return null;
		}
	}
}

