## Spark-knn

Repositório responsável pela criação do KNN dentro do framework Spark.

### Notas:
- De alguma forma adaptar o `Instance` do WEKA para o `JavaRDD<T>` do Apache Spark.
- Dar uma olhada no código a seguir: https://stackoverflow.com/questions/41668340/convert-class-object-to-java-rdd
- Após realizar as duas etapas anteriores com sucesso, partir para a implementação das funções map.
- As funções map visam mapear toda a batch e criar uma estrutura `<Instance, Distance>`
