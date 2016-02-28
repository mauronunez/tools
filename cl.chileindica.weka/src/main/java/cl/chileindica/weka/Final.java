package cl.chileindica.weka;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.NeuralNetwork;
import weka.core.Instances;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stopwords.WordsFromFile;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Final {

	
	
	
	public static void main(String[] args) throws Exception {

		
		Class.forName("com.mysql.jdbc.Driver");
		
		
		Final f=new Final();
		Modelo m = new Modelo(10);
		// generateDistributionOfSampleBySector
		
		Instances sample=m.filter(m.generate(0));
		
		
		weka.core.SerializationHelper.write("instances.model", sample);
		
		//Instances sample=f.generateSample();
		Instances test=m.filter(m.generate(2014));
		
		StringToWordVector filter=(StringToWordVector)f.buildFilter();
		
		filter.setInputFormat(sample);

		Instances filtered = Filter.useFilter(sample, filter);
		
		filter.setStemmer(null);
		
		weka.core.SerializationHelper.write("filter.model", filter);
		
		filtered.setClass(filtered.attribute("N_REGION"));
		
		
		Classifier classifier=f.buildModel(filtered,50);

		Evaluation eTest0 = new Evaluation(filtered);
		eTest0.evaluateModel(classifier, filtered);
		System.out.println(eTest0.toSummaryString());
		
		m.generate(2010);
		m.generate(2011);
		m.generate(2012);
		m.generate(2013);
		m.generate(2014);
		m.generate(2015);
	}

	
	public Classifier buildModel(Instances instances, int iterations) throws Exception{
		NeuralNetwork model=new NeuralNetwork();
		
		Classifier classifier=model;
		model.setHiddenLayers("1000");
		
		
		//model.setLearningRate(0.2);
		model.setMaxIterations(iterations);
		
		model.buildClassifier(instances);

		weka.core.SerializationHelper.write("classifier.model", classifier);
		
		return classifier;
	}
	
	public Filter buildFilter(){
		StringToWordVector sw = new StringToWordVector();
		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMinSize(2);
		tokenizer.setNGramMaxSize(3);
		tokenizer.setDelimiters("\\W");

		sw.setTokenizer(tokenizer);
		sw.setWordsToKeep(500);
		sw.setLowerCaseTokens(true);
		sw.setDoNotOperateOnPerClassBasis(true);

		sw.setTFTransform(true);
		sw.setIDFTransform(true);
		WordsFromFile value = new WordsFromFile();
		value.setStopwords(new File("/home/mnunez/workspace/jee/indica/cl.chileindica.weka/stopwords.txt"));
		sw.setStopwordsHandler(value);

		SnowballStemmer stemmer = new SnowballStemmer("spanish");

		sw.setStemmer(stemmer);
		return sw;
	}
	
	public Instances generateSample(){
		return null;
	}
	
	public Instances generateTestSet(){
		return null;
	}
	
	public void generateYear(int year){
		
	}
	
}
