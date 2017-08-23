## Spark-knn

Repositório responsável pela criação do KNN dentro do framework Spark.

### Notas:
- Prequential: Popular a Matriz de Confusão.
- Experimenter: Parser dos parametros.
- Descrever as funcionalidades de cada função.
- Ver como carrega um arff gigante com as bibliotecas do MOA.

Então, vou deixar a parte de implementar e modularizar cada experimenter(em cada framework) para a segunda iteração, principalmente tratar os arquivos grandes.


Mudar o arffloader para ArffFileStream do MOA! Adaptar o código!

**Como carregar os arquivos arff grandes:**

```

ArffFileStream filess = new ArffFileStream("/home/loezerl-fworks/Downloads/kyoto.arff", 8);
//
        InstanceExample olar = filess.nextInstance();

        while(filess.hasMoreInstances()){
            olar = filess.nextInstance();
            System.out.println(olar.getData());
        }
        
```