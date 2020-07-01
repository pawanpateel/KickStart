package com.phorm.kStart.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class CleanProcessor {

	private static final File CLEAN_FOLDER=new File("/pumd");

	public void cleanSweep() {
		long curTime=System.currentTimeMillis();
		long diffTime=curTime-(60000*30);
		try {
			delFile(CLEAN_FOLDER, diffTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void delFile(File folderPath, long lastTime) throws FileNotFoundException,
	IOException {
		for (File file : folderPath.listFiles()) {
			if(file.lastModified()<=lastTime) {
				if (file.isDirectory()) {
					delFile(file, lastTime);
				}
				file.delete();
			}
		}

	}


}
