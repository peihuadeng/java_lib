package com.dph.common.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dph.common.cache.dao.CacheDao;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LinkTo {
	String field();
	Class<? extends CacheDao<?>> dao();
}
