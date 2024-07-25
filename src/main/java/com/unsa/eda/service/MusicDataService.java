package com.unsa.eda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;

//import com.fasterxml.jackson.databind.JsonNode;
import com.unsa.eda.api.data.MusicData;
import com.unsa.eda.service.Reader.*;

import List.MyArrayList;


@Service
public class MusicDataService {
//------------------
  private final MyArrayList<MusicData> canciones; // Arreglo
   // Arreglo de urls hacia las imagenes
//------------------
  private final WebClient client = WebClient.create("https://api.spotify.com");
  private final String token;
  
  @Autowired
  public MusicDataService(Reader r) {
    Reader reader = r;
    MyArrayList<Music> musics = reader.getData();
    String genericAlbumImage= "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flipsnack.com%2Fes%2Ftemplates%2Fcovers%2Falbum&psig=AOvVaw0Zl4oWw8M-biroSG26nlwf&ust=1722009288737000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCLjKmtPGwocDFQAAAAAdAAAAABAE";
    String formatToken = client.post().uri("https://accounts.spotify.com/api/token")
    .bodyValue("grant_type=client_credentials&client_id=8f6fcb6c81684d88b6f479c7f77dbe21&client_secret=91254e810a394a9bbc173e3f1a1e8d36")
    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
    .retrieve().bodyToMono(String.class).block()
    .split(",")[0].split(":")[1];
    token = formatToken.substring(1, formatToken.length()-1);
    System.out.println("logged succesfully, token: " + token);
    canciones = new MyArrayList<>();
    for(int i = 0; i < musics.size(); i++){
      canciones.add(new MusicData(musics.get(i), genericAlbumImage));
    }
  }

  public MusicData getMusicData(int i){
    ObjectMapper objectMapper = new ObjectMapper();
    if(canciones.get(i).getImgURL() == null){
      String urlEndpoint= "/v1/tracks/" + canciones.get(i).getCancion().getTrackId();
      System.out.println("--------------------");
      System.out.println("URL: " + urlEndpoint);
      System.out.println("--------------------");
      String imgURL = client.get().uri("/v1/tracks/" + canciones.get(i).getCancion().getTrackId())
      .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
      .retrieve()
      .bodyToMono(String.class)
      .block();
      
      try {
        JsonNode root = objectMapper.readTree(imgURL);
        JsonNode albumNode = root.path("album");
        JsonNode imagesNode = albumNode.path("images");
        if (imagesNode.isArray() && imagesNode.size() > 0) {
            imgURL = imagesNode.get(0).path("url").asText(); // Get the first image URL
        }

      }catch (Exception e) {
        imgURL = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fillustrations%2Fmusic-album-cover&psig=AOvVaw02WoISmfUh7B9Ew1EpvL9e&ust=1721476318482000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCODEipiFs4cDFQAAAAAdAAAAABAE";
        System.out.println("--------------------");
        System.out.println();
        e.printStackTrace();
      }
    
      canciones.get(i).setImageUrl(imgURL);
      // .split(",")[0].split(":")[1];
      // imagesURLS[i] = imgURL.substring(1, imgURL.length()-1);
    }
    
    return canciones.get(i);
  }
}
