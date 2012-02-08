package org.aesthete.swingobjects.datamap;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.view.FrameFactory;

public class DataWrapper {

	private Object value;

	public DataWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Returns the string representation of this object
	 *
	 * @return a string
	 */
	public String toString() {
		return this.asString();
	}

	/**
	 * Returns the string representation of this object
	 *
	 * @return a string
	 */
	public String asString() {
		if (isNull()) {
			return null;
		} else if (isString()) {
			return (String) value;
		} else if (isBytes()) {
			return new String((byte[]) value);
		} else {
			return value.toString();
		}
	}

	public String[] asStringArray() {
		try {
			if(isNull()) {
				return null;
			}else if(value instanceof String[]) {
				return (String[])value;
			}else {
				throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
			}
		}catch (Exception e) {
			throw new SwingObjectRunException( e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a BigDecimal
	 *
	 * @return a BigDecimal
	 *
	 * @exception DataSetException
	 */
	public BigDecimal asBigDecimal()
	{
		try {
			if (isNull()) {
				return null;
			} else if (isBigDecimal()) {
				return (BigDecimal) value;
			} else if (isDouble()) {
				return new BigDecimal(((Double) value).doubleValue());
			} else if (isFloat()) {
				return new BigDecimal(((Float) value).doubleValue());
			} else if (isString() || isInt() || isLong() || isShort() || isByte()) {
				return new BigDecimal(asString());
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a BigDecimal
	 *
	 * @param scale
	 *            TODO: DOCUMENT ME!
	 *
	 * @return a BigDecimal
	 *
	 * @exception DataSetException
	 */
	public BigDecimal asBigDecimal(int scale)
	{
		try {
			if (isNull()) {
				return null;
			} else if (isBigDecimal()) {
				return ((BigDecimal) value).setScale(scale);
			} else if (isDouble()) {
				return new BigDecimal(((Double) value).doubleValue()).setScale(scale);
			} else if (isFloat()) {
				return new BigDecimal(((Float) value).doubleValue()).setScale(scale);
			} else if (isString() || isInt() || isLong() || isShort() || isByte()) {
				return new BigDecimal(asString()).setScale(scale);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asBoolean
	 *
	 * @return a boolean
	 *
	 * @exception DataSetException
	 */
	public boolean asBoolean()
	{
		try {
			if (isNull()) {
				return false;
			} else if (isBoolean()) {
				return ((Boolean) value).booleanValue();
			}

			String check = asString();

			return (check == null) ? false : isTrue(check);
		} catch (Exception e) {
			throw new SwingObjectRunException( e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	private boolean isTrue(String value)
    {
        return (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("t")
                || value.equalsIgnoreCase("yes")
        || value.equalsIgnoreCase("y") || value.equals("1"));
    }

	/**
	 * Get the value as a Boolean object
	 *
	 * @return a Boolean
	 *
	 * @exception DataSetException
	 */
	public Boolean asBooleanObj()
	{
		try {
			if (isNull()) {
				return null;
			} else if (isBoolean()) {
				return (Boolean) value;
			}

			String check = asString();

			if (check == null) {
				return null;
			} else if (isTrue(check)) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asInt
	 *
	 * @return an int
	 *
	 * @exception DataSetException
	 */
	public int asInt()
	{
		try {
			if (isNull()) {
				return 0;
			} else if (isInt()) {
				return ((Integer) value).intValue();
			} else if (isString()) {
				return Integer.valueOf((String) value).intValue();
			} else if (isLong()) {
				return ((Long) value).intValue();
			} else if (isDouble()) {
				return ((Double) value).intValue();
			} else if (isFloat()) {
				return ((Float) value).intValue();
			} else if (isBigDecimal()) {
				return ((BigDecimal) value).intValue();
			} else {
				return Integer.valueOf(asString()).intValue();
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( e, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a Integer Ojbect
	 *
	 * @return an Integer
	 *
	 * @exception DataSetException
	 */
	public Integer asIntegerObj()
	{
		try {
			if (isNull()) {
				return null;
			} else if (isInt()) {
				return ((Integer) value);
			} else if (isString() || isDouble() || isFloat() || isBigDecimal() || isLong() || isShort() || isByte()) {
				return new Integer(asString());
			} else {
				throw new SwingObjectRunException(null, ErrorSeverity.SEVERE, FrameFactory.class);
			}
		} catch (Exception e) {
			throw new SwingObjectRunException(null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asByte
	 *
	 * @return a byte
	 *
	 * @exception DataSetException
	 */
	public byte asByte()

	{
		try {
			if (isNull()) {
				return 0;
			} else if (isByte()) {
				return ((Byte) value).byteValue();
			} else if (isString()) {
				return Integer.valueOf((String) value).byteValue();
			} else if (isShort()) {
				return ((Short) value).byteValue();
			} else if (isInt()) {
				return ((Integer) value).byteValue();
			} else if (isLong()) {
				return ((Long) value).byteValue();
			} else if (isDouble()) {
				return ((Double) value).byteValue();
			} else if (isFloat()) {
				return ((Float) value).byteValue();
			} else if (isBigDecimal()) {
				return ((BigDecimal) value).byteValue();
			} else {
				return Integer.valueOf(asString()).byteValue();
			}
		} catch (Exception e) {
			throw new SwingObjectRunException(null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a Byte Object
	 *
	 * @return a Byte
	 *
	 * @exception DataSetException
	 */
	public Byte asByteObj()

	{
		try {
			if (isNull()) {
				return null;
			} else if (isByte()) {
				return ((Byte) value);
			} else if (isString() || isDouble() || isFloat() || isInt() || isLong() || isShort() || isBigDecimal()) {
				return new Byte(asString());
			} else {
				throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
			}
		} catch (Exception e) {
			throw new SwingObjectRunException(null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asBytes
	 *
	 * @return a byte array
	 *
	 * @exception DataSetException
	 */
	public byte[] asBytes()
	{
		try {
			if (isNull()) {
				return new byte[0];
			} else if (isBytes()) {
				return (byte[]) value;
			} else if (isString()) {
				return ((String) value).getBytes();
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}

		return new byte[0];
	}

	/**
	 * Get the value as a asShort
	 *
	 * @return a short
	 *
	 * @exception DataSetException
	 */
	public short asShort()
	{
		try {
			if (isNull()) {
				return 0;
			} else if (isShort()) {
				return ((Short) value).shortValue();
			} else if (isString()) {
				return Integer.valueOf((String) value).shortValue();
			} else if (isInt()) {
				return ((Integer) value).shortValue();
			} else if (isLong()) {
				return ((Long) value).shortValue();
			} else if (isDouble()) {
				return ((Double) value).shortValue();
			} else if (isFloat()) {
				return ((Float) value).shortValue();
			} else if (isBigDecimal()) {
				return ((BigDecimal) value).shortValue();
			} else {
				return Integer.valueOf(asString()).shortValue();
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a Short Object
	 *
	 * @return a Short
	 *
	 * @exception DataSetException
	 */
	public Short asShortObj()
	{
		try {
			if (isNull()) {
				return null;
			} else if (isShort()) {
				return ((Short) value);
			} else if (isString() || isDouble() || isFloat() || isInt() || isLong() || isBigDecimal() || isByte()) {
				return new Short(asString());
			} else {
				throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asLong
	 *
	 * @return a long
	 *
	 * @exception DataSetException
	 */
	public long asLong()
	{
		try {
			if (isNull()) {
				return 0;
			} else if (isLong()) {
				return ((Long) value).longValue();
			} else if (isString()) {
				return Integer.valueOf((String) value).longValue();
			} else if (isShort()) {
				return ((Short) value).longValue();
			} else if (isInt()) {
				return ((Integer) value).longValue();
			} else if (isDouble()) {
				return ((Double) value).longValue();
			} else if (isFloat()) {
				return ((Float) value).longValue();
			} else if (isBigDecimal()) {
				return ((BigDecimal) value).longValue();
			} else {
				return Integer.valueOf(asString()).longValue();
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a Long Object
	 *
	 * @return a Long
	 *
	 * @exception DataSetException
	 */
	public Long asLongObj()
	{
		try {
			if (isNull()) {
				return null;
			} else if (isLong()) {
				return ((Long) value);
			} else if (isString() || isDouble() || isFloat() || isInt() || isBigDecimal() || isShort() || isByte()) {
				return new Long(asString());
			} else {
				throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asDouble
	 *
	 * @return a double
	 *
	 * @exception DataSetException
	 */
	public double asDouble()

	{
		try {
			if (isNull()) {
				return 0.0D;
			} else if (isDouble()) {
				return ((Double) value).doubleValue();
			} else if (isString()) {
				return Integer.valueOf((String) value).doubleValue();
			} else if (isShort()) {
				return ((Short) value).doubleValue();
			} else if (isInt()) {
				return ((Integer) value).doubleValue();
			} else if (isLong()) {
				return ((Long) value).doubleValue();
			} else if (isFloat()) {
				return ((Float) value).doubleValue();
			} else if (isBigDecimal()) {
				return ((BigDecimal) value).doubleValue();
			} else {
				return Integer.valueOf(asString()).doubleValue();
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a Double Object
	 *
	 * @return a Double
	 *
	 * @exception DataSetException
	 */
	public Double asDoubleObj()
	{
		try {
			if (isNull()) {
				return null;
			} else if (isDouble()) {
				return ((Double) value);
			} else if (isString() || isBigDecimal() || isFloat() || isInt() || isLong() || isShort() || isByte()) {
				return new Double(asString());
			} else {
				throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asFloat
	 *
	 * @return a float
	 *
	 * @exception DataSetException
	 */
	public float asFloat()
	{
		try {
			if (isNull()) {
				return 0.0F;
			} else if (isFloat()) {
				return ((Float) value).floatValue();
			} else if (isString()) {
				return Integer.valueOf((String) value).floatValue();
			} else if (isShort()) {
				return ((Short) value).floatValue();
			} else if (isInt()) {
				return ((Integer) value).floatValue();
			} else if (isLong()) {
				return ((Long) value).floatValue();
			} else if (isDouble()) {
				return ((Double) value).floatValue();
			} else if (isBigDecimal()) {
				return ((BigDecimal) value).floatValue();
			} else {
				return Integer.valueOf(asString()).floatValue();
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a Float Obj
	 *
	 * @return a Float
	 *
	 * @exception DataSetException
	 */
	public Float asFloatObj()
	{
		try {
			if (isNull()) {
				return null;
			} else if (isFloat()) {
				return ((Float) value);
			} else if (isString() || isDouble() || isBigDecimal() || isInt() || isLong() || isShort() || isByte()) {
				return new Float(asString());
			} else {
				throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
			}
		} catch (Exception e) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asTime
	 *
	 * @return a Time
	 *
	 * @exception DataSetException
	 */
	public Time asTime()
	{
		try {
			if (isNull()) {
				return null;
			} else if (isTime()) {
				return (Time) value;
			}

			Calendar cal = Calendar.getInstance();

			if (isTimestamp()) {
				cal.setTime((Timestamp) value);

				return new Time(cal.getTime().getTime());
			} else if (isUtilDate()) {
				cal.setTime((java.util.Date) value);

				return new Time(cal.getTime().getTime());
			} else if (isString()) {
				return Time.valueOf((String) value);
			} else {
				return Time.valueOf(asString());
			}
		} catch (IllegalArgumentException a) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		} catch (Exception b) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asTimestamp
	 *
	 * @return a Timestamp
	 *
	 * @exception DataSetException
	 */
	public Timestamp asTimestamp()

	{
		try {
			if (isNull()) {
				return null;
			} else if (isTimestamp()) {
				return (Timestamp) value;
			}

			if (isTime()) {
				Calendar cal = Calendar.getInstance();
				cal.setTime((Time) value);

				return new Timestamp(cal.getTime().getTime());
			} else if (isUtilDate()) {
				return new Timestamp(((java.util.Date) value).getTime());
			} else if (isString()) {
				return Timestamp.valueOf((String) value);
			} else {
				return Timestamp.valueOf(asString());
			}
		} catch (IllegalArgumentException a) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		} catch (Exception b) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Get the value as a asDate
	 *
	 * @return a java.sql.Date
	 *
	 * @exception DataSetException
	 */
	public java.sql.Date asDate()

	{
		try {
			if (isNull()) {
				return null;
			} else if (isDate()) {
				return (java.sql.Date) value;
			}

			Calendar cal = Calendar.getInstance();

			if (isTimestamp()) {
				Timestamp ts = (Timestamp) value;
				long date = ts.getTime();
				int nanos = ts.getNanos();

				return new java.sql.Date(date + (nanos / 1000000));
			} else if (isTime()) {
				cal.setTime((Time) value);

				return java.sql.Date.valueOf(cal.get(Calendar.YEAR) + "-" + leadingZero(cal.get(Calendar.MONTH) + 1) + "-"
						+ leadingZero(cal.get(Calendar.DAY_OF_MONTH)));
			} else if (isUtilDate()) {
				cal.setTime((java.util.Date) value);

				return java.sql.Date.valueOf(cal.get(Calendar.YEAR) + "-" + leadingZero(cal.get(Calendar.MONTH) + 1) + "-"
						+ leadingZero(cal.get(Calendar.DAY_OF_MONTH)));
			} else if (isString()) {
				return java.sql.Date.valueOf((String) value);
			} else {
				return java.sql.Date.valueOf(asString());
			}
		} catch (IllegalArgumentException a) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		} catch (Exception b) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
     * Convert an int to a two digit String with a leading zero where necessary.
     *
     * @param val The value to be converted.
     * @return A two character String with leading zero.
     */
    private String leadingZero(int val)
    {
        return (val < 10 ? "0" : "") + val;
    }

	/**
	 * Get the value as a asUtilDate
	 *
	 * @return a java.util.Date
	 *
	 * @exception DataSetException
	 */
	public java.util.Date asUtilDate()

	{
		try {
			if (isNull()) {
				return null;
			} else if (isUtilDate()) {
				return (java.util.Date) value;
			}

			Calendar cal = Calendar.getInstance();

			if (isTimestamp()) {
				Timestamp ts = (Timestamp) value;
				long date = ts.getTime();
				int nanos = ts.getNanos();

				return new java.util.Date(date + (nanos / 1000000));
			} else if (isTime()) {
				cal.setTime((Time) value);

				return java.sql.Date.valueOf(cal.get(Calendar.YEAR) + "-" + leadingZero(cal.get(Calendar.MONTH) + 1) + "-"
						+ leadingZero(cal.get(Calendar.DAY_OF_MONTH)));
			} else if (isUtilDate()) {
				cal.setTime((java.util.Date) value);

				return java.sql.Date.valueOf(cal.get(Calendar.YEAR) + "-" + leadingZero(cal.get(Calendar.MONTH) + 1) + "-"
						+ leadingZero(cal.get(Calendar.DAY_OF_MONTH)));
			} else {
				return null;
			}
		} catch (IllegalArgumentException a) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		} catch (Exception b) {
			throw new SwingObjectRunException( null, ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}

	/**
	 * Is the value a isBigDecimal
	 *
	 * @return true if BigDecimal
	 */
	public boolean isBigDecimal() {
		return value instanceof BigDecimal;
	}

	/**
	 * Is the value a isByte
	 *
	 * @return true if is Byte
	 */
	public boolean isByte() {
		return value instanceof Byte;
	}

	/**
	 * Is the value a isBytes
	 *
	 * @return true if is byte[]
	 */
	public boolean isBytes() {
		return value instanceof byte[];
	}

	/**
	 * Is the value a isDate
	 *
	 * @return true if is java.sql.Date
	 */
	public boolean isDate() {
		return value instanceof java.sql.Date;
	}

	/**
	 * Is the value a isShort
	 *
	 * @return true if is Short
	 */
	public boolean isShort() {
		return value instanceof Short;
	}

	/**
	 * Is the value a isInt
	 *
	 * @return true if is Integer
	 */
	public boolean isInt() {
		return value instanceof Integer;
	}

	/**
	 * Is the value a isLong
	 *
	 * @return true if is Long
	 */
	public boolean isLong() {
		return value instanceof Long;
	}

	/**
	 * Is the value a isDouble
	 *
	 * @return true if is Double
	 */
	public boolean isDouble() {
		return value instanceof Double;
	}

	/**
	 * Is the value a isFloat
	 *
	 * @return true if is Float
	 */
	public boolean isFloat() {
		return value instanceof Float;
	}

	/**
	 * Is the value a isBoolean
	 *
	 * @return true if is Boolean
	 */
	public boolean isBoolean() {
		return value instanceof Boolean;
	}

	/**
	 * Is the value a isNull
	 *
	 * @return true if is null
	 */
	public boolean isNull() {
		return value == null;
	}

	/**
	 * Is the value a isString
	 *
	 * @return true if is String
	 */
	public boolean isString() {
		return value instanceof String;
	}

	/**
	 * Is the value a isTime
	 *
	 * @return true if is java.sql.Time
	 */
	public boolean isTime() {
		return value instanceof java.sql.Time;
	}

	/**
	 * Is the value a isTimestamp
	 *
	 * @return true if is java.sql.Timestamp
	 */
	public boolean isTimestamp() {
		return value instanceof java.sql.Timestamp;
	}

	/**
	 * Is the value a isUtilDate
	 *
	 * @return true if is java.util.Date
	 */
	public boolean isUtilDate() {
		return value instanceof java.util.Date;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object valueObject) {
		this.value = valueObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataWrapper other = (DataWrapper) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}



}