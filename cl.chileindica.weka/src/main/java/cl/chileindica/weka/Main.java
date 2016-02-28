package cl.chileindica.weka;

import java.beans.DesignMode;
import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.C45Saver;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stemmers.Stemmer;
import weka.core.stopwords.WordsFromFile;
import weka.core.tokenizers.NGramTokenizer;
import weka.experiment.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Main {
	
	static Filter filter;
	
	public static Classifier buildClassifierX(Instances filtered) throws Exception {
		J48 model=new J48();
		
		FilteredClassifier fc=new FilteredClassifier();
		fc.setClassifier(model);
		fc.setFilter(filter);
		
		fc.buildClassifier(filtered);
		
		return fc;
	}

	public static Classifier buildClassifier(Instances filtered) throws Exception {
		J48 cModel = new J48();
		J48 j48graft=new J48(); 
		
		MultilayerPerceptron multilayer=new MultilayerPerceptron();
		
		DecisionTable decisionTable=new DecisionTable();
		
		JRip jRip=new JRip();
		
		decisionTable.setDisplayRules(true);
	
/*
		multilayer.buildClassifier(filtered);
		
		cModel.buildClassifier(filtered);
		
		decisionTable.buildClassifier(filtered);
*/		
		j48graft.buildClassifier(filtered);

		weka.core.SerializationHelper.write("inversion_j48.model", cModel);

		/*
		System.out.println(cModel.graph());

		System.out.println(multilayer.toString());
		
		System.out.println(decisionTable.toString());
		*/
		System.out.println(j48graft.graph());
		/*
		evaluate(cModel,filtered);
		evaluate(multilayer,filtered);
		evaluate(decisionTable,filtered);
		*/
		//evaluate(j48graft,filtered);
		
		return cModel;
	}
	
	
	private static void evaluate(Classifier cModel, Instances prior,Instances filtered) throws Exception {
		
		Evaluation eTest = new Evaluation(prior);
		
		eTest.evaluateModel(cModel, filtered);

		// Print the result à la Weka explorer:
		String strSummary = eTest.toSummaryString();
		System.out.println(strSummary);

		// Get the confusion matrix
		double[][] cmMatrix = eTest.confusionMatrix();
	}

	private static Instances generateInstances02() throws Exception {
		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMinSize(1);
		tokenizer.setNGramMaxSize(2);
		tokenizer.setDelimiters("\\W");

		InstanceQuery query = new InstanceQuery();
		query.setDatabaseURL("jdbc:mysql://127.0.0.1:3306/chileindica");

		query.setUsername("root");
		query.setPassword("subdere");
		query.setQuery("select I.N_REGION,I.N_SECTOR,I.SOLICITADO from INVERSION I WHERE C_INSTITUCION=1 LIMIT 1500");

		Instances source = query.retrieveInstances();
		source.setClass(source.attribute("N_REGION"));

		/*
		 * ArffSaver save = new ArffSaver(); save.setInstances(filtered);
		 * save.setFile(new File("output.arff")); save.writeBatch();
		 * 
		 * C45Saver saver = new C45Saver(); saver.setInstances(filtered);
		 * saver.setFile(new File("instances.names")); saver.writeBatch();
		 * 
		 * 
		 * System.out.println(filtered);
		 */
		return source;
	}

	public static Instances generateInstances(int ano, Instances input) throws Exception {

		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMinSize(1);
		tokenizer.setNGramMaxSize(2);
		tokenizer.setDelimiters("\\W");

		InstanceQuery query = new InstanceQuery();
		// query.setDatabaseURL("jdbc:mysql://192.168.3.223:3306/arica");
		query.setDatabaseURL("jdbc:mysql://127.0.0.1:3306/chileindica");
		query.setUsername("root");
		query.setPassword("subdere");
		
		String sql="select I.N_REGION,I.C_INSTITUCION=1 GORE,I.N_SECTOR,I.NOMBRE,I.DESCRIPCION,I.BENEFICIARIOS,I.SOLICITADO ";
		sql+="from INVERSION I where ANO=" +ano +" LIMIT 1500";
		
		if(ano==0){
			sql="select I.N_REGION,I.C_INSTITUCION=1 GORE,I.N_SECTOR,I.NOMBRE,I.DESCRIPCION,I.BENEFICIARIOS,I.SOLICITADO ";
			sql+="from INVERSION I WHERE C_INSTITUCION=1 LIMIT 10000";
		}
		
		
		query.setQuery(sql);

		Instances source = query.retrieveInstances();
		NominalToString ns = new NominalToString();

		ns.setAttributeIndexes("4-7");
		ns.setInputFormat(source);

		Instances data = Filter.useFilter(source, ns);
		
		

		StringToWordVector sw = new StringToWordVector();
		sw.setTokenizer(tokenizer);
		sw.setWordsToKeep(100);
		sw.setLowerCaseTokens(true);
		sw.setDoNotOperateOnPerClassBasis(true);
		if(input!=null){
		sw.setInputFormat(input);
		}else{
			sw.setInputFormat(data);
		}
		sw.setInputFormat(data);
		
		sw.setIDFTransform(true);
		WordsFromFile value = new WordsFromFile();
		value.setStopwords(new File("stopwords.txt"));
		sw.setStopwordsHandler(value);

		SnowballStemmer stemmer = new SnowballStemmer("spanish");

		sw.setStemmer(stemmer);
		//sw.setInputFormat(data);

		Instances filtered = Filter.useFilter(data, sw);

		filtered.setClass(filtered.attribute("N_REGION"));

		/*
		 * ArffSaver save = new ArffSaver(); save.setInstances(filtered);
		 * save.setFile(new File("output.arff")); save.writeBatch();
		 * 
		 * C45Saver saver = new C45Saver(); saver.setInstances(filtered);
		 * saver.setFile(new File("instances.names")); saver.writeBatch();
		 * 
		 * 
		 * System.out.println(filtered);
		 */
		return filtered;
	}

	public static void main(String args[]) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");

		Instances instances0 = generateInstances(2010,null);
		Instances instances1 = generateInstances(2011,null );
		Instances instances2 = generateInstances(2012,null);
		Instances instances3 = generateInstances(2013,null);
		Instances instances4 = generateInstances(2014,null);
		Instances instances5 = generateInstances(2015,null);
		
		Classifier model0=buildClassifierX(instances0);
		/*
		Classifier model1=buildClassifierX(instances1);
		Classifier model2=buildClassifierX(instances2);
		Classifier model3=buildClassifierX(instances3);
		Classifier model4=buildClassifierX(instances4);
		Classifier model5=buildClassifierX(instances5);
		*/
		evaluate(model0,instances0,instances0);
		evaluate(model0,instances0,instances1);
		evaluate(model0,instances0,instances2);
		evaluate(model0,instances0,instances3);
		evaluate(model0,instances0,instances4);
		evaluate(model0,instances0,instances5);

		
	}

}
