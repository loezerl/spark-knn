package evaluators;

/**
 * Created by loezerl-fworks on 14/08/17.
 */
import classifiers.Classifier;
import moa.streams.ArffFileStream;
import weka.core.Instances;


public class Evaluator{
    public Classifier mClassifier;
    public ArffFileStream data_source;

    public Evaluator(Classifier _classifier, ArffFileStream _data){
        mClassifier = _classifier;
        data_source = _data;
    }
    public void run(long milliseconds){}
}

