package cl.chileindica.weka;

import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class Extractor {
	
	public Instances extraer(int ano) throws Exception{
		
		InstanceQuery query = new InstanceQuery();
		query.setDatabaseURL("jdbc:mysql://127.0.0.1:3306/chileindica");

		query.setUsername("root");
		query.setPassword("subdere");
		query.setQuery("select I.N_REGION,I.N_SECTOR,I.SOLICITADO from INVERSION I WHERE C_INSTITUCION=1 LIMIT 1500");
		Instances source = query.retrieveInstances();
		source.setClass(source.attribute("N_REGION"));
		return source;
	}

}
