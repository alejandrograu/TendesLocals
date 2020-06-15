package es.tiendaslocales;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ShopsActivity extends AppCompatActivity {
    String [] categories=new String [] {"Alimentacion","Limpieza","Perfumeria","Ropa","Farmacia"};
    String [] alimentacion=new String [] {"Carnes", "Pescados", "Frutas", "Verduras","Panaderia", "Bolleria"};
    String [] limpieza=new String [] {"Desinfectantes","Jabones"};
    String [] perfumeria=new String [] {"Perfumes","Cosmetica"};
    String [] ropa=new String [] {"Pantalones","Camisas"};
    String [] farmacia=new String [] {"Cremas", "Medicinas"};
    String [] subcategories;
    Spinner spinnerCategoria, spinnerSubCategoria;

    /*private static Map<String, String[]> categoria;
    static {
        categoria=new HashMap<String, String[]>();
        categoria.put("Alimentacion", new String[] {"Carnes", "Pescados", "Frutas", "Verduras","Panaderia", "Bolleria"});
        categoria.put("Limpieza", new String[]{"Desinfectantes","Jabones"});
        categoria.put("Perfumeria",new String[]{"Perfumes","Cosmetica"});
        categoria.put("Ropa", new String[]{"Pantalones","Camisas"});
        categoria.put("Farmacia", new String[]{"Cremas", "Medicinas"});
    }*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        stringCopy(alimentacion);

        spinnerCategoria=(Spinner) findViewById(R.id.spinner_categoria);
        spinnerSubCategoria=(Spinner) findViewById(R.id.spinner_subcategoria);

        ArrayAdapter<String> itemsCategoria= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, categories);
        itemsCategoria.setNotifyOnChange(true);
        spinnerCategoria.setAdapter(itemsCategoria);

        ArrayAdapter<String> itemsSubCategoria= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, subcategories);
        itemsSubCategoria.setNotifyOnChange(true);
        spinnerSubCategoria.setAdapter(itemsSubCategoria);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemCategoria=spinnerCategoria.getSelectedItem().toString();
                switch (itemCategoria){
                    case "Alimentacion":
                        //subcategories=new String[] {String.valueOf(alimentacion)};
                        stringCopy(alimentacion);
                        break;
                    case "Limpieza":
                        //subcategories=new String[] {String.valueOf(limpieza)};
                        stringCopy(limpieza);
                        break;
                    case "Perfumeria":
                        //subcategories=new String[] {String.valueOf(perfumeria)};
                        stringCopy(perfumeria);
                        break;
                    case "Ropa":
                        //subcategories=new String[] {String.valueOf(ropa)};
                        stringCopy(ropa);
                        break;
                    case "Farmacia":
                        //subcategories=new String[] {String.valueOf(farmacia)};
                        stringCopy(farmacia);
                        break;
                    default:
                        stringCopy(alimentacion);
                        break;
                }
                actualizarSubCategoria();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinnerSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void stringCopy(String [] string){
        subcategories=new String[string.length];
        for (int i=0; i<string.length;i++){
            subcategories[i]=string[i];
        }
    }

    public void actualizarSubCategoria(){
        ArrayAdapter<String> itemsSubCategoria= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, subcategories);
        itemsSubCategoria.setNotifyOnChange(true);
        spinnerSubCategoria.setAdapter(itemsSubCategoria);
    }

}
