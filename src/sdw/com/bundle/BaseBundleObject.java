package sdw.com.bundle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BaseBundleObject implements Serializable
{
	public String toString() {
		String str = this.getClass().getName() + " >>> "; // 获取类名
		Field[] field = this.getClass().getDeclaredFields();
		for (int i = 0; i < field.length; ++i) {
			try {
				Field f = field[i];
				String fieldName = f.getName();
				String getName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1); // get方法的名字
				Class classType = this.getClass();
				Method getMethod = classType.getMethod(getName, new Class[] {});
				Object value = getMethod.invoke(this, new Object[] {});
				str += fieldName + ": " + value + ",";
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return str;
	}
}
