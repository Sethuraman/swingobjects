package org.aesthete.swingobjects.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FileHelper {

	public static void copyFromTo(File fromFile,File toFile) throws SwingObjectException{
		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(fromFile).getChannel();
			destination = new FileOutputStream(toFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}catch(Exception e){
			throw new SwingObjectException(e, ErrorSeverity.SEVERE,FileHelper.class);
		}finally {
			try {
				try {
					if(source != null) {
						source.close();
					}
				} finally {
					if(destination != null) {
						destination.close();
					}
				}
			} catch (Exception e) {
				throw new SwingObjectException(e, ErrorSeverity.SEVERE,FileHelper.class);
			}
		}
	}

	public static boolean isFileLocked(String filename) throws SwingObjectException {
		boolean isLocked=false;
		RandomAccessFile fos=null;
		try {
			File file = new File(filename);
			if(file.exists()) {
				fos=new RandomAccessFile(file,"rw");
			}
		} catch (FileNotFoundException e) {
			isLocked=true;
		}catch (Exception e) {
			throw new SwingObjectException("Error while checking if a file is locked",e, ErrorSeverity.SEVERE,FileHelper.class);
		}finally {
			try {
				if(fos!=null) {
					fos.close();
				}
			}catch(Exception e) {
				throw new SwingObjectException("Error while checking if a file is locked",e, ErrorSeverity.SEVERE,FileHelper.class);
			}
		}
		return isLocked;
	}

	public static void deleteFolder(String folderPath) throws SwingObjectException {
		File folder=new File(folderPath);
		if(folder.exists()) {
			try {
				FileUtils.deleteDirectory(folder);
			}catch(IOException e) {
				new SwingObjectException("Error while deleting",e, ErrorSeverity.SEVERE,FileHelper.class);
				String message = e.getMessage();
				if(message.indexOf("\\")!=-1) {
					message=message.replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("/"));
				}
				throw new SwingObjectException("Unable to delete folder", e, ErrorSeverity.ERROR,FileHelper.class);
			}
		}
	}

	public static void deleteFolderWaitIfOpen(final File folder) {
		try {
			FileUtils.deleteDirectory(folder);
		}catch(final IOException e) {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						new SwingObjectException("Error while deleting",e, ErrorSeverity.SEVERE,FileHelper.class);
						String message = e.getMessage();
						if(message.indexOf("\\")!=-1) {
							message=message.replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("/"));
						}
						int option = JOptionPane.showConfirmDialog(null, String.format("Unable to delete folder %s.\nPlease close to proceed.",folder.getAbsolutePath()),
								"Warning", JOptionPane.DEFAULT_OPTION);

						if (option == JOptionPane.YES_OPTION) {
							deleteFolderWaitIfOpen(folder);
						}
					}
				});
			} catch (Exception e1) {
				new SwingObjectException("Error while deleting",e, ErrorSeverity.SEVERE,FileHelper.class);
			}
		}

	}

	public static void deleteFile(String filename) {
		File file=new File(filename);
		file.delete();
	}

	public static boolean checkIf2FilesAreTheSame(File file1,File file2) throws SwingObjectException {
		try {
			if (!file1.equals(file2)) {
				if(!file1.getName().equals(file2.getName())){
					return false;
				}
				File dir1 = new File(FilenameUtils.getFullPath(file1.getAbsolutePath()));
				File dir2 = new File(FilenameUtils.getFullPath(file2.getAbsolutePath()));
				File tempFile = File.createTempFile("checker", "checker", dir1);
				File checkTempFile=new File(dir2,tempFile.getName());
				boolean isDirSame=checkTempFile.exists();
				checkTempFile.delete();
				return isDirSame;
			}else {
				return true;
			}
		} catch (Exception e) {
			throw new SwingObjectException("Error while checking if 2 files are the same",e, ErrorSeverity.SEVERE,FileHelper.class);
		}
	}


	public static String getFileResource(String relativepath) {
		URL resource = FileHelper.class.getResource(relativepath);
		if(resource==null) {
			return null;
		}
		String file=resource.getFile();
		try {
			file=URLDecoder.decode(file,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SwingObjectRunException(e,ErrorSeverity.SEVERE,FileHelper.class);
		}
		return file;
	}
}


