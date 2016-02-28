package cl.chileindica.qa.handler;

import java.util.*;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.Page;

public class CustomAlertHandler implements AlertHandler{
	 
		private List<String>alerts=new LinkedList<String>(); 
	
			@Override
			public void handleAlert(Page page, String message) {
				alerts.add(message);
			}
			
			public String getLastMessage(){
				if(alerts.size()==0) return null;
				return alerts.get(alerts.size()-1);
			}
		
}
