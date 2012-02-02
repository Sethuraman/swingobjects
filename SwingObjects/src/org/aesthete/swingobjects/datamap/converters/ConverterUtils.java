package org.aesthete.swingobjects.datamap.converters;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

public class ConverterUtils {

	private static final Map<Class<?>, Converter> map = new HashMap<Class<?>, Converter>();

	public static void registerConverter(Class<? extends JComponent> clz, Converter converter) {
		map.put(clz, converter);
	}

	public static void deregisterConverter(Class<? extends JComponent> clz) {
		map.remove(clz);
	}

	public static Converter getConverter(Class<?> clz) {
		for (Class<? extends Object> check = clz; check != null && check != Container.class; check = check.getSuperclass()) {
			for (Class<?> key : map.keySet()) {
				if (key == check) {
					return map.get(key);
				}
			}
		}
		return null;
	}
}
