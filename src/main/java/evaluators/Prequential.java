package evaluators;

/**
 * Created by loezerl-fworks on 14/08/17.
 */
import classifiers.Classifier;
import moa.streams.ArffFileStream;
import com.yahoo.labs.samoa.instances.Instance;

import java.util.concurrent.TimeUnit;

public class Prequential extends Evaluator{
    private int confirm=0;
    private int miss=0;

    public Prequential(Classifier _classifier, ArffFileStream data){
        super(_classifier, data);
    }

    @Override
    public void run(long milliseconds){
        /**
         * Essa função é responsável por gerenciar a etapa de treino e teste do classificador.
         * Será responsável por:
         * - Passar as instancias para o test e em seguida o treino.
         * - Contabilizar os hits confirms e hit miss.
         * */

        long finishTime = System.currentTimeMillis() + milliseconds;
        while(data_source.hasMoreInstances() && (System.currentTimeMillis() <= finishTime)){

            Instance example = data_source.nextInstance().getData();
            //long startTime = System.nanoTime();
            if(mClassifier.test(example)){ confirm++; }
            else{miss++;}
            //long estimatedTime = System.nanoTime() - startTime;
            //System.err.println("\n\n===========\nTest time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime) + "\n============");
            mClassifier.train(example);

        }


        System.out.println("Total acertos: " + confirm + " Total erros: " + miss);
    }

    public int getConfirm(){return confirm;}
    public int getMiss(){return miss;}
}