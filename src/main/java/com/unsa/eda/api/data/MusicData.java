package com.unsa.eda.api.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.unsa.eda.service.Reader.*;

import HashTable.*;
import List.DoubleCircularLinkedList;
import List.MyArrayList;
import TreeB.*;

//Podemos utilizar bytecode
//Crear el objeto arreglo de canciones
//Seg
public class MusicData {
  
  Music cancion;
  String imgURL;
  
  public MusicData(Music cancion, String imgURL) {
    this.cancion = cancion;
    this.imgURL = imgURL;
  }

  public Music getCancion() {
    return cancion;
  }

  public String getImgURL() {
    return imgURL;
  }

  public void setImageUrl(String imgURL2) {
    imgURL = imgURL2;
  }
  public String toString(){
    return "" + cancion.getTrackName() + ", " + cancion.getArtistName();
  }
}