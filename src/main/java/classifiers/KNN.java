package classifiers;

/**
 * Created by loezerl-fworks on 14/08/17.
 */

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import scala.Array;
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

        if(Window.size() <= 0){return false;}

        //Cria uma estrutura JavaRDD utilizando a List<Instance> Window
        JavaRDD<Instance> W_instances = sc.parallelize(Window);

        //Calcula a distancia entre a instance e as instancias presentes na janela
        JavaPairRDD<Instance, Double> distances = W_instances.mapToPair(s -> new Tuple2<>(s, Similarity.EuclideanDistance(instance, s)));

        //Transforma o JavaRDD em uma lista de tuplas
        List<Tuple2<Instance, Double>> distances_list = distances.collect();

        List<Tuple2<Instance, Double>> K_neighbours = new ArrayList<>(K);

        //Insere os K vizinhos numa lista
        int i = 0;
        int index=0;
        for (Tuple2<Instance, Double> tuple : distances_list){
            if(i < K){
                i++;
                K_neighbours.add(tuple);
            }
            else{
                index=0;
                for(Tuple2<Instance, Double> tuple_k : K_neighbours){
                    if(tuple._2() < tuple_k._2()){
                        K_neighbours.remove(index);
                        K_neighbours.add(index, tuple);
                        break;
                    }
                    index++;
                }
            }
        }

        //Calculo do voto majoritario
        Map majorvote = new HashMap<Double, Integer>(instance.numClasses());

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
    public synchronized void train(Instance data) {
        if (Window.size() < WindowSize) {
            Window.add(data);
        }
        else{
            Window.remove(0);
            Window.add(data);
        }
    }
}
