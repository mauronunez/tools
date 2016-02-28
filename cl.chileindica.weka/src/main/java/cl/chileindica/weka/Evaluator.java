package cl.chileindica.weka;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.NeuralNetwork;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.stemmers.SnowballStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Evaluator {
	
	
	public static void evaluate(Instances filtered, Instances testSet, Classifier classifier,String msg) throws Exception{
		
		Evaluation eTest1 = new Evaluation(filtered);
		eTest1.evaluateModel(classifier, testSet);

		System.out.println(msg);
		System.out.println(eTest1.toSummaryString());
		
	}
	
	
	public static Instances generateTestSet(Instances filtered,int year,Filter filter)throws Exception{
		Modelo m=new Modelo();
		Instances testSet2 = new Instances(filtered.stringFreeStructure());
		Instances testSet=m.filter(m.generate(year));
		for (int i = 0; i < testSet.numInstances(); i++) {
			filter.input(testSet.instance(i));
			Instance filteredInstance = filter.output();
			testSet2.add(filteredInstance);
		}
		testSet2.setClass(filtered.attribute("N_REGION"));
		return testSet2;
		
	}
	
	
	
	public static void main(String args[])throws Exception{
		
		NeuralNetwork classifier=(NeuralNetwork)weka.core.SerializationHelper.read("classifier.model");
		StringToWordVector filter=(StringToWordVector)weka.core.SerializationHelper.read("filter.model");
		
		Instances sample=(Instances)weka.core.SerializationHelper.read("instances.model");
		SnowballStemmer stemmer = new SnowballStemmer("spanish");
		
		filter.setStemmer(stemmer);
		
		filter.setInputFormat(sample);

		Instances filtered = Filter.useFilter(sample, filter);
		
		Modelo m=new Modelo();
		
		
		filtered.setClass(filtered.attribute("N_REGION"));
		
		
		Instances set2010=generateTestSet(filtered,2010,filter);

		Instances set2011=generateTestSet(filtered,2011,filter);

		Instances set2012=generateTestSet(filtered,2012,filter);

		Instances set2013=generateTestSet(filtered,2013,filter);

		Instances set2014=generateTestSet(filtered,2014,filter);
		
		Instances set2015=generateTestSet(filtered,2015,filter);
		

		evaluate(filtered,filtered, classifier,"Evaluación Set de Entrenamiento");
		evaluate(filtered,set2010, classifier,"Evaluación dataset 2010");
		evaluate(filtered,set2011, classifier,"Evaluación dataset 2011");
		evaluate(filtered,set2012, classifier,"Evaluación dataset 2012");
		evaluate(filtered,set2013, classifier,"Evaluación dataset 2013");
		evaluate(filtered,set2014, classifier,"Evaluación dataset 2014");
		evaluate(filtered,set2015, classifier,"Evaluación dataset 2015");
		
		
		
	}

}
