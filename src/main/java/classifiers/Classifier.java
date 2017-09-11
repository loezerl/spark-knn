package classifiers;

import org.apache.spark.api.java.JavaSparkContext;
import com.yahoo.labs.samoa.instances.Instance;

/**
 * Created by loezerl-fworks on 14/08/17.
 */
public class Classifier {
    JavaSparkContext sc;
    public Classifier(JavaSparkContext scsc){
        sc = scsc;
    }
    public boolean test(Instance example){return true;}
    public void train(Instance example){}
}
