package com.dph.common.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JsonDataPackage {

	@JsonIgnore
	// @JSONField(serialize = false, deserialize = false)//阿里巴巴fastjson
	private final Map<String, Object> data;

	public JsonDataPackage(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public static JsonDataPackage fromJson(String json) {
		if (StringUtils.isBlank(json)) {
			return null;
		}

		Map<String, Object> map = JsonUtils.str2map(json, String.class, Object.class);
		if (map == null) {
			return null;
		}

		JsonDataPackage dataPackage = new JsonDataPackage(map);
		return dataPackage;
	}

	@Override
	public String toString() {
		String json = JsonUtils.bean2Str(data);
		return json;
	}

	public Boolean getBoolean(String key) {
		Object value = data.get(key);
		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof Integer) {
			int i = (Integer) value;
			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} else if (value instanceof String) {
			Integer i;
			try {
				i = Integer.valueOf((String) value);
			} catch (NumberFormatException e) {
				return null;
			}

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		}

		return null;
	}

	public void setBoolean(String key, Boolean value) {
		data.put(key, value == false ? 0 : 1);
	}

	public String getString(String key) {
		Object value = data.get(key);
		if (value instanceof String) {
			return (String) value;
		}

		return null;
	}

	public void setString(String key, String value) {
		data.put(key, value);
	}

	public Integer getInteger(String key) {
		Object value = data.get(key);
		if (value instanceof Integer) {
			return (Integer) value;
		}

		if (value instanceof String) {
			try {
				return Integer.valueOf((String) value);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		return null;
	}

	public void setInteger(String key, Integer value) {
		data.put(key, value);
	}

	public Long getLong(String key) {
		Object value = data.get(key);

		if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Integer) {
			int i = (Integer) value;

			return Long.valueOf(i);
		} else if (value instanceof String) {
			try {
				return Long.valueOf((String) value);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		return null;
	}

	public void setLong(String key, Long value) {
		data.put(key, value);
	}

	public Double getDouble(String key) {
		Object value = data.get(key);
		if (value instanceof Double) {
			return (Double) value;
		}

		if (value instanceof String) {
			try {
				return Double.valueOf((String) value);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		return null;
	}

	public void setDouble(String key, Double value) {
		data.put(key, value);
	}

	public void setDate(String key, Date value) {
		data.put(key, DateUtils.formatDateTime(value));
	}

	public Date getDate(String key) {
		Object value = data.get(key);
		if (value instanceof Date) {
			return (Date) value;
		}

		if (value instanceof String) {
			Date date = DateUtils.parseDate((String) value);
			return date;
		}

		return null;
	}

	public <T> List<T> getArray(String key, Class<T> clazz) {
		Object value = data.get(key);
		if (value instanceof List) {
			String json = value.toString();
			try {
				List<T> list = JsonUtils.str2list(json, clazz);
				return list;
			} catch (Exception e) {
			}
		}

		return null;
	}

	public void setArray(String key, List<?> value) {
		data.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public JsonDataPackage getObject(String key) {
		Object value = data.get(key);
		if (value instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) value;
			JsonDataPackage jsonDataPackage = new JsonDataPackage(map);
			return jsonDataPackage;
		}

		return null;
	}

	public void setObject(String key, Object value) {
		data.put(key, value);
	}

	public static void main(String[] args) {
		String json = "{\"student\": {\"id\":0,\"name\":\"abc\", \"array\":[1,2,3]}}";
		JsonDataPackage dataPackage = JsonDataPackage.fromJson(json);
		JsonDataPackage student = dataPackage.getObject("student");
		Integer id = student.getInteger("id");
		String name = student.getString("name");
		System.out.println(String.format("id:%d, name:%s", id, name));

		List<Integer> array = student.getArray("array", Integer.class);
		System.out.println(String.format("array: %s", array));
		for (Integer str : array) {
			System.out.println(str);
		}
		System.out.println(dataPackage);
	}
}