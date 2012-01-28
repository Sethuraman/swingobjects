package org.aesthete.swingobjects.view;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.text.JTextComponent;

import org.aesthete.swingobjects.annotations.Validate;
import org.aesthete.swingobjects.annotations.ValidateTypes;
import org.aesthete.swingobjects.annotations.Validation;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;

public class UIValidator {

	/**
	 * This method assumes that all fields passed in have an annotation Validate
	 * present
	 *
	 * @param fields
	 * @param container
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static boolean validate(List<Field> fields, Object container, String action) throws IllegalArgumentException, IllegalAccessException {
		try {

			for (Field field : fields) {
				Validate validate = field.getAnnotation(Validate.class);
				Validation[] validations = validate.value();
				Object fieldObj = field.get(container);
				for (Validation val : validations) {
					if ("ALL".equals(val.action()) || (val.action() != null && val.action().equals(action))) {
						for (ValidateTypes type : val.value()) {
							switch (type) {
								case Required:
									if(JTextComponent.class.isAssignableFrom(fieldObj.getClass())) {

									}

							}
						}
					}
				}

			}

		}catch (Exception e) {
			throw new SwingObjectRunException("Error processing action", e,
					ErrorSeverity.SEVERE, FrameFactory.class);
		}

		return true;
	}
}
