package classifiers;

/**
 * Created by loezerl-fworks on 14/08/17.
 */

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import util.Similarity;
import com.yahoo.labs.samoa.instances.Instance;

import java.util.*;

public class KNN extends Classifier{
    private int K;
    private int WindowSize;
    private String DistanceFunction;
    private List<Instance> Window;

    public KNN(int kneighbours, int wsize, String function, JavaSparkContext sc_){
        super(sc_);
        K = kneighbours;
        WindowSize = wsize;
        if(function.equals("euclidean")){
            DistanceFunction = "euclidean";
        }
        else{
            System.err.println("Distancias disponiveis: euclidean");
            System.exit(1);
        }
        Window = new ArrayList<>(wsize);
    }

    @Override
    public boolean test(Instance instance) {
        /**
         * Essa função vai receber uma instancia.
         * O passo inicial é calcular a distancia entre a instancia do parametro e as instancias presentes na janela.
         * Após calcular a distancia entre os pontos, é necessário selecionar as K instancias mais próximas.
         * Com um vetor com as K instancias mais próximas, opta-se por realizar um voto majoritário entre as classes de cada instancia.
         * Assim, verifica-se se a classe mais votada é igual a classe da instancia parametro, retornando True ou False.
         * **/


        //Cria uma estrutura JavaRDD utilizando a List<Instance> Window
        JavaRDD<Instance> W_instances = sc.parallelize(Window);

        //Calcula a distancia entre a instance e as instancias presentes na janela
        JavaPairRDD<Instance, Double> distances = W_instances.mapToPair(s -> new Tuple2<>(s, Similarity.EuclideanDistance(instance, s)));

        //Ordena as instancias
        distances = distances.mapToPair(s -> s.swap()).sortByKey().mapToPair(x -> x.swap());

        //Pega os K vizinhos mais próximos
        List<Tuple2<Instance, Double>> K_neighbours = distances.take(K);



        Map majorvote = new HashMap<Double, Integer>();

        for (Tuple2<Instance, Double> tuple : K_neighbours){
            if(majorvote.containsKey(tuple._1().classValue())){
                Integer aux = (Integer)majorvote.get(tuple._1().classValue());
                majorvote.put(tuple._1().classValue(), aux + 1);
            }else{
                majorvote.put(tuple._1().classValue(), 1);
            }
        }

        Integer bestclass_vote = -600;
        Double bestclass_label = -600.0;

        Iterator<Map.Entry<Double, Integer>> it = majorvote.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Double, Integer> pair = it.next();
            if(pair.getValue() > bestclass_vote){
                bestclass_label = pair.getKey();
                bestclass_vote = pair.getValue();
            }
        }

        Double targetclass = instance.classValue();

        if(targetclass.equals(bestclass_label))
            return true;

        return false;
    }

    @Override
    public void train(Instance data) {
        /**
         * Atente-se aqui em relação a exclusão mútua.
         * É provavel que as estruturas de array dos frameworks possuam mutex interno, mas é necessário verificar isso em cada framework.
         * */
        if (Window.size() < WindowSize) {
            Window.add(data);
        }
        else{
            Window.remove(0);
            Window.add(data);
        }
    }
}
