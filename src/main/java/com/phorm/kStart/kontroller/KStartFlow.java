package com.phorm.kStart.kontroller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.phorm.kStart.model.Application;
import com.phorm.kStart.model.Modal;
import com.phorm.kStart.model.ModelList;
import com.phorm.kStart.process.AppProcessor;
import com.phorm.kStart.process.CleanProcessor;
import com.phorm.kStart.process.FieldProcessor;
import com.phorm.kStart.process.InstructProcessor;
import com.phorm.kStart.process.ModelProcessor;
import com.phorm.kStart.process.SsnProcessor;
import com.phorm.kStart.process.TermProcessor;




@Controller
public class KStartFlow {

	@Autowired
	private ModelProcessor modelRead;
	
	@Autowired
	private FieldProcessor fieldRead;
	
	@Autowired
	private TermProcessor termRead; 

	@Autowired
	private InstructProcessor stepRead;

	@Autowired
	private CleanProcessor cleanMap;

	@Autowired
	private SsnProcessor ssnInspect;
	
	@Autowired
	private AppProcessor appRead;

	@GetMapping("/")
	public String applicationForm(@Autowired Application appDetails, Model model) {
		cleanMap.cleanSweep();
		model.addAttribute("stageOne", appDetails);
		return "phorm/application";
	}	
	@PostMapping("/phorm/appSave.do")
	public String appDetailSave(@Valid @ModelAttribute("stageOne") Application appDetails,
			BindingResult result, HttpServletRequest request) {		
		if(result.hasErrors()) {
			return "phorm/application";
		}else {
					
    		appDetails.setAppName(appRead.getAppName(appDetails.getAppName()));
			request.getSession(true).setAttribute("appDetails", appDetails);
			request.getSession(false).setMaxInactiveInterval(600);
			request.getSession(false).setAttribute("zipReady", false);
			return "redirect:/phorm/model.do";
		}

	}

	@GetMapping("/phorm/model.do")
	public String modelForm(Model model, HttpServletRequest request, @Autowired ModelList models) {
		if(ssnInspect.healthCheck(request)) {		
			Application appDetails=(Application) request.getSession(false).getAttribute("appDetails");
			if(!appDetails.getModels().isEmpty()) {
				models.setModels(appDetails.getModels());
			}
			model.addAttribute("stageTwo", models);
			return "phorm/model";
		}else {
			return "redirect:/";
		}
	}
	@PostMapping("/phorm/modelSave.do")
	public String modelSave(HttpServletRequest request, @Valid @ModelAttribute("stageTwo") ModelList models,
			BindingResult result, Model model) {	
		
		if(ssnInspect.healthCheck(request)) {
			if(result.hasErrors()) {
				ArrayList<Modal> modList=models.getModels();				
				models.setModels(modelRead.process(modList));
				model.addAttribute("stageTwo", models);
				return "phorm/model";
			}else {
				Application appDetails=(Application) request.getSession(false).getAttribute("appDetails");
				ArrayList<Modal> modList=models.getModels();				
				appDetails.setModels(modelRead.process(modList));
				request.getSession(false).setAttribute("appDetails", appDetails);
				return "redirect:/phorm/field.do";
			}
		}else {
			return "redirect:/";
		}
	}

	@GetMapping("/phorm/field.do")
	public String fieldForm(HttpServletRequest request, Model model, @Autowired ModelList models) {
		if(ssnInspect.healthCheck(request)) {
			Application appDetails=(Application) request.getSession(false).getAttribute("appDetails");
			models.setModels(appDetails.getModels());
			model.addAttribute("stageThree", models);		
			return "phorm/field";
		}else {
			return "redirect:/";
		}
	}
	@PostMapping("/phorm/fieldSave.do")
	public String fieldSave(HttpServletRequest request, @Valid @ModelAttribute("stageThree") ModelList models,
			BindingResult result, Model model) {
		if(ssnInspect.healthCheck(request)) {
			if(result.hasErrors()) {
				ArrayList<Modal> modList=models.getModels();				
				models.setModels(fieldRead.errorProcess(modList));
				model.addAttribute("stageThree", models);
				return "phorm/field";
			}else {
				Application appDetails=(Application) request.getSession(false).getAttribute("appDetails");
				ArrayList<Modal> modList=models.getModels();
				appDetails.setModels(fieldRead.finalProcess(modList));
				request.getSession(false).setAttribute("appDetails", appDetails);
				return "redirect:/phorm/term.do";
			}			
		}else {
			return "redirect:/";
		}

	}

	@GetMapping("/phorm/term.do")
	public String termShow(HttpServletRequest request) {	

		if(ssnInspect.healthCheck(request)) {
			return "phorm/term";
		}else {
			return "redirect:/";
		}
		
	}
	@PostMapping("/phorm/termSave.do")
	public void termShow(HttpServletRequest request, HttpServletResponse response) {
		if(ssnInspect.healthCheck(request)) {
		boolean isFileReady=(boolean)request.getSession(false).getAttribute("zipReady");
		if(!isFileReady) {

			termRead.saveTerms(request);
		}						
		stepRead.genZip(request, response);
		}else {
			ssnInspect.displaySessionTimeOut(response);
		}

	}

	@GetMapping("/phorm/clean.do")
	public String cleanArea() {		

		cleanMap.cleanSweep();
		return "redirect:/";

	}

}