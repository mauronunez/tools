package cl.chileindica.analiza.json;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import consultabip.GetCodigoBipDocument;
import cl.gob.ministeriodesarrollosocial.bip.ConsultaProyectoBipInterfazServiceStub;


public class JsonImporter {
	
	public static void main(String myHelpers[]) throws Exception{
	     JSONTokener tokener=new JSONTokener(new FileInputStream("arica.json"));
	     
	     JSONArray array=new JSONArray(tokener);
	     	     
	     ConsultaProyectoBipInterfazServiceStub stub=new ConsultaProyectoBipInterfazServiceStub();
	     GetCodigoBipDocument arg0=GetCodigoBipDocument.Factory.newInstance();
	     
		stub.getCodigoBip(arg0);
	     
	     
	     File file=new File("fromJSON.csv");
	     String csv = CDL.toString(array);
	     FileUtils.writeStringToFile(file, csv);
	  }

}
