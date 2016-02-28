package cl.chileindica.weka;

import weka.core.Instances;

public class Evolucion {
	
	Extractor extractor=new Extractor();
	
	public static void main(String args[]) throws Exception{
		
		Evolucion evolucion=new Evolucion();
		
		evolucion.compare(2010,2010); 
		evolucion.compare(2010,2011);
		evolucion.compare(2010,2012);
		evolucion.compare(2010,2013);
		evolucion.compare(2010,2014);
		evolucion.compare(2010,2015);
		
		evolucion.compare(2011,2011);
		evolucion.compare(2011,2012);
		evolucion.compare(2011,2013);
		evolucion.compare(2011,2014);
		evolucion.compare(2011,2015);

		evolucion.compare(2012,2012);
		evolucion.compare(2012,2013);
		evolucion.compare(2012,2014);
		evolucion.compare(2012,2015);

		evolucion.compare(2013,2013);
		evolucion.compare(2013,2014);
		evolucion.compare(2013,2015);
		
		evolucion.compare(2014,2014);
		evolucion.compare(2014,2015);
		
		evolucion.compare(2015,2015);
		

		
	}
	
	public void compare(int ini,int end) throws Exception{
		if(ini!=end) return;
		
		Instances instances=extractor.extraer(ini);
		
		
	}

}
