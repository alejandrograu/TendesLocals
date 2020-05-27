package es.tiendaslocales;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static es.tiendaslocales.R.id;
import static es.tiendaslocales.R.layout;

public class PoblacioNova {

    public  PoblacioNova(final Context context, final LatLng point, final GoogleMap mMap) {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layout.poblacio_nova);

        final EditText editText_nom=(EditText) dialog.findViewById(id.editText_nom);
        final EditText editText_cp=(EditText) dialog.findViewById(id.editText_cp);
        TextView txtInfo=(TextView) dialog.findViewById(id.txtinfo);
        ImageView imgAccept=(ImageView) dialog.findViewById(id.img_accept);
        ImageView imgCancel=(ImageView) dialog.findViewById(id.img_cancel);
        final Context finalContext = context;
        Log.d("PoblacioNova","Start PoblacioNova");

        imgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom=editText_nom.getText().toString();
                String cp=editText_cp.getText().toString();

                if(nom.length()>=3 && cp.length()>=5){
                    Marker marker=mMap.addMarker(new MarkerOptions().position(point).title(nom).snippet(cp));
                    new FileManager().insertPobleDB(context,marker);
                    dialog.dismiss();

                }else{
                    if(nom.length()<3){
                        displayToast(finalContext, "El nom ha de tindre mes de 3 lletres.");
                    }else if(cp.length()<5){
                        displayToast(finalContext, "El CP ha de tindre al menys 5 numeros.");
                    }
                }
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void displayToast(Context context,String text){
        int duration= Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text, duration);
        toast.show();
    }
}
