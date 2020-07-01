package com.phorm.kStart.process;


import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

import com.phorm.kStart.model.Modal;


@Scope("prototype")
public class ModelProcessor {

	public ArrayList<Modal> process(ArrayList<Modal> inputList) {
		
		ArrayList<Modal> processedList=new ArrayList<Modal>();
		for(Modal m:inputList) {
			if(!m.getModelName().equals("tnkvaiaigkddiahul")) {
				processedList.add(m);
			}
		}
		return processedList;
	}


}
