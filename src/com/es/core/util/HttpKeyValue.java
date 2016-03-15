package com.es.core.util;
/**
 * HttpRequest�ļ�ֵ��
 * @author youwc
 *
 */
public class HttpKeyValue {
	public static HttpKeyValue create(String key, String value) {
		HttpKeyValue keyValue = new HttpKeyValue();
		keyValue.setKey(key);
		keyValue.setValue(value);
		return keyValue;
	}
	private String key;		// ��
	
	private String value;	// ֵ
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
