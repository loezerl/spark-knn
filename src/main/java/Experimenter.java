
/**
 * Created by loezerl-fworks on 31/07/17.
 */

import classifiers.Classifier;
import classifiers.KNN;
import com.yahoo.labs.samoa.instances.Instance;
import evaluators.Evaluator;
import evaluators.Prequential;
import moa.streams.ArffFileStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.concurrent.TimeUnit;

public class Experimenter {
    public static SparkConf conf;
    public static JavaSparkContext sc;

    public static void main(String[] args) throws Exception {

        //        local[2] - seta o numero de threads
        conf = new SparkConf().setAppName("spark-knn").setMaster("local[16]").set("spark.cores.max", "8");
        sc = new JavaSparkContext(conf);
        sc.setLogLevel("ERROR");

        String DIABETES_DATABASE = "/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff";
        String KYOTO_DATABASE = "/home/loezerl-fworks/Downloads/kyoto.arff";

        ArffFileStream file = new ArffFileStream(KYOTO_DATABASE, -1);

        Classifier myClassifier = new KNN(7, 1000, "euclidean", sc);
        Prequential myEvaluator = new Prequential(myClassifier, file);

        long startTime=0;
        long estimatedTime=0;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("Running shutdownhook..");
                    System.err.println("Confirms: " + myEvaluator.getConfirm());
                    System.err.println("Miss: " + myEvaluator.getMiss());
                    System.err.println("Instances: " + (myEvaluator.getMiss() + myEvaluator.getConfirm()));
                    sc.stop();
                }catch (Exception e){}
            }
        });

        startTime = System.nanoTime();
        myEvaluator.run();
        estimatedTime = System.nanoTime() - startTime;
        System.err.println("\n=====\nTempo rodando o programa: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime) + "\n");

        sc.stop();
    }
}
