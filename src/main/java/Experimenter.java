
/**
 * Created by loezerl-fworks on 31/07/17.
 */

import classifiers.Classifier;
import classifiers.KNN;
import evaluators.Evaluator;
import evaluators.Prequential;
import moa.streams.ArffFileStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class Experimenter {
    public static SparkConf conf;
    public static JavaSparkContext sc;

    public static void main(String[] args) throws Exception {

//        local[2] - seta o numero de threads
        conf = new SparkConf().setAppName("spark-knn").setMaster("local[4]").set("spark.cores.max", "4");
        sc = new JavaSparkContext(conf);
        sc.setLogLevel("ERROR");


        String DIABETES_DATABASE = "/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff";
        String KYOTO_DATABASE = "/home/loezerl-fworks/Downloads/kyoto.arff";

        ArffFileStream file = new ArffFileStream(DIABETES_DATABASE, -1);

        Classifier myClassifier = new KNN(7, 30, "euclidean", sc);
        Evaluator myEvaluator = new Prequential(myClassifier, file);
        myEvaluator.run();

        sc.stop();
    }
}
