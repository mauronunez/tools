package cl.gov.subdere.chileindica.sgd.mgmt;

import cl.gov.subdere.chileindica.sgd.Builder;
import cl.gov.subdere.chileindica.sgd.Document;
import cl.gov.subdere.chileindica.sgd.Expedient;
import cl.gov.subdere.chileindica.sgd.Path;
import cl.gov.subdere.chileindica.sgd.adm.User;
import cl.gov.subdere.chileindica.sgd.api.DocumentRequest;

public class Sgdoc {

	public Expedient createDocument(DocumentRequest documentRequest) {
		Document document=Builder.buildDocument(documentRequest);
		
		Expedient expedient=new Expedient();
		expedient.addDocument(document);
		
		return expedient;
		
	}

	public void derivate(Expedient exp, User user) {
		Path path=createHistory(exp,user);
	}

	private Path createHistory(Expedient exp, User user) {
		return new Path();
	}

	public Expedient search() {
		return new Expedient();
	}

}
