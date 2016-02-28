package cl.gov.subdere.chileindica.sgd;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cl.gov.subdere.chileindica.sgd.adm.User;
import cl.gov.subdere.chileindica.sgd.api.DocumentRequest;
import cl.gov.subdere.chileindica.sgd.mgmt.Sgdoc;




public class DocumentMgmtTest {
	
	
	private Sgdoc sgdoc;
	
	@Before public void setUp(){
		sgdoc=new Sgdoc();
	}
	
	@Test public void createDocumentTest(){
		
		DocumentRequest documentRequest=new DocumentRequest(); 
		Expedient expedient=sgdoc.createDocument(documentRequest);
		Assert.assertEquals(expedient.documents(),1 );
		
	}

	@Test public void createExpedientTest(){
		Date now=new Date();
		DocumentRequest documentRequest=new DocumentRequest(); 
		Expedient expedient=sgdoc.createDocument(documentRequest);
		Date date=expedient.getCreationDate();
		Assert.assertNotNull(date);
		Assert.assertTrue(date!=null && date.after(now));
	}
	
	@Test public void derivateExpedientTest(){
		User user=new User();
		
		Expedient exp=sgdoc.search();
		sgdoc.derivate(exp, user);
		
	}
	
	@Test public void searchExpedientPatyhTest(){
		
	}
	
	@Test public void editExpedientTest(){
		
	}
	
	@Test public void joinExpedientsTest(){
		
	}
	
	@Test public void archiveExpedientTest(){
		
	}
	
	@Test public void updateExpedienteTest(){
		
	}
	
	@Test public void setVisibilityExpedienteTest(){
		
	}
	
	@Test public void signDocumentTest(){
		
	}
	
	@Test public void signDocumentandDeriveExpedientTest(){
		
	}
	
}
