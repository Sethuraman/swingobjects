package test;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.aesthete.swingobjects.datamap.converters.JTextComponentConverter;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaClass;
import org.apache.commons.beanutils.MutableDynaClass;
import org.apache.commons.beanutils.PropertyUtils;

public class TestData {

	private String tftest = "test";
	private String tftest1;
	private String cbCombo;
	private boolean chkBx;

	public String getCbCombo() {
		return cbCombo;
	}

	public void setCbCombo(String cbCombo) {
		this.cbCombo = cbCombo;
	}

	public boolean isChkBx() {
		return chkBx;
	}

	public void setChkBx(boolean chkBx) {
		this.chkBx = chkBx;
	}

	public String getTftest() {
		return tftest;
	}

	public void setTftest(String tftest) {
		this.tftest = tftest;
	}

	public String getTftest1() {
		return tftest1;
	}

	public void setTftest1(String tftest1) {
		this.tftest1 = tftest1;
	}

	public static void main(String[] args) {
		try {
			TestData bean = new TestData();
			PropertyUtils.setProperty(bean, "tftest", "something");
			System.out.println(bean.getTftest());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
