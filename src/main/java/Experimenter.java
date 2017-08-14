/**
 * Created by loezerl-fworks on 14/08/17.
 */
/**
 * Created by loezerl-fworks on 31/07/17.
 */
import classifiers.Classifier;
import classifiers.KNN;
import evaluators.Evaluator;
import evaluators.Prequential;
import weka.core.EuclideanDistance;
import weka.core.NormalizableDistance;
import util.Similarity;

// import weka.core.converters.ArffLoader;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Experimenter {

    public static void main(String[] args) throws Exception {

        DataSource source = new DataSource("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff");
        Instances data = source.getDataSet();

        if(data.classIndex() == -1){
            data.setClassIndex(data.numAttributes() - 1);
        }

        KNN myClassifier = new KNN(3, 4, "euclidean");
        myClassifier.train(data.get(0));

//        Evaluator myEvaluator = new Prequential(myClassifier, data);
        System.out.println(data.size());
        System.out.println(data.get(0).value(8));
        //data.get(index).classValue() - Retorna um numero para classe

    }

    private boolean parseParameters(){
        return true;
    }

    public void run(){

    }
}
