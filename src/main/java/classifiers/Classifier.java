package classifiers;

import scala.tools.nsc.transform.patmat.Interface;

/**
 * Created by loezerl-fworks on 14/08/17.
 */
public interface Classifier {
    // Aqui vai receber o dado sem label, com label e a predicao do classificador e ira retornar acerto (true) erro (false)
    static boolean test(){return true;}
    static void train(){}
}
