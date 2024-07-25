## 7/01 Primeras implementaciones de un HashTable

El estudiante Ricardo esta implementando un hash table desde cero utilizando solo los conceptos que averiguo por libros e internet.

Para el primer commit voy a implementar 1 de 3 implementaciones de HashTables. una vez que esten los 3 implementados voy a compararlos y voy a mejorar mi funcion hash para los tipos de datos reales que va a contener.

Todas estas implementaran la interfaz Dictinary. Con los 3 metodos más comunes que exponen[1].

### MyClosedHashTable

HashTable que maneja las collisiones colocando los valores en una Lista que se encuentra en cada indice generado por la funcion hash. La funcion Hash que implementare es personal, no estare usando el método hashCode() que proporciona java, apesar de que no forme parte del modulo Collection, ya que, de lo que eh averiguado, este es el método que usan las Colecciones de java para realizar el hashing e indexacion[2]. Si no que voy a utilizar un método propio simplon.

Tenemos que tener en cuenta que queremos un mapeo por cada llave **diferente**, esto es un problema al encontrar el dato con la llave y al colocar un par llave, valor.

Imaginemos que 2 llaves diferentes nos dan la misma direccion, por lo que, en un mapa cerrado tenemos una lista[3] de tamaño mayor que 1, en este caso debemos buscar el valor que le corresponde a esta llave, esto lo podemos lograr guardando en cada nodo de la lista el objeto llave que lo ha generado y comprobando por igualdad con el metodo equals.

He realizado una Lista especial para guardar mis nodos que contienen llave y valor, esta implementa la interfaz iterable sobre sus nodos, al principio me parecio buena idea, sin embargo ahora me doy cuenta que las operaciones insertar y eliminar pueden estar completamente definidas en la clase Lista.

## MyOpenHashTable

Al momento de insertar, si se presentan colisiones vamos a buscar el siguiente indice desocupado que le sigue al indice ocupado, y colocaremos nuestro par hay, y al momento de buscar elemento vamos a comprobar si la llave es identica 

## Optimizacion y rehuso de código



## Referencias

[1] T. H. Cormen, C. E. Leiserson, R. L. Rivest, and C. Stein, Introduction to Algorithms, 3rd ed. Cambridge, MA: MIT Press, 2009, p. 272.
[2]
[3]"Java - Implementing Iterator and Iterable Interface," GeeksforGeeks. [Online]. Available: https://www.geeksforgeeks.org/java-implementing-iterator-and-iterable-interface/. [Accessed: Jul. 2, 2024].

[4] https://www.baeldung.com/java-measure-elapsed-time