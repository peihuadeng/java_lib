package com.dph.common.utils;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class JsonDataPackage {

	@JsonIgnore
//	@JSONField(serialize = false, deserialize = false)//阿里巴巴fastjson
	private Map<String, Object> data;

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
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

}