package HashTable;

import java.util.Random;

import com.unsa.eda.service.Reader.Reader;

import List.MyArrayList;

public class Main {
  public static void main(String[] args) {
    MyOpenHashTable<String, Integer> table = new MyOpenHashTable<>();
    MyClosedHashTable<String, Integer> closedTable = new MyClosedHashTable<>();

    long startTime = System.nanoTime();
    poblarMapa(table, 2000);
    long endTime = System.nanoTime();
    System.out.println("Elapsed time for open table: " + (endTime - startTime) + " ns");
    startTime = System.nanoTime();
    poblarMapa(closedTable, 2000);
    endTime = System.nanoTime();
    System.out.println("Elapsed time for closed table: " + (endTime - startTime) + " ns");
  }
  protected static void poblarMapa(Dictionary<String, Integer> table, int size){
    Reader reader = new Reader();
    //Random random = new Random();
    //MyArrayList<String> list = new MyArrayList<String>();
    for(int i = 0; i < size; i++){
      String key = "";
      
      table.put(key, i);
    }
  }


    // table.put("a", 1);
    // table.put("b", 2);
    // table.put("c", 3);
    // table.put("arroz", 5);
    // System.out.println("the value stored in a is: " + table.get("a"));
    // System.out.println("the value stored in b is: " + table.get("b"));
    // System.out.println("the value stored in arroz is: " + table.get("arroz"));
    // table.remove("a");
    // if(table.get("a") == null)
    //   System.out.println("a was removed");
    // else
    //   System.out.println("a was not removed");
  
}
