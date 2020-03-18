package es.tiendaslocales;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class InfoTendaActivity extends MainMenu {

    TextView nom;
    TextView poblacio;
    TextView adresa;
    Button telefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_tenda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nom=(TextView)findViewById(R.id.txt_nom);
        poblacio=(TextView)findViewById(R.id.txt_poblacio);
        adresa=(TextView)findViewById(R.id.txt_adresa);
        telefon=(Button)findViewById(R.id.btn_telefono);


        /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        Bundle bundle=this.getIntent().getExtras();
        String nomTenda=bundle.getString("Tenda");
        Tenda objecteTenda=TendesDAO.getTendaTelefon(nomTenda);
        String tenda=objecteTenda.getNom();
        String poble=objecteTenda.getPoblacio();
        String direccio=objecteTenda.getAdresa();
        String tlf=objecteTenda.getTelefon();
        nom.setText(tenda);
        poblacio.setText(poble);
        adresa.setText(direccio);
        telefon.setText(tlf);

        telefon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+telefon.getText().toString().trim()));
                startActivity(callIntent);
            }
        });
    }

}
