package com.unsa.eda.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.unsa.eda.api.data.MusicData;
import com.unsa.eda.api.data.VistasOrdenadasService;

import List.List;

@RestController
@RequestMapping("/vistas")
public class VistasController {
  private VistasOrdenadasService vistasOrdenadasService;
  
  @Autowired
    public VistasController(VistasOrdenadasService vos) {
        vistasOrdenadasService = vos;
    }
  
  @GetMapping("/popularidad/{batch}")
  public MusicData[] getBatchPopularMusic(@PathVariable int batch) {
    return vistasOrdenadasService.getBatchPopularMusic(batch);
  }
  @GetMapping("/duracion/{batch}")
  public MusicData[] getBatchDuracion(@PathVariable int batch) {
    return vistasOrdenadasService.getBatchDuracionMusic(batch);
  }           
  @GetMapping("/fecha/{batch}")
  public MusicData[] getBatchFechaMusic(@PathVariable int batch) {
    return vistasOrdenadasService.getBatchFechaMusic(batch);
  }
}
