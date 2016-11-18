package com.dph.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * json处理工具
 * 
 * @author root
 *
 */
public class JsonUtils {

	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		return mapper;
	}

	/**
	 * 将bean转化成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String bean2Str(Object obj) {
		ObjectMapper mapper = getObjectMapper();
		StringWriter writer = new StringWriter();
		JsonGenerator gen = null;
		try {
			gen = new JsonFactory().createGenerator(writer);
			mapper.writeValue(gen, obj);
			String json = writer.toString();

			return json;
		} catch (IOException e) {
			throw new RuntimeException("fail to convert bean to string, class: " + (obj == null ? obj : obj.getClass().getName()), e);
		} finally {
			if (gen != null) {
				try {
					gen.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将bean序列化为str：可与@JsonTypeInfo注解配合使用，实现多态序列化
	 * 
	 * @param valueTypeRef
	 * @param t
	 * @return
	 */
	public static <T> String bean2Str(TypeReference<T> valueTypeRef, T t) {
		ObjectMapper mapper = getObjectMapper();
		StringWriter writer = new StringWriter();
		JsonGenerator gen = null;
		try {
			gen = new JsonFactory().createGenerator(writer);
			mapper.writerFor(valueTypeRef).writeValue(gen, t);
			String json = writer.toString();

			return json;
		} catch (IOException e) {
			throw new RuntimeException(String.format("fail to convert bean to string, type:%s, object class:%s", valueTypeRef, t == null ? t : t.getClass().getName()), e);
		} finally {
			if (gen != null) {
				try {
					gen.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将bean序列化到文件：可与@JsonTypeInfo注解配合使用，实现多态序列化
	 * 
	 * @param valueTypeRef
	 * @param t
	 * @param file
	 */
	public static <T> void bean2File(TypeReference<T> valueTypeRef, T t, File file) {
		ObjectMapper mapper = getObjectMapper();
		try {
			File path = file.getParentFile();
			if (path != null && path.exists() == false) {
				path.mkdirs();
			}

			mapper.writerFor(valueTypeRef).writeValue(file, t);
		} catch (IOException e) {
			throw new RuntimeException(String.format("fail to convert bean to file, type: %s, object class: %s, file: %s", //
					valueTypeRef, t == null ? t : t.getClass().getName(), file), e);
		}
	}

	/**
	 * 将json字符串转化成目标类型对象
	 * 
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
			throw new RuntimeException(String.format("fail to convert string to bean, class:%s, string:%s", clazz, json), e);
		}
	}

	/**
	 * 将json字符串转换成object
	 * 
	 * @param json
	 * @param obj
	 */
	public static Object str2bean(String json) {
		try {
			ObjectMapper mapper = getObjectMapper();
			Object obj = mapper.readValue(json, Object.class);
			return obj;
		} catch (IOException e) {
			throw new RuntimeException("fail to convert string to object, string: " + json, e);
		}
	}

	/**
	 * 将json字符串转化成collection
	 * 
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
			throw new RuntimeException(String.format("fail to convert string to collection, type:%s, string:%s", valueTypeRef, json), e);
		}
	}

	/**
	 * 将文件内容序列化成collection
	 * 
	 * @param file
	 * @param valueTypeRef
	 * @return
	 */
	public static <T> T file2Collection(File file, TypeReference<T> valueTypeRef) {
		try {
			ObjectMapper mapper = getObjectMapper();
			T t = mapper.readValue(file, valueTypeRef);

			return t;
		} catch (IOException e) {
			throw new RuntimeException(String.format("fail to convert file to collection, type:%s, file:%s", //
					valueTypeRef, file), e);
		}
	}

	/**
	 * 将json字符串转化成list
	 * 
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
			throw new RuntimeException(String.format("fail to convert string to list, class:%s, string:%s", clazz, json), e);
		}
	}

	/**
	 * 将json字符串转化成map
	 * 
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
			throw new RuntimeException(String.format("fail to convert string to map, keyClass:%s, valueClass:%s, string:%s", keyClass, valueClass, json), e);
		}
	}
}
