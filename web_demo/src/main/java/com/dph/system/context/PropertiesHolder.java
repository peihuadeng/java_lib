package com.dph.system.context;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component
public class PropertiesHolder implements EmbeddedValueResolverAware {

	private static StringValueResolver stringValueResolver;

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		stringValueResolver = resolver;
	}

	public static String getString(String name) {
		return stringValueResolver.resolveStringValue(name);
	}

	public static byte getByte(String name) {
		String result = stringValueResolver.resolveStringValue(name);
		return Byte.valueOf(result);
	}

	public static int getInt(String name) {
		String result = stringValueResolver.resolveStringValue(name);
		return Integer.valueOf(result);
	}

	public static long getLong(String name) {
		String result = stringValueResolver.resolveStringValue(name);
		return Long.valueOf(result);
	}

	public static double getDouble(String name) {
		String result = stringValueResolver.resolveStringValue(name);
		return Double.valueOf(result);
	}

	public static float getFloat(String name) {
		String result = stringValueResolver.resolveStringValue(name);
		return Float.valueOf(result);
	}

	public static boolean getBoolean(String name) {
		String result = stringValueResolver.resolveStringValue(name);
		return Boolean.valueOf(result);
	}

}
