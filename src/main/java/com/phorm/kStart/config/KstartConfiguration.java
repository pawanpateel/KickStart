package com.phorm.kStart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.phorm.kStart.process.AppProcessor;
import com.phorm.kStart.process.CleanProcessor;
import com.phorm.kStart.process.FieldProcessor;
import com.phorm.kStart.process.InstructProcessor;
import com.phorm.kStart.process.ModelProcessor;
import com.phorm.kStart.process.SsnProcessor;
import com.phorm.kStart.process.TermProcessor;
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

@Configuration
public class KstartConfiguration {
	
		@Bean
		public AppProcessor appRead() {
			return new AppProcessor();
		}
		
		@Bean
		public ModelProcessor modelRead() {
			return new ModelProcessor();
		}
		
		@Bean
		public FieldProcessor fieldRead() {
			return new FieldProcessor();
		}
	
	 	@Bean
		public TermProcessor termRead(){ 	        
	        return new TermProcessor();
	    }
	 	
	 	@Bean
	 	public SsnProcessor inspectSsn() {
	 		return new SsnProcessor();
	 	}
	 	@Bean
	 	public CleanProcessor cleanDump() {
	 		return new CleanProcessor();
	 	}
	 	
	 	@Bean
		public AddForm addJsp(){
			return new AddForm();
		} 
		
		@Bean
		public AppFile aFile(){
			return new AppFile();
		}
		
		@Bean
		public ConfigFile cfgFile(){
			return new ConfigFile();
		}
		
		@Bean
		public PomFile pFile(){
			return new PomFile();
		}
		
		@Bean
		public PropFile prpFile(){
			return new PropFile();
		}
		
		@Bean
		public ModelFile mFile(){
			return new ModelFile();
		}
		
		@Bean
		public DaoFile dFile(){
			return new DaoFile();
		}
		
		@Bean
		public ControlFile cFile(){
			return new ControlFile();
		}
		
		@Bean
		public UpdateForm updateJsp(){
			return new UpdateForm();
		}
		
		@Bean
		public ListForm listJsp(){
			return new ListForm();
		}
		
		@Bean
		public InstructProcessor stepRead(){
			return new InstructProcessor();
		}
}
