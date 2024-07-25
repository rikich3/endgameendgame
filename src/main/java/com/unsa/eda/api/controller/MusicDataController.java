package com.unsa.eda.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unsa.eda.service.MusicDataService;
import com.unsa.eda.api.data.MusicData;

@RestController
public class MusicDataController {

  private MusicDataService mds;
  
  @Autowired
  public MusicDataController(MusicDataService mds) {
    this.mds = mds;
  }

  @GetMapping(value= "/music", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MusicData> getMusicData(@RequestParam(value = "id", defaultValue = "0") int id) {
    MusicData musicData = mds.getMusicData(id);
    if (musicData == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok().body(musicData);
}

}
