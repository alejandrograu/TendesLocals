package es.tiendaslocales;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import static es.tiendaslocales.R.*;

public class CuadroDialogo {

    public CuadroDialogo(Context context, String msg) {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layout.cuadro_dialogo);

        TextView txtInfo=(TextView) dialog.findViewById(id.txtinfo);
        ImageView imgAccept=(ImageView) dialog.findViewById(id.img_accept);

        txtInfo.setText(msg);

        imgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
