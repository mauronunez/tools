package cl.gov.subdere.chileindica.sgd;

import cl.gov.subdere.chileindica.sgd.api.DocumentRequest;

public class Builder {
	
	public static Document buildDocument(DocumentRequest request){
		return new Document("title");
	}

}
