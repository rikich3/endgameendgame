package TreeB;

import org.springframework.beans.factory.annotation.Autowired;

import com.unsa.eda.api.data.MusicData;
import com.unsa.eda.service.MusicDataService;
import com.unsa.eda.service.Reader.Reader;


public class Main {
  static BPLus<Integer, MusicData> tree = new BPLus<Integer, MusicData>(4);
  static MusicDataService mds = new MusicDataService(new Reader());
  public static void main(String[] args){
    
    for(int i = 0; i < 3; i++)
      tree.insertar(i, mds.getMusicData(i));
    tree.mostrarLista();
  }
}
