package org.aesthete.swingobjects.view;

import org.aesthete.swingobjects.SwingObjUtils;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

public class DesktopHelper  implements ClipboardOwner{

	private static Logger logger= Logger.getLogger(DesktopHelper.class);

	public void dispURLInBrowser(String URL)  {
		boolean isNotShown=false;
        if(Desktop.isDesktopSupported()){
            Desktop desktop=Desktop.getDesktop();
            if(desktop.isSupported(Desktop.Action.BROWSE)){
                try {
                    desktop.browse(new URI(URL));
                } catch (Exception ex) {
                    throw new SwingObjectRunException(ex, this.getClass());
                }
            } else{
                isNotShown=true;
            }
        }else{
            isNotShown=true;
        }
        if(isNotShown){
            throw new SwingObjectRunException(ErrorSeverity.SEVERE,"Cannot launch browser for url "+URL, this);
        }
	}

	public void openFile(String filename) {
		try {
			File file = new File(filename);
			if(!file.exists()) {
				throw new SwingObjectRunException(new FileNotFoundException(file.getAbsolutePath()), this.getClass());
			}

			if(SwingObjUtils.isWindows()) {
				if(file.isDirectory()) {
					CommandLine cl=CommandLine.parse("cmd.exe");
					cl.addArgument("/C");
					cl.addArgument("start");
					cl.addArgument("Open File");
					cl.addArgument(FilenameUtils.separatorsToWindows(file.getAbsolutePath()));
					DefaultExecutor executor=new DefaultExecutor();
					ExecuteWatchdog watchdog=new ExecuteWatchdog(60000);
					executor.setWatchdog(watchdog);
					int exitValue=executor.execute(cl);
					logger.debug("exit value obtained while opening file = "+exitValue);
				}else {
					CommandLine cl=CommandLine.parse("rundll32 SHELL32.DLL,ShellExec_RunDLL");
					cl.addArgument(FilenameUtils.separatorsToWindows(file.getAbsolutePath()));
					DefaultExecutor executor=new DefaultExecutor();
					ExecuteWatchdog watchdog=new ExecuteWatchdog(60000);
					executor.setWatchdog(watchdog);
					executor.setExitValues(new int[] {0,1});
					int exitValue=executor.execute(cl);
					logger.debug("exit value obtained while opening file = "+exitValue);
				}
			}else if(SwingObjUtils.isLinux()){
                CommandLine commandLine=new CommandLine("xdg-open");
                commandLine.addArgument(new File(filename).toURI().toString());
                DefaultExecutor executor=new DefaultExecutor();
                ExecuteWatchdog watchdog=new ExecuteWatchdog(60000);
                executor.setWatchdog(watchdog);
                int exitValue=executor.execute(commandLine);
                logger.debug("exit value obtained while opening file = "+exitValue);
            }else {
				Desktop desktop=Desktop.getDesktop();
				desktop.open(file);
			}

		}catch (Exception e) {
            throw new SwingObjectRunException(ErrorSeverity.SEVERE,this.getClass());
		}
	}

	public void copyToClipboard(String text) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Clipboard clip = tk.getSystemClipboard();
		StringSelection formPathtoClipboard = new StringSelection(text);
		clip.setContents(formPathtoClipboard, this);
	}

	public String getFromClipboard() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Clipboard clip = tk.getSystemClipboard();
		Transferable clipData=clip.getContents(clip);
		 if(clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				return (String)(clipData.getTransferData(DataFlavor.stringFlavor));
			} catch (UnsupportedFlavorException e) {
                throw new SwingObjectRunException(ErrorSeverity.SEVERE,this.getClass());
			} catch (IOException e) {
                throw new SwingObjectRunException(ErrorSeverity.SEVERE,this.getClass());
			}
		 }
		 return "";
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		logger.debug("the pasted contents have gone.. we have lost ownshership of clipboard");
	}
}
