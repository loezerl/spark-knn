package evaluators;

/**
 * Created by loezerl-fworks on 14/08/17.
 */
import classifiers.Classifier;
import weka.core.Instances;


public class Prequential extends Evaluator{
    private int confirm=0;
    private int miss=0;
    private int[][] confusion_matrix;

    public Prequential(Classifier _classifier, Instances _data){
        super(_classifier, _data);
    }
    public void run(){
        /**
         * Essa função é responsável por gerenciar a etapa de treino e teste do classificador.
         * Será responsável por:
         * - Passar as instancias para o test e em seguida o treino.
         * - Contabilizar os hits confirms e hit miss.
         * - Popular a Confusion Matrix
         * - Imprimir em real time os resultados obtidos, aka acuracia.
         * - Imprimir um relatório log final com os resultados, aka acuracia, calculos de erro (kappa), etc..
         *
         * */
    }
}