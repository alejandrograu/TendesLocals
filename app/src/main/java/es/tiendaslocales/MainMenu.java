package es.tiendaslocales;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static es.tiendaslocales.MainActivity.numOption;
import static es.tiendaslocales.MainActivity.usuari;

public class MainMenu extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        for (int i = 0; i < menu.size(); i++) {
            if (i == numOption) menu.getItem(i).setVisible(false);
            else menu.getItem(i).setVisible(true);
        }

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String text=getString(R.string.txt_opt_sel);
        Intent intent;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            numOption=0;
            displayToast(text + getString(R.string.txt_action_inici));
            /*intent = new Intent(this, MainActivity.class);
            startActivity(intent);*/
            this.finish();
        }
        if (id == R.id.action_map) {
            if(usuari!=null){
                numOption=1;
                displayToast(text + getString(R.string.txt_action_map));
                intent=new Intent(this, PoblacionsActivity.class);
                startActivity(intent);
            }else{

                intent=new Intent(this, Login.class);
                startActivity(intent);
            }
        }

        if (id == R.id.action_shop) {
            numOption=2;
            displayToast(text + getString(R.string.txt_action_shop));
            return true;
        }
        if (id == R.id.action_info) {
            String msg="";
            if (numOption==0) msg=getString(R.string.txtInfoHome);
            if (numOption==1) msg=getString(R.string.txtInfoMap);
            if (numOption==2) msg=getString(R.string.txtInfoHome);

            new CuadroDialogo(this, msg);
            return true;
        }

        if (id == R.id.action_producte) {
            displayToast(text + getString(R.string.txt_insertarproducte));
            intent = new Intent(this, ProducteNou.class);
            startActivity(intent);
            //this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String text){
        int duration= Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(this,text, duration);
        toast.show();
    }

}
