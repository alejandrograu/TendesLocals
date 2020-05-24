package es.tiendaslocales;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static es.tiendaslocales.R.id;
import static es.tiendaslocales.R.layout;
import static es.tiendaslocales.PoblacionsActivity.dades;

public class PoblacioNova {
    Context context;
    String dades2[]=new String[2];

    public PoblacioNova(Context context) {
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
        imgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText_nom.length()<3 || editText_cp.length()<5){
                    dades2[0]=editText_nom.getText().toString();
                    dades2[1]=editText_cp.getText().toString();
                    dades=dades2;
                    dialog.dismiss();
                }else{
                    displayToast(finalContext, "No pot ser null cap dels camps!");
                    dades=null;
                }
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dades=null;
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
