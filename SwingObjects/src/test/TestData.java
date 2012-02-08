package test;

import javax.swing.JComboBox;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.annotations.Column;
import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.WaitDialog;
import org.aesthete.swingobjects.view.table.RowDataBean;


public class TestData extends RowDataBean{

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
			SwingObjectsInit.init("/swingobjects.properties", "/error.properties");
//			JXPanel panel = new JXPanel();
//			MattePainter mp = new MattePainter(Colors.LightBlue.alpha(0.5f));
//			GlossPainter gp = new GlossPainter(Colors.White.alpha(0.3f), GlossPainter.GlossPosition.TOP);
//			panel.setBackgroundPainter(new CompoundPainter<JXPanel>(mp, gp));
//
//			SwingObjFormBuilder builder=new SwingObjFormBuilder(new FormLayout("10dlu:grow,150dlu,5dlu,150dlu,10dlu:grow", "10dlu:grow,300dlu,10dlu:grow"), panel);
//			builder.addComponents(new JLabel("Something"),new JTextField());
//
//
//			JXFrame frame=new JXFrame();
//			frame.setContentPane(panel);
			CommonUI.showOnScreen(WaitDialog.getInstance());


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cbCombo == null) ? 0 : cbCombo.hashCode());
		result = prime * result + (chkBx ? 1231 : 1237);
		result = prime * result + ((tftest == null) ? 0 : tftest.hashCode());
		result = prime * result + ((tftest1 == null) ? 0 : tftest1.hashCode());
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
		TestData other = (TestData) obj;
		if (cbCombo == null) {
			if (other.cbCombo != null)
				return false;
		} else if (!cbCombo.equals(other.cbCombo))
			return false;
		if (chkBx != other.chkBx)
			return false;
		if (tftest == null) {
			if (other.tftest != null)
				return false;
		} else if (!tftest.equals(other.tftest))
			return false;
		if (tftest1 == null) {
			if (other.tftest1 != null)
				return false;
		} else if (!tftest1.equals(other.tftest1))
			return false;
		return true;
	}


}
