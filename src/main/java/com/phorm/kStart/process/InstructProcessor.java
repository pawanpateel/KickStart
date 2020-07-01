package com.phorm.kStart.process;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;

import com.phorm.kStart.model.Application;

@Scope("prototype")
public class InstructProcessor {


	private static final int BUFFER_SIZE = 4096;

	public void genZip(HttpServletRequest request, HttpServletResponse response) {		


		try {
			Application appDetails=(Application) request.getSession(false).getAttribute("appDetails");

			String appName=appDetails.getAppName();
			boolean isFileReady=(boolean)request.getSession(false).getAttribute("zipReady");
			if(!isFileReady) {				
				zip(new File("/pumd/"+appName), "/pumd/"+appName+".zip");
				request.getSession(false).setAttribute("zipReady", true);
			}

			downloadFile(response,"/pumd/"+appName+".zip");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void zip(File folderRoot, String destZipFile) throws FileNotFoundException,
	IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFile));
		zipDirectory(folderRoot, folderRoot.getName(), zos);		
		zos.flush();
		zos.close();
	}

	private void zipDirectory(File folder, String parentFolder,
			ZipOutputStream zos) throws FileNotFoundException, IOException {

		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				zipDirectory(file, parentFolder + "/" + file.getName(), zos);
				continue;
			}
			zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			long bytesRead = 0;
			byte[] bytesIn = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = bis.read(bytesIn)) != -1) {
				zos.write(bytesIn, 0, read);
				bytesRead += read;
			}
			bis.close();
			zos.closeEntry();
		}
	}

	private void downloadFile(HttpServletResponse resonse, String filePath) throws IOException {
		File file = new File(filePath);

		resonse.setContentType("application/zip");
		resonse.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = 0;
		while ((bytesRead = inStrem.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		outStream.flush();
		inStrem.close();
		outStream.close();
	}


}
