package cl.gov.subdere.chileindica.sgd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Expedient {
	
	private Date creationDate;
	
	List<Document> documents=new ArrayList<Document>();
	
	public Expedient(){
		this.creationDate=new Date();
	}
	
	public void addDocument(Document document){
		documents.add(document);
	}

	public int documents() {
		return documents.size();
	}

	public Date getCreationDate() {
		return creationDate;
	}

}
