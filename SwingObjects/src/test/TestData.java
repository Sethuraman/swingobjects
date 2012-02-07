package test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.view.table.SwingObjTable;


public class TestData {

	@Column(name="Column 1",index=0)
	private String tftest = "test";
	@Column(name="Column 2",index=1,editable=true)
	private String tftest1;
	@Column(name="Column 3",index=2,editable=true,type=JComboBox.class)
	private String cbCombo;
	@Column(name="Column 4",index=3,editable=true)
	private boolean chkBx;

	public TestData(){

	}

	public TestData(String tftest, String tftest1, String cbCombo, boolean chkBx) {
		super();
		this.tftest = tftest;
		this.tftest1 = tftest1;
		this.cbCombo = cbCombo;
		this.chkBx = chkBx;
	}

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
			System.out.println("<html>Try</html>".replaceAll("<html>|</html>", ""));


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
