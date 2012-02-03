package org.aesthete.swingobjects.workers;

import javax.swing.SwingWorker;

public abstract class CommonSwingWorker extends SwingWorker<Void, Void>{

	private String action;

	public CommonSwingWorker(String action) {
		super();
		this.action = action;
	}

	@Override
	protected Void doInBackground() throws Exception {
		return null;
	}

	@Override
	protected void done() {
	}

	public abstract void callModel();

	public abstract void callConnector();

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
