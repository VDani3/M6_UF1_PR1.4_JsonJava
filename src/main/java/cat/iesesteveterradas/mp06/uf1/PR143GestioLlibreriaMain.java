package cat.iesesteveterradas.mp06.uf1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PR143GestioLlibreriaMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Practica de JSON en Java\n");
        PR143GestioLlibreriaMain ex = new PR143GestioLlibreriaMain();

          //Comença
        List<Map<String,Object>> info = ex.llecturaJSONJackson("data/llibres_input.json");
        info = ex.modificarAnyJSONJackson(info, 1, 1995);
        info = ex.afegirLlibre(info, 4, "Històries de la ciutat", "Miquel Soler", 2022);
        info = ex.eliminarLlibre(info, 2);
        ex.guardar(info, "data/llibres_output.json");
        
    
    }

      //Metodes
    public List<Map<String,Object>> llecturaJSONJackson(String file) {
          //Setegem la llista per si acas dona error
        List<Map<String,Object>> llistaDeLlibres = null;
          //Per si fa error
        try {
              //Fa el Mapper i llegeix tot els 'Diccionaris' que n'hi han
            ObjectMapper objectMapper = new ObjectMapper();
            
            llistaDeLlibres = objectMapper.readValue(new File(file), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            
            System.out.println("Resultat de llectura: ");
            for (Map<String, Object> map : llistaDeLlibres ) {
                System.out.println("  "+map);
            }
            
        } catch (Exception e) {e.printStackTrace();}
        return llistaDeLlibres;
    }   

    public List<Map<String,Object>>  modificarAnyJSONJackson(List<Map<String,Object>> l, int id, int newAny) {
        int any = -1;
          //Recorre la llista, i canvia l'any del llibre amb l'id especificat
        for (int i = 0; i < l.size(); i++) {
            if ((int) l.get(i).get("id") == id) {
                any = (int) l.get(i).get("any");
                l.get(i).put("any", newAny);
                break;
            }
        }

          //Si l'any es major a 0 es que s'ha modificat, si no es que no s'ha trobat
        if (any > 0) {
            System.out.println(String.format("Any del llibre amb id %s canviat de %s a %s", id, any, newAny));
        } else {
            System.out.println("No s'ha pogut canviar res");
        }
        return l;
    }

    public List<Map<String,Object>> afegirLlibre(List<Map<String,Object>> l, int id, String titol, String autor, int any){
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("id", id);
        m.put("titol", titol);
        m.put("autor", autor);
        m.put("any", any);

        l.add(m);
        System.out.println("Nou llibre creat correctament");
        return l;

    }

    public List<Map<String,Object>> eliminarLlibre(List<Map<String,Object>> l, int id) {
      for (int i = 0; i < l.size(); i++) {
        if ((int) l.get(i).get("id") == id) {
          l.remove(i);
        }
      }
      return l;
    }

    public void guardar(List<Map<String, Object>> l, String file) {
      ObjectMapper oM = new ObjectMapper();
      oM.enable(SerializationFeature.INDENT_OUTPUT);
      
      try {
        oM.writeValue(new File(file), l);
        System.out.println("Guardat correctament");
      } catch (Exception e) {
        System.out.println("Error al intentar guardar el ficher");
        return;
      }
    }
}
