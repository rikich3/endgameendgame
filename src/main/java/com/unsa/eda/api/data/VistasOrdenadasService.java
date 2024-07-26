package com.unsa.eda.api.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.stereotype.Service;
import com.unsa.eda.service.MusicDataService;
import List.MyArrayList;
import TreeB.*;
//import jakarta.el.ELException;
@Service
public class VistasOrdenadasService {
    private int loadedPopu;
    private int loadedDura;
    private int loadedFecha;
    private MusicDataService mds;
    private BPLus<Integer, MusicData> popularidad;
    private BPLus<Integer, MusicData> duracion;
    private BPLus<Integer, MusicData> fecha;
    private final int batchSize = 20;
    private final int dockerSize = 1000;  
    @Autowired
    VistasOrdenadasService(MusicDataService mds){
        this.mds = mds;
        duracion = new BPLus<>(9);
        fecha = new BPLus<>(9);
        popularidad = new BPLus<>(9);
        loadedDura = 0;
        loadedFecha = 0;
        loadedPopu = 0;
        
        for(int i = 0; i < dockerSize; i++){
          System.out.println("Insertando en popularidad: " + i);
          popularidad.insertar(-Integer.parseInt(mds.getMusicData(i).getCancion().getPopularity()), mds.getMusicData(i));
        }
        loadedPopu++;
        System.out.println("--------------------");
        popularidad.mostrarLista();
        System.out.println("--------------------");
        for(int i = 0; i < dockerSize; i++){
            System.out.println("Insertando en duracion: " + i);
            duracion.insertar(Integer.parseInt(mds.getMusicData(i).getCancion().getDurationMs()), mds.getMusicData(i));
        }
        loadedDura++;
        System.out.println("--------------------");
        duracion.mostrarLista();
        System.out.println("--------------------");
        for(int i = 0; i < dockerSize; i++){
            System.out.println("Insertando en fecha: " + i);
            fecha.insertar(Integer.parseInt(mds.getMusicData(i).getCancion().getYear()), mds.getMusicData(i));
        }
        loadedFecha++;
        System.out.println("--------------------");
        fecha.mostrarLista();
        System.out.println("--------------------");
    }
    public MusicData[] getBatchPopularMusic(int batch) {
        int floor = batch*batchSize;
        BPlusNode<Integer, MusicData> list = popularidad.getList();
        MusicData[] res = new MusicData[batchSize];
        //valuesBuffer es el conjunto de arreglos de MusicData que se encuentran en el nodo actual
        //empezando a debugear
        int nodosRecoridos = 0;
        int elementosSaltados = 0;
        MyArrayList<MusicData> valuesBuffer;
        try {
              valuesBuffer = list.getValues();
        }catch(Exception e){
            valuesBuffer = null;
        }
        //EL INDICE EN EL NODO ACTUAL, SOLO SE SETEARA CUANDO
        int innerIndex = 0;
        //SE ENCUENTRE LA REFERENCIA NUMERO FLOOR
        boolean flag = false;
        try{
            //avanzando la lista hasta el piso, si no se puede hay que leer mil elementos más
            for(; elementosSaltados < floor; list = list.getNext()){
                System.out.println("nodos recorridos: " + nodosRecoridos);
                System.out.println("elementos saltados " + elementosSaltados);
                valuesBuffer = list.getValues();
                //supongo que aca falla por que valuesBuffer es nulo
                if(floor - elementosSaltados < valuesBuffer.size()){
                    innerIndex = floor - elementosSaltados;
                    System.out.println("Encontramos el index interno " + innerIndex);
                    break;
                }
                elementosSaltados += valuesBuffer.size();
                System.out.println("Saltamos " + valuesBuffer.size() + " elementos");
                System.out.println("Avanzamos al siguiente nodo");
                nodosRecoridos++;
            }
            //hasta aca list ya esta en el nodo que le corresponde
            //e innerIndex ya se encuentra en el indice que le corresponde
        } catch(Exception e){
            //si hubo un error hay que cargar mil elementos más
            System.out.println("He fallado, intentando cargar mil elementos más");
            for(int i = 0; i < dockerSize; i++){
                int index = loadedFecha*dockerSize + i;
                System.out.println("Insertando elemento numero " + index);
                popularidad.insertar(Integer.parseInt(mds.getMusicData(index).getCancion().getYear()), mds.getMusicData(index));
            }
            loadedPopu++;
            System.out.println("Tenemos en el arbol " + loadedPopu*dockerSize + "elementos");
            flag = true;
        }//todo pudo haber cambiado por lo que si sucedio la insercion entonces toca hacer lo mismo
        //por que aca puede que valuesBuffer sea nulo?

        if(flag){
            System.out.println("He entrado aca por que hubo un error y vuelvo a recorrer la lista hasta estar en el nodo y posicion correcta");
            list = popularidad.getList();
            elementosSaltados = 0;
            nodosRecoridos = 0;
            for(; elementosSaltados < floor; list = list.getNext()){
                //por que hay error aca
                System.out.println("nodos recorridos: " + nodosRecoridos);
            System.out.println("elementos saltados " + elementosSaltados);
                valuesBuffer = list.getValues();
                if(floor - elementosSaltados < valuesBuffer.size()){
                    innerIndex = floor - elementosSaltados;
                    System.out.println("Encontramos el index interno " + innerIndex);
                    break;
                }
                elementosSaltados += valuesBuffer.size();
                System.out.println("Avanzamos al siguiente nodo");
                nodosRecoridos++;
            }
        }
        System.out.println("Hasta este punto elementosSaltados es " + elementosSaltados);
        System.out.println("nodos recorridos: " + nodosRecoridos);
        System.out.println("innerIndex es " + innerIndex);
        System.out.println("Hasta aca deberia estar todo bien");

        for(int i = 0; i < batchSize; i++){
            System.out.println("insertando a la respuesta el elemento numero " + i);
            System.out.println("El innerIndex es " + innerIndex);
            if(innerIndex >= valuesBuffer.size()){
                System.out.println("No hay mas elementos en este nodo");
                list = list.getNext();
                System.out.println("Moviendonos al siguiente nodo");
                nodosRecoridos++;
                elementosSaltados += valuesBuffer.size();
                valuesBuffer = list.getValues();
                System.out.println("Agarrando los valores de este nuevo nodo");
                System.out.println("elementosSaltados es " + elementosSaltados);
                System.out.println("nodosRecoridos es " + nodosRecoridos);
                System.out.println("la nueva posicion es 0");
                innerIndex = 0;
            }
            System.out.println("Insertando en este batch numero "+ batch + " el elemento en la posicion " + (elementosSaltados + innerIndex));
            res[i] = valuesBuffer.get(innerIndex);
            innerIndex ++;
        }
        return res;
    }
    public MusicData[] getBatchDuracionMusic(int batch) {
        int floor = batch*batchSize;
        BPlusNode<Integer, MusicData> list = duracion.getList();
        MusicData[] res = new MusicData[batchSize];
        //valuesBuffer es el conjunto de arreglos de MusicData que se encuentran en el nodo actual
        MyArrayList<MusicData> valuesBuffer;
        try {
            valuesBuffer = list.getValues();
        }catch(Exception e){
            valuesBuffer = null;
        }
        int innerIndex = 0;
        boolean flag = false;
        try{
            //avanzando la lista hasta el piso, si no se puede hay que leer mil elementos más
            for(int i = 0; i < floor; list = list.getNext()){
                valuesBuffer = list.getValues();
                if(floor - i < valuesBuffer.size()){
                    innerIndex = floor - i;
                    break;
                }
                i += valuesBuffer.size();
            }
            //hasta aca valuesBuffer ya esta en el nodo que le corresponde piso
        } catch(Exception e){
            //o no 
            for(int i = 0; i < dockerSize; i++){
                int index = loadedDura*dockerSize + i;
                duracion.insertar(Integer.parseInt(mds.getMusicData(index).getCancion().getPopularity()), mds.getMusicData(index));
            }
            flag = true;
            loadedDura++;
        }//ahora list ya esta en el ultimo nodo que habia cargado, puede que toda la lista se halla actualizado
        //por lo que toca ir al principio
        if(flag){
            list = duracion.getList();
            for(int i = 0; i < floor; list = list.getNext()){
                valuesBuffer = list.getValues();
                if(floor - i < valuesBuffer.size()){
                    innerIndex = floor - i;
                    break;
                }
                i += valuesBuffer.size();
            }
        }
        
        for(int i = 0; i < batchSize; i++){
            if(innerIndex >= valuesBuffer.size()){
                list = list.getNext();
                valuesBuffer = list.getValues();
                innerIndex = 0;
            }
            res[i] = valuesBuffer.get(innerIndex);
            innerIndex ++;
        }
        return res;
    }
    public MusicData[] getBatchFechaMusic(int batch) {
        int floor = batch*batchSize;
        BPlusNode<Integer, MusicData> list = fecha.getList();
        MusicData[] res = new MusicData[batchSize];
        //valuesBuffer es el conjunto de arreglos de MusicData que se encuentran en el nodo actual
        int nodosRecoridos = 0;
        int elementosSaltados = 0;
        MyArrayList<MusicData> valuesBuffer;
        try {
            valuesBuffer = list.getValues();
        }catch(Exception e){
            valuesBuffer = null;
        }
        int innerIndex = 0;
        boolean flag = false;
        try{
            //avanzando la lista hasta el piso, si no se puede hay que leer mil elementos más
            System.out.println("intentando colocar index y list en sus posiciones correctas para extraer el batch");
            for(; elementosSaltados < floor; list = list.getNext()){
                System.out.println("nodos recorridos: " + nodosRecoridos);
                System.out.println("elementos saltados " + elementosSaltados);
                valuesBuffer = list.getValues();
                //supongo que aca falla por que valuesBuffer es nulo
                if(floor - elementosSaltados < valuesBuffer.size()){
                    innerIndex = floor - elementosSaltados;
                    System.out.println("Encontramos el index interno " + innerIndex);
                    break;
                }
                elementosSaltados += valuesBuffer.size();
                System.out.println("Saltamos " + valuesBuffer.size() + " elementos");
                System.out.println("Avanzamos al siguiente nodo");
                nodosRecoridos++;
            }
            //hasta aca list ya esta en el nodo que le corresponde
            //e innerIndex ya se encuentra en el indice que le corresponde
        } catch(Exception e){
            //si hubo un error hay que cargar mil elementos más
            System.out.println("He fallado, intentando cargar mil elementos más");
            for(int i = 0; i < dockerSize; i++){
                
                int index = loadedFecha*dockerSize + i;
                System.out.println("Insertando elemento numero " + index);
                fecha.insertar(Integer.parseInt(mds.getMusicData(index).getCancion().getYear()), mds.getMusicData(index));
            }
            loadedFecha++;
            flag = true;
        }//todo pudo haber cambiado por lo que si sucedio la insercion entonces toca hacer lo mismo
        //por que aca puede que valuesBuffer sea nulo?

        if(flag){
            System.out.println("He entrado aca por que hubo un error y vuelvo a recorrer la lista hasta estar en el nodo y posicion correcta");
            list = fecha.getList();
            elementosSaltados = 0;
            nodosRecoridos = 0;
            for(; elementosSaltados < floor; list = list.getNext()){
                //por que hay error aca
                System.out.println("nodos recorridos: " + nodosRecoridos);
            System.out.println("elementos saltados " + elementosSaltados);
                valuesBuffer = list.getValues();
                if(floor - elementosSaltados < valuesBuffer.size()){
                    innerIndex = floor - elementosSaltados;
                    System.out.println("Encontramos el index interno " + innerIndex);
                    break;
                }
                elementosSaltados += valuesBuffer.size();
                System.out.println("Avanzamos al siguiente nodo");
                nodosRecoridos++;
            }
        }
        System.out.println("Hasta este punto elementosSaltados es " + elementosSaltados);
        System.out.println("nodos recorridos: " + nodosRecoridos);
        System.out.println("innerIndex es " + innerIndex);
        System.out.println("Hasta aca deberia estar todo bien");

        for(int i = 0; i < batchSize; i++){
            System.out.println("insertando a la respuesta el elemento numero " + i);
            System.out.println("El innerIndex es " + innerIndex);
            if(innerIndex >= valuesBuffer.size()){
                System.out.println("No hay mas elementos en este nodo");
                list = list.getNext();
                System.out.println("Moviendonos al siguiente nodo");
                nodosRecoridos++;
                elementosSaltados += valuesBuffer.size();
                valuesBuffer = list.getValues();
                System.out.println("Agarrando los valores de este nuevo nodo");
                System.out.println("elementosSaltados es " + elementosSaltados);
                System.out.println("la nueva posicion es 0");
                innerIndex = 0;
            }
            System.out.println("Insertando en este batch numero "+ batch + " el elemento en la posicion " + (elementosSaltados + innerIndex));
            res[i] = valuesBuffer.get(innerIndex);
            innerIndex ++;
        }
        return res;
    }
    
}
