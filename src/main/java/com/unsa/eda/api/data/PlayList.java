package com.unsa.eda.api.data;
import List.DoubleCircularLinkedList;
import List.Node;

import com.unsa.eda.service.Reader.Music;

import HashTable.MyClosedHashTable;

public class PlayList {
  MyClosedHashTable<String, Node<Music>> accesoRapido;
  DoubleCircularLinkedList<Music> cola;

  //
  public void addSong(){
    cola.insert(null);
  }
  
}
