package org.aesthete.swingobjects;

public enum YesNo {
	YES("Y","Yes"),
	NO("N","No");

	private String dbtype;
	private String disp;

	private YesNo(String dbtype, String disp) {
		this.dbtype = dbtype;
		this.disp = disp;
	}

	public String dbtype() {
		return dbtype;
	}

	public String disp() {
		return disp;
	}

	public static YesNo fromDisp(String fromDisp) {
		for (YesNo var : YesNo.values()) {
			if (var.disp().equalsIgnoreCase(fromDisp)) {
				return var;
			}
		}
		return null;
	}

	public static YesNo fromDbtype(String fromDbtype) {
		for (YesNo var : YesNo.values()) {
			if (var.dbtype().equalsIgnoreCase(fromDbtype)) {
				return var;
			}
		}
		return null;
	}

}
