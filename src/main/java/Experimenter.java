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
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.*;

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
//
//        local[2] - seta o numero de threads
        conf = new SparkConf().setAppName("spark-knn").setMaster("local[4]").set("spark.cores.max", "4");
        sc = new JavaSparkContext(conf);
        sc.setLogLevel("ERROR");

        //JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));


//        FileInputStream inputStream = null;
//        Scanner sc = null;
//        try {
//            inputStream = new FileInputStream("/home/loezerl-fworks/Downloads/kyoto.arff");
//            sc = new Scanner(inputStream, "UTF-8");
//            while (sc.hasNextLine()) {
//                String line = sc.nextLine();
//                System.out.println(line);
//            }
//            // note that Scanner suppresses exceptions
//            if (sc.ioException() != null) {
//                throw sc.ioException();
//            }
//        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//            if (sc != null) {
//                sc.close();
//            }
//        }

        ///////////////////////////////////////// WEKA ARFF LOADER
//        DataSource source = new DataSource("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff");
//        Instances data = source.getDataSet();
//        if (data.classIndex() == -1) {
//            data.setClassIndex(data.numAttributes() - 1);
//        }
        ////////////////////////

        ////////////////// WEKA ARFF LOAD 2
        BufferedReader reader =
                new BufferedReader(new FileReader("/home/loezerl-fworks/Downloads/kyoto.arff"));
        System.out.println("Reader criado");
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        System.out.println("Arff Reader Criado");
        Instances data = arff.getData();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }
        ///////////////////////////////////////////////

        Classifier myClassifier = new KNN(7, 30, "euclidean", sc);
        Evaluator myEvaluator = new Prequential(myClassifier, data);
        myEvaluator.run();

        sc.stop();
    }

    private boolean parseParameters(){
        return true;
    }
}
