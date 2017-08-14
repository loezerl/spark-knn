package classifiers;

/**
 * Created by loezerl-fworks on 14/08/17.
 */

import java.util.*;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import util.Similarity;
import weka.core.NormalizableDistance;
import weka.core.Instances;
//import util.Pair;

public class KNN extends Classifier{
    private int K;
    private int WindowSize;
    private String DistanceFunction;
    private List<Instance> Window;

    public KNN(int kdistance, int wsize, String function){
        K = kdistance;
        WindowSize = wsize;
        if(function == "euclidean"){
            DistanceFunction = "euclidean";
        }
        else{
            System.out.println("Distancias disponiveis: euclidean");
            System.exit(1);
        }
        Window = new ArrayList<>(wsize);
    }

    // Aqui vai receber o dado sem label, com label e a predicao do classificador e ira retornar acerto (true) erro (false)
    public boolean test(Instance instance) {
        /**
         * Essa função vai receber uma instancia.
         * O passo inicial é calcular a distancia entre a instancia do parametro e as instancias presentes na janela.
         * Após calcular a distancia entre os pontos, é necessário selecionar as K instancias mais próximas.
         * Com um vetor com as K instancias mais próximas, opta-se por realizar um voto majoritário entre as classes de cada instancia.
         * Assim, verifica-se se a classe mais votada é igual a classe da instancia parametro, retornando True ou False.
         *
         * Notas:
         * - Tomar cuidado caso a janela esteja vazia e não haja Instancias para comparar, contabilizando um erro.
         * - Deixar o código mais flexível possível em relação aos tamanhos das bases.
         * - É muito provavel que as estruturas auxiliares daqui sejam exclusivas de cada framework.
         *
         * **/


        //Aqui eu devo iterar a janela e calcular a distancia pra cada ponto, em seguida pegar os K vizinhos mais proximos e realizar um voto majoritario.
//        List<Pair> Neighbors = new Vector<>();

//        Pair[] Neighbors = new Pair[Window.size()];
//        //Neighbors[0] = new Pair(10, 10.0);
//
//        for (int i=0; i< Window.size(); i++){
//            double dist = Similarity.EuclideanDistance(instance, Window.get(i));
//            Pair pair_ = new Pair(i, dist);
////            Neighbors.add(pair_);
//            Neighbors[i] = pair_;
//        }
//        List<Integer> KNeighbors = new Vector<>(K);
//
//        // Ordena de forma decrescente
//        Arrays.sort(Neighbors, (Pair b1, Pair b2) -> {
//            if (b1.getValue() > b1.getValue()) return -1;
//            if (b1.getValue() < b2.getValue()) return 1;
//            return 0;
//        });



        //Percorre o vetor e retira as K menores distancias
//        for(int i =0; i< Neighbors.size; i++){
//
//            System.exit(1);
//        }
        //Realiza o voto majoritario entre os K vizinhos
        return false;
    }

    public void train(Instance data) {
        /**
         * Atente-se aqui em relação a exclusão mútua.
         * É provavel que as estruturas de array dos frameworks possuam mutex interno, mas é necessário verificar isso em cada framework.
         * */

        System.out.println(Window.size());
        if (Window.size() == 0) {
            Window.add(data);
        }
        else{
            Window.remove(0);
            Window.add(data);
        }
        System.out.println(Window.size());
    }
}
