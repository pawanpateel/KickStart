package com.phorm.kStart.process;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.phorm.kStart.util.AddForm;
import com.phorm.kStart.util.AppFile;
import com.phorm.kStart.util.ConfigFile;
import com.phorm.kStart.util.ControlFile;
import com.phorm.kStart.util.DaoFile;
import com.phorm.kStart.util.ListForm;
import com.phorm.kStart.util.ModelFile;
import com.phorm.kStart.util.PomFile;
import com.phorm.kStart.util.PropFile;
import com.phorm.kStart.util.UpdateForm;
import com.phorm.kStart.model.Application;
import com.phorm.kStart.model.Modal;

@Scope("prototype")
public class TermProcessor {

	@Autowired
	private AddForm addJsp; 

	@Autowired
	private AppFile aFile;

	@Autowired
	private ConfigFile cfgFile;

	@Autowired
	private PomFile pFile;

	@Autowired
	private PropFile prpFile;

	@Autowired
	private ModelFile mFile;

	@Autowired
	private DaoFile dFile;

	@Autowired
	private ControlFile cFile;

	@Autowired
	private UpdateForm updateJsp;

	@Autowired
	private ListForm listJsp;


	public void saveTerms(HttpServletRequest request) {
		Application appDetails=(Application) request.getSession(false).getAttribute("appDetails");

		String appName=appDetails.getAppName();
		String appFolder="/pumd/"+appName+"/src/main/java/com/"+appName;
		String configFolder="/pumd/"+appName+"/src/main/java/com/"+appName+"/config";
		String pomFolder="/pumd/"+appName;
		String propFolder="/pumd/"+appName+"/src/main/resources";
		createFolderStructure(appFolder);
		createFolderStructure(configFolder);
		createFolderStructure(pomFolder);
		createFolderStructure(propFolder);

		aFile.createFile(appFolder, appName, appDetails.getUserName(), appDetails.getPlaceName());

		cfgFile.createFile(configFolder, appName);

		pFile.createFile(pomFolder, appName);

		prpFile.createFile(propFolder);

		String modelFolder="/pumd/"+appName+"/src/main/java/com/"+appName+"/model";
		String daoFolder="/pumd/"+appName+"/src/main/java/com/"+appName+"/dao";
		String controlFolder="/pumd/"+appName+"/src/main/java/com/"+appName+"/controller";
		String jspFolder="/pumd/"+appName+"/src/main/webapp/WEB-INF/views";
		createFolderStructure(modelFolder);
		createFolderStructure(daoFolder);
		createFolderStructure(controlFolder);

		String mdlPackageName="com."+appName+".model";
		String daoPackageName="com."+appName+".dao";
		String ctrlPackageName="com."+appName+".controller";

		ArrayList<Modal> listModal=appDetails.getModels();
		Map<String, Modal> mpModal=new HashMap<String, Modal>();
		ArrayList<String>  modelList=new ArrayList<String>();
		String listModalName="";
		for(Modal m: listModal) {
			listModalName=m.getModelName();
			modelList.add(listModalName);
			mpModal.put(listModalName, m);
		}
		
		Modal tempModal;		
		boolean createJSP=true;
		
		for(Map.Entry<String, Modal> entry: mpModal.entrySet()){

			tempModal=entry.getValue();
			createFolderStructure(jspFolder+"/"+tempModal.getModelName());
			createJSP=mFile.createFile(tempModal, modelFolder, mdlPackageName, modelList, mpModal);
			dFile.createFile(tempModal, daoFolder, daoPackageName, modelList,mpModal);		
			cFile.createFile(tempModal, controlFolder, ctrlPackageName, appName);
			if(createJSP) {
				addJsp.createFile(tempModal, jspFolder, appName, modelList, mpModal, modelFolder);
				updateJsp.createFile(tempModal, jspFolder, appName, modelList, mpModal);
				listJsp.createFile(tempModal, jspFolder, appName, modelList, mpModal);
			}
		}


	}
	private void createFolderStructure(String folderPath) {
		Path fldrPath=Paths.get(folderPath);
		try {
			Files.createDirectories(fldrPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
