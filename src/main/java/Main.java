/**
 * Created by loezerl-fworks on 14/08/17.
 */
import org.apache.spark.sql.Row;
import scala.Tuple2;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

//meu

import org.apache.spark.sql.Dataset;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {
    private static final Pattern SPACE = Pattern.compile("\n");
    public static void main(String[] args) throws Exception {

//        if (args.length < 1) {
//            System.err.println("Usage: JavaWordCount <file>");
//            //System.exit(1);
//        }

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaWordCount")
                .config("spark.master", "local")
                .getOrCreate();


        DataSource source = new DataSource("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff");

        Instances data = source.getDataSet();

        if(data.classIndex() == -1){
            data.setClassIndex(data.numAttributes() - 1);
        }

//        JavaRDD<Row> dataset_ = spark.read().csv("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff").javaRDD();
//
//        List<Row> output__ = dataset_.collect();
//
//        for (Row linha : output__){
//            System.out.println(linha.toString());
//        }

        JavaRDD<Instance> data__;

        

        JavaRDD<String> lines = spark.read().textFile("/home/loezerl-fworks/IdeaProjects/Experimenter/diabetes.arff").javaRDD();

        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));

        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?,?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        spark.stop();
    }

}
