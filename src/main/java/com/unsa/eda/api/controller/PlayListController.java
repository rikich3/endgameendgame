package com.unsa.eda.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import com.unsa.eda.api.data.MusicData;
import com.unsa.eda.service.MusicDataService;
import com.unsa.eda.service.Reader.Music;
import com.unsa.eda.service.Reader.Reader;

//Estructuras usadas
import List.DoubleCircularLinkedList;
import List.MyArrayList;


@RestController
@RequestMapping("/playlist")
public class PlayListController {
  private DoubleCircularLinkedList<Music> lista = new DoubleCircularLinkedList<>();
  //delete this
  
  MusicDataService datos;

  @Autowired
  PlayListController(MusicDataService mds) {
    datos = mds;
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/add/{id}")
  public void addMusic(@PathVariable String id) {  
    lista.insert(datos.getMusicData(Integer.parseInt(id)).getCancion());
    //System.out.println(lista.print());
  }
  
  @GetMapping("/list")
  public MusicData[] listMusic(){
    int[] songs = lista.getList();
    int size = songs.length;
    MusicData[] playlist = new MusicData[size];
    for(int i = 0; i < size; i++){
      playlist[i] = datos.getMusicData(songs[i]);
    }
    return playlist;
  }
  
  @CrossOrigin(origins = "*")
  @DeleteMapping("/remove/{id}")
  public void removeMusic(@PathVariable String id) {
    lista.delete(datos.getMusicData(Integer.parseInt(id)).getCancion());  
    System.out.println("Música eliminada: " + id);
    //System.out.println(lista.print());
  }

  @CrossOrigin(origins = "*")
  @DeleteMapping("/move/{index}/{indexFinal}")
  public void moveMusic(@PathVariable String index, @PathVariable String indexFinal) {
    lista.move(index, indexFinal);
    //System.out.println(lista.print());
  }
}
