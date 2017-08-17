/**
 * Created by loezerl-fworks on 14/08/17.
 */
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.codehaus.janino.Java;
import scala.Tuple2;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

//meu

import org.apache.spark.sql.Dataset;
import util.Similarity;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {
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
        conf = new SparkConf().setAppName("org.sparkexample.WordCount").setMaster("local[2]").set("spark.cores.max", "4");
        sc = new JavaSparkContext(conf);


        ///////////////////////////////////////// WEKA ARFF LOADER
        DataSource source = new DataSource("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }


        Instance auxI = data.get(10);
        auxI.classAttribute().numValues();
        System.out.println(auxI.classAttribute().numValues());
        ////////////////////////
        List<Instance> ListInst = new Vector<>();

        for (int i = 0; i < data.numInstances(); i++) {
            ListInst.add(data.get(i));
        }

        JavaRDD<Instance> dataset = sc.parallelize(ListInst);

        JavaPairRDD<Instance, Double> distances = dataset.mapToPair(s -> new Tuple2<>(s, Similarity.EuclideanDistance(auxI, s)));

        distances = distances.mapToPair(s -> s.swap()).sortByKey().mapToPair(x -> x.swap());

        int k = 7;

        List<Tuple2<Instance, Double>> k_neighbours = distances.take(k);

        //JavaPairRDD<Instance, Double> cc =

        List<Tuple2<Instance, Double>> output = distances.collect();

        List<Integer> major_vote = new ArrayList<Integer>(auxI.classAttribute().numValues());

        int[] major_vote2 = new int[auxI.classAttribute().numValues()];

//        for(Object obj : major_vote){
//            System.out.println(obj);
//        }


        for (Tuple2<Instance, Double> tuple : k_neighbours){
            int aux = (int)tuple._1().classValue();
            major_vote2[aux]++;
            System.out.println(tuple._1().classValue() + " : " + tuple._2());
        }

        int bestclass_d = -600;
        int bestclass = -600;

        for(int i=0; i< major_vote2.length; i++){

            if(major_vote2[i] > bestclass_d){
                bestclass = i;
                bestclass_d = major_vote2[i];
            }
            System.out.println(major_vote2[i]);
        }

        System.out.println("Class: " + bestclass);



        sc.stop();

//        JavaRDD<String> lines = spark.read().textFile("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff").javaRDD();
//
//        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());
//
//        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
//
//        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);
//
//        List<Tuple2<String, Integer>> output = counts.collect();
////        for (Tuple2<?,?> tuple : output) {
////            System.out.println(tuple._1() + ": " + tuple._2());
////        }
//        spark.stop();
    }

}
