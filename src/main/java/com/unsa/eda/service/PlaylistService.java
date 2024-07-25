package com.unsa.eda.service;

import org.springframework.stereotype.Service;

import com.unsa.eda.service.Reader.Music;
import com.unsa.eda.service.Reader.Reader;

// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

import List.DoubleCircularLinkedList;
import List.MyArrayList;

@Service
public class PlaylistService {
    
    private DoubleCircularLinkedList<Music> lista = new DoubleCircularLinkedList<>();
    private Reader r = new Reader();
    private MyArrayList<Music> datos = r.getData();
  
   public String addMusic(String id) {
    lista.insert(datos.get(Integer.valueOf(id)));
    System.out.println("----------------------------");
    System.out.println("Música añadida: de reader " + datos.get(Integer.valueOf(id)));
    //System.out.println(lista.print());
    System.out.println("----------------------------");
    return "entro add service";

  }

  public String removeMusic(String id) {
    System.out.println("----------------------------");
    int eliminarid = Integer.valueOf(id);
    lista.delete(datos.get(eliminarid));
    System.out.println("Música eliminada: " + eliminarid);
    //System.out.println(lista.print());
    System.out.println("----------------------------");
    return "entro remove service";
  }
}