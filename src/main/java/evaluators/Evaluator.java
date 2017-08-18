package evaluators;

/**
 * Created by loezerl-fworks on 14/08/17.
 */
import classifiers.Classifier;
import weka.core.Instances;


public class Evaluator{
    public Classifier mClassifier;
    public Instances data_source;

    public Evaluator(Classifier _classifier, Instances _data){
        mClassifier = _classifier;
        data_source = _data;
    }
    public void run(){}
}

