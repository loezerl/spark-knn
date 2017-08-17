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
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


import java.util.regex.Pattern;

public class Experimenter {
    private static final Pattern SPACE = Pattern.compile("\n");
    public static SparkConf conf;
    public static JavaSparkContext sc;
    public static void main(String[] args) throws Exception {

        //        if (args.length < 1) {
//            System.err.println("Usage: JavaWordCount <file>");
//            //System.exit(1);
//        }

//        SparkSession spark = SparkSession
//                .builder()
//                .appName("JavaWordCount")
//                .config("spark.master", "local")
//                .getOrCreate();

        //local[2] - seta o numero de threads
        conf = new SparkConf().setAppName("spark-knn").setMaster("local[2]").set("spark.cores.max", "4");
        sc = new JavaSparkContext(conf);


        ///////////////////////////////////////// WEKA ARFF LOADER
        DataSource source = new DataSource("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }
        ////////////////////////

        Classifier myClassifier = new KNN(7, 30, "euclidean", sc);

        Evaluator myEvaluator = new Prequential(myClassifier, data);

        sc.stop();
    }

    private boolean parseParameters(){
        return true;
    }

    public void run(){

    }
}
