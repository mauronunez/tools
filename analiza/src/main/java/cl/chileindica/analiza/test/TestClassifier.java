package cl.chileindica.analiza.test;

import meka.classifiers.multilabel.MultilabelClassifier;
import weka.core.*;

public class TestClassifier extends MultilabelClassifier {
	public void buildClassifier(Instances D) throws Exception {
		testCapabilities(D);
		int C = D.classIndex();
	}

	public double[] distributionForInstance(Instance x) throws Exception {
		int C = x.classIndex();
		return new double[C];
	}

	public static void main(String args[]) {
		MultilabelClassifier.runClassifier(new TestClassifier(), args);
	}
}