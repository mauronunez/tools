package cl.chileindica.weka;

import java.io.File;
import java.lang.invoke.LambdaMetafactory;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.HNB;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.net.search.global.GeneticSearch;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.NeuralNetwork;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.LogitBoost;
import weka.classifiers.meta.RandomSubSpace;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.rules.PART;
import weka.classifiers.rules.Ridor;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.HoeffdingTree;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.J48graft;
import weka.classifiers.trees.LADTree;
import weka.classifiers.trees.LMT;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.REPTree;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stopwords.WordsFromFile;
import weka.core.tokenizers.NGramTokenizer;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Modelo {

	private int size;
	
	public Modelo(){
		this.size=10;
	}
	
	public Modelo(int size){
		this.size=size;
	}
	
	public static void main(String args[]) throws Exception {

		long start = System.currentTimeMillis();

		Modelo m = new Modelo();

		Instances trainSet = m.filter(m.generate(0));
		System.out.println("TrainSet generated:"
				+ (System.currentTimeMillis() - start) / 1000);
		Instances testSet = m.filter(m.generate(2014));
		System.out.println("TestSet generated:"
				+ (System.currentTimeMillis() - start) / 1000);

		Filter filter = m.createFilter();

		filter.setInputFormat(trainSet);

		Instances filtered = Filter.useFilter(trainSet, filter);

		System.out.println("TrainSet filtered:"
				+ (System.currentTimeMillis() - start) / 1000);

		Instances testSet2 = new Instances(filtered.stringFreeStructure());

		System.out.println(testSet2.numInstances());
		System.out.println(testSet2.numAttributes());

		System.out.println(filtered.numInstances());
		System.out.println(filtered.numAttributes());

		for (int i = 0; i < testSet.numInstances(); i++) {
			filter.input(testSet.instance(i));
			Instance filteredInstance = filter.output();
			testSet2.add(filteredInstance);
		}

		System.out.println("TestSet filtered:"
				+ (System.currentTimeMillis() - start) / 1000);

		System.out.println(testSet2.numInstances());
		System.out.println(testSet2.numAttributes());

		/*
		for (int i = 0; i < 50; i++) {
			System.out.println(filtered.attribute(i).name());
			System.out.println(testSet2.attribute(i).name());
		}
		 */
		filtered.setClass(filtered.attribute("N_REGION"));
		testSet2.setClass(filtered.attribute("N_REGION"));

		Classifier classifier2 = m.buildClassifierY(filtered);

		System.out.println("Classifier builded:"
				+ (System.currentTimeMillis() - start) / 1000);

		Evaluation eTest0 = new Evaluation(filtered);
		eTest0.evaluateModel(classifier2, filtered);
		System.out.println(eTest0.toSummaryString());

		Evaluation eTest1 = new Evaluation(filtered);
		eTest1.evaluateModel(classifier2, testSet2);
		System.out.println(eTest1.toSummaryString());

		Classifier classifier = m.buildClassifierX(filtered);

		System.out.println("Classifier builded:"
				+ (System.currentTimeMillis() - start) / 1000);

		Evaluation eTest = new Evaluation(filtered);

		eTest.evaluateModel(classifier, filtered);

		System.out.println(eTest.toSummaryString());

		Evaluation eTest2 = new Evaluation(filtered);
		eTest2.evaluateModel(classifier, testSet2);

		System.out.println(eTest2.toSummaryString());
		
	}

	private Instances filter2(Instances generate) {
		// TODO Auto-generated method stub
		return generate;
	}

	public Classifier buildClassifierX(Instances filtered) throws Exception {

		//Classifier j48 = new J48();
		// MultiBoostAB model=new MultiBoostAB();
		//AdaBoostM1 model = new AdaBoostM1();

		SMO x = new SMO();
/*
		Bagging model=new Bagging();
		model.setDebug(true);
		model.setNumIterations(20);
		model.setBagSizePercent(20);
*/
		//LogitBoost model=new LogitBoost();
		
		//RandomSubSpace model=new RandomSubSpace();
		//MultilayerPerceptron model=new MultilayerPerceptron();
		//model.setDebug(true);
		
		//M5P model=new M5P();
		
		/*
		HoeffdingTree model=new HoeffdingTree();
		SelectedTag cri=new SelectedTag(1, HoeffdingTree.TAGS_SELECTION);
		model.setSplitCriterion(cri);
		*/
		/*
		AdaBoostM1 model=new AdaBoostM1();
		//model.setClassifier(new HoeffdingTree());
		J48graft j48=new J48graft();
		j48.setDebug(true);
		j48.setUnpruned(true);
		//j48.setBinarySplits(v);
		model.setClassifier(j48);
		//model.setUseResampling(true);
		model.setWeightThreshold(5);
		model.setNumIterations(10);
		
		model.setDebug(true);
		
		*/
		
		//Ridor model=new Ridor();
		//LADTree model=new LADTree();
		//PART model=new PART();
		
		NeuralNetwork model=new NeuralNetwork();
		
		
		//model.setHiddenLayers("20-5-5-2-2,100-5-5-2-2");
		
		model.setHiddenLayers("1000");
		
		//model.setLearningRate(0.2);
		model.setMaxIterations(1000);
		
		
		//LMT model=new LMT();
		//JRip model=new JRip();
		//model.setSubSpaceSize(20);
		
		//BayesNet c=new BayesNet();
		
		//c.setSearchAlgorithm(new GeneticSearch());
		
		//model.setClassifier(new REPTree());
		
		
		//model.setClassifier(x);
		// J48graft model=new J48graft();
		model.buildClassifier(filtered);

		return model;
	}

	public Classifier buildClassifierY(Instances filtered) throws Exception {

		ZeroR model = new ZeroR();

		// J48graft model=new J48graft();
		model.buildClassifier(filtered);
		return model;
	}

	public Filter createFilter() throws Exception {
		StringToWordVector sw = new StringToWordVector();
		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMinSize(2);
		tokenizer.setNGramMaxSize(3);
		tokenizer.setDelimiters("\\W");

		sw.setTokenizer(tokenizer);
		sw.setWordsToKeep(1500);
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

	public Instances generate(int ano) throws Exception {
		InstanceQuery query = new InstanceQuery();
		// query.setDatabaseURL("jdbc:mysql://192.168.3.223:3306/arica");
		query.setDatabaseURL("jdbc:mysql://127.0.0.1:3306/chileindica");

		query.setUsername("root");
		query.setPassword("subdere");

		String limit = String.valueOf(this.size);
		
		
		String sql0 = "(select I.N_REGION,I.C_INSTITUCION=1 GORE,I.N_SECTOR,I.NOMBRE,I.DESCRIPCION,I.BENEFICIARIOS,I.SOLICITADO from INVERSION I";
		String sql = sql0 + " where ANO=" + ano + " AND I.REGION=1  LIMIT "
				+ limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=2  LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=3  LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=4  LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=5  LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=6  LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=7  LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=8  LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=9  LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=10 LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=11 LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=12 LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=13 LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=14 LIMIT " + limit + ") ";
		sql += " UNION " + sql0 + " where ANO=" + ano
				+ " AND I.REGION=15 LIMIT " + limit + ") ";

		System.out.println(sql);

		if (ano == 0) {
			limit = "5000";
			sql = sql0 + " where  I.REGION=1  LIMIT " + limit + ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=2  LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=3  LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=4  LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=5  LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=6  LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=7  LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=8  LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=9  LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=10 LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=11 LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=12 LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=13 LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=14 LIMIT " + limit
					+ ") ";
			sql += " UNION " + sql0 + " where ANO> 2011 AND I.REGION=15 LIMIT " + limit
					+ ") ";
		}

		query.setQuery(sql);
		Instances source = query.retrieveInstances();
		return source;
	}

	public Instances filter(Instances source) throws Exception {

		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMinSize(1);
		tokenizer.setNGramMaxSize(3);
		tokenizer.setDelimiters("\\W");

		NominalToString ns = new NominalToString();

		ns.setAttributeIndexes("4-7");

		ns.setInputFormat(source);

		Instances data = Filter.useFilter(source, ns);

		return data;

	}

}
