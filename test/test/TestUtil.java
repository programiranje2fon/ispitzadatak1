package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility methods used for test execution.
 */
public class TestUtil {

	/**
	 * Returns a value of a field named 'fieldName' from the passed object.
	 * 
	 * @param obj         object to retrieve the field value from
	 * @param fieldName   field name
	 * @param failMessage message to be written with a failed test in case there is
	 *                    no field with the given name
	 * @return value of the field (to be casted to the proper type)
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		try {
			// collect all fields from the obj class and all super classes
			List<Field> result = new ArrayList<Field>();

			Class<?> clazz = obj.getClass();
			while (clazz != null && clazz != Object.class) {
				Collections.addAll(result, clazz.getDeclaredFields());
				clazz = clazz.getSuperclass();
			}

			// iterate through all fields
			for (Field field : result) {
				if (field.getName().equals(fieldName)) {
					field.setAccessible(true);

					return field.get(obj);
				}
			}
		} catch (IllegalAccessException e1) {
		}
		return null;
	}

	public static boolean doesFieldExist(Class<?> c, String fieldName) {
		try {
			c.getDeclaredField(fieldName);

			return true;
		} catch (NoSuchFieldException e1) {
			return false;
		}
	}

	public static boolean hasFieldModifier(Class<?> c, String fieldName, int modifier) {
		try {
			Field field = c.getDeclaredField(fieldName);

			switch (modifier) {
			case Modifier.PUBLIC:
				return Modifier.isPublic(field.getModifiers());
			case Modifier.PRIVATE:
				return Modifier.isPrivate(field.getModifiers());
			case Modifier.PROTECTED:
				return Modifier.isProtected(field.getModifiers());
			case 0: // package scope
				return field.getModifiers() == 0;
			default:
				return false;
			}
		} catch (NoSuchFieldException | SecurityException e) {
			return false;
		}
	}

	public static boolean hasMethodModifier(Class<?> c, String methodName, Class<?>[] parameterTypes, int modifier) {
		try {
			Method method = c.getDeclaredMethod(methodName, parameterTypes);

			switch (modifier) {
			case Modifier.PUBLIC:
				return Modifier.isPublic(method.getModifiers());
			case Modifier.PRIVATE:
				return Modifier.isPrivate(method.getModifiers());
			case Modifier.PROTECTED:
				return Modifier.isProtected(method.getModifiers());
			case 0: // package scope
				return method.getModifiers() == 0;
			default:
				return false;
			}
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}
	}

	public static boolean hasConstructorModifier(Class<?> c, Class<?>[] parameterTypes, int modifier) {
		try {
			Constructor<? extends Object> constructor = c.getDeclaredConstructor(parameterTypes);

			switch (modifier) {
			case Modifier.PUBLIC:
				return Modifier.isPublic(constructor.getModifiers());
			case Modifier.PRIVATE:
				return Modifier.isPrivate(constructor.getModifiers());
			case Modifier.PROTECTED:
				return Modifier.isProtected(constructor.getModifiers());
			case 0: // package scope
				return constructor.getModifiers() == 0;
			default:
				return false;
			}
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}
	}

}
