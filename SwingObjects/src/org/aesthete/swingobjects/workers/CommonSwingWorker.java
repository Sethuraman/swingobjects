package org.aesthete.swingobjects.workers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.aesthete.swingobjects.SwingObjProps;
import org.aesthete.swingobjects.datamap.DataMapper;
import org.aesthete.swingobjects.datamap.SwingObjData;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.exceptions.SwingObjectsExceptions;
import org.aesthete.swingobjects.scope.RequestScope;
import org.aesthete.swingobjects.scope.RequestScopeObject;
import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.WaitDialog;

public abstract class CommonSwingWorker extends SwingWorker<Void, Void> implements SwingWorkerInterface{

	private String action;
	private Timer timer;
	private static final int WAIT_DELAY=Integer.parseInt(SwingObjProps.getProperty("waitdialogopentime"));
	private RequestScopeObject scopeObj;


	public CommonSwingWorker() {
		super();
		this.action = "";
		scopeObj=RequestScope.getRequestObj();
	}

	public CommonSwingWorker(String action) {
		super();
		this.action = action;
		scopeObj=RequestScope.getRequestObj();
	}

	@Override
	protected Void doInBackground() throws Exception {
		startTimerForWaitDialog();

		try {
			callModel();
		}catch(SwingObjectException e) {
			scopeObj.setErrorObj(e);
		}catch(SwingObjectRunException e) {
			scopeObj.setErrorObj(e);
		}catch(Exception e) {
			scopeObj.setErrorObj(new SwingObjectException(e,ErrorSeverity.SEVERE, CommonSwingWorker.class));
		}

		return null;
	}

	private void startTimerForWaitDialog() {
		timer=new Timer(WAIT_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						WaitDialog.displayWaitDialog();
					}
				});
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	@Override
	protected void done() {
		WaitDialog.hideWaitDialog();
		CommonUI.restoreComponentsToInitialState(scopeObj.getFieldsOfTheContainer());
		handleErrorAndCallConnector();
	}

	/**
	 * Only errors INFO and WARNING will go ahead and call the Connector. Error and Severe, will not.
	 * Throw the correct errors from the model and user will only see the error and stop.
	 * If you would like to call the connector inspite of the error/severe in your application, override this method
	 * and change the implementation.
	 * Make sure you call the DataMapper, to get the data in {@link SwingObjData} that you have set in your model, to automatically update the
	 * GUIs. You will get to set the unmapped data from the model to the GUI in the connector.
	 * The line you will need to retain for auto Data Mapping is:
	 * <pre>
	 *    DataMapper.mapGUI(scopeObj.getContainer());
	 * </pre>
	 */
	protected void handleErrorAndCallConnector() {
		SwingObjectsExceptions e = scopeObj.getErrorObj();
		boolean isCall=true;
		if(e!=null) {
			CommonUI.showErrorDialogForComponent(e);
			switch(e.getErrorSeverity()) {
				case ERROR:
				case SEVERE: isCall=false; break;
				default: // call connector
							break;
			}
		}

		if(isCall) {
			DataMapper.mapGUI(scopeObj.getContainer());
			callConnector();
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
