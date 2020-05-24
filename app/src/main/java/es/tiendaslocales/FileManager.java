package es.tiendaslocales;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static es.tiendaslocales.MainActivity.DATABASE_VERSION;
import static es.tiendaslocales.MainActivity.dataBaseSQL;
import static es.tiendaslocales.MainActivity.favorit;
import static es.tiendaslocales.MainActivity.imgPerfil_toolbar_class;
import static es.tiendaslocales.MainActivity.usersDB;
import static es.tiendaslocales.MainActivity.usuari;


public class FileManager extends AppCompatActivity {
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final StorageReference storageRef = storage.getReference();
    private static final StorageReference dbRef=storageRef.child("db/databaseDB");
    private static final StorageReference verRef=storageRef.child("db/versionDB");
    private static final StorageReference imagesRef = storageRef.child("images");

    DatabaseReference usuariReference= FirebaseDatabase.getInstance().getReference().child("users");

    public FileManager(){

    }

    // ****************************************************************************
    // *   Metode per a descargar el nombre de la versio de la DB.
    // ****************************************************************************
    public int FileVersion() throws IOException {

        final File localFileVersion=File.createTempFile("version","DB");
        Log.d("FileManager","FileVersion localFileVersion=>"+localFileVersion);
        verRef.getFile(localFileVersion).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot){

                try {
                    FileReader fr = new FileReader(localFileVersion.getAbsolutePath());
                    BufferedReader br = new BufferedReader(fr);
                    DATABASE_VERSION = Integer.parseInt(br.readLine());
                    fr.close();
                    Log.d("FileManager","Version cargada Correctamente! "+DATABASE_VERSION);
                }
                catch(Exception e) {
                    System.out.println("Excepcion leyendo fichero "+ localFileVersion + "=> " + e);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FileManager","Error al crear el archiu TMP");
            }
        });
        return DATABASE_VERSION;
    }

    // *****************************************************************************
    // *   Metode per a les instruccions de insercio i creacio de la DB de SQLite  *
    // *****************************************************************************
    public ArrayList<String> FileDatabase() throws IOException {
        Log.d("FileManager","Start FileDatabase()");

        final File localFileDatabase = File.createTempFile("database", "DB");
        Log.d("FileManager","localFileDatabase="+localFileDatabase.getAbsolutePath());
        dbRef.getFile(localFileDatabase)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        try {
                            FileReader fr = new FileReader(String.valueOf(localFileDatabase));
                            BufferedReader br = new BufferedReader(fr);

                            String linea;
                            while((linea = br.readLine()) != null)
                                dataBaseSQL.add(linea);

                            fr.close();
                            Log.d("FileManager","Datos Cargados Correctamente!");
                        }
                        catch(Exception e) {
                            System.out.println("Excepcion leyendo fichero "+ localFileDatabase + "=> " + e);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("FileManager","Error al crear el archiu database.DB");
            }
        });
        return dataBaseSQL;
    }

    // *************************************************************
    // *   Metode per a la  insercio i creacio dels usuaris        *
    // *************************************************************
    public ArrayList<User> usuaris(){
        Log.d("FileManager","Start usuaris()");
        usuariReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user=dataSnapshot.getValue(User.class);
                Log.d("FileManager","usuaris() userDB= "+dataSnapshot.getKey()+" nom= "+user.getNom()+" favorit= "+user.getFavorit());
                if((usuari!=null)&&(usuari.equals(user.getNom()))){
                    favorit=user.getFavorit();
                }
                usersDB.add(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return usersDB;

    }

    public void insertarDades(final String camp, final String valor){
        usuariReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user=dataSnapshot.getValue(User.class);
                Log.d("FileManager","usuaris() userDB= "+dataSnapshot.getKey()+" nom= "+user.getNom()+" favorit= "+user.getFavorit());
                if((usuari!=null)&&(usuari.equals(user.getNom()))){
                    String id=dataSnapshot.getKey();
                    Log.d("FileManager","insertarDades getKey()="+id+" user.getNom="+user.getNom()+" camp="+camp+" valor="+valor);
                    usuariReference.child(id).child(camp).setValue(valor);
                }
                usersDB.add(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // ***********************************************************
    // *   Metodes per a descargar fitxers. Nom i Extensio         *
    // ***********************************************************
    public void download(final String nomFitxer, final String extFitxer){
        //StorageReference ref=FirebaseStorage.getInstance().getReference().child("firebase.pdf");
        StorageReference ref=FirebaseStorage.getInstance().getReference().child(nomFitxer+extFitxer);

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadFiles(FileManager.this, nomFitxer, extFitxer, DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    public void downloadFiles(Context context, String fileName, String fileExtension, String destinationDirectory, String url){
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,fileName+fileExtension);

        downloadManager.enqueue(request);
    }

    // *******************************************************************************
    // *   Metode que comproba si el usuari ja habia utilitzat anteriorment el       *
    // *   programa sabent si existeis el archiu i agafant el nom de usuari ja creat  *
    // *   Al mateix temps si no existira el archiu el craria nou.                   *
    // *   I concatena la DB oficial amb la del usuari desde Firebase.               *
    // *******************************************************************************
    public String fileUserExists(Context context){

            Log.d("FileManager","Start fileUserExists");
            try{
                File local_file_user= new File(context.getFilesDir(),"user");
                File local_DBfile_user=new File(context.getFilesDir(),"DB_"+usuari);
                Log.d("FileManager","local_file_user="+ local_file_user.getAbsolutePath());
                Log.d("FileManager","local_file_user.exists()="+local_file_user.exists());

                if(!local_file_user.exists()){
                    if(usuari!=null){
                        Log.d("FileManager","user not null, usuari="+usuari);
                        Log.d("FileManager","local_file_user not exists! and user="+usuari);
                        createLocalFileUser(context);
                    }else {
                        Log.d("FileManager","user null, usuari="+usuari);
                    }
                }else{
                    readFileUser(context, local_file_user);
                }

                Log.d("FileManager","user="+usuari);
                Log.d("FileManager","local_DB_file_user=>"+local_DBfile_user.getAbsolutePath());
                Log.d("FileManager","local_DB_file_user.exists()=>"+local_DBfile_user.exists());
                if(!local_DBfile_user.exists()){
                    if(usuari!=null){
                        Log.d("FileManager","local_DBfile_user not exists!");
                        downloadDB(context);
                        downloadImg(context);
                    }
                }else{
                    Log.d("FileManager","Already local_DBfile_user Exists!");
                    concatDB();
                }
            }catch (Exception e){
                Log.e("FileManager", "Error creant el archiu local_file_user==>"+e);
            }
            Log.d("FileManager","fileUserExists? usuari="+usuari);

        return usuari;
    }

    // ****************************************************************************
    // *   Metode per a extrarue el nom de usuari en el fitxer local              *
    // ****************************************************************************
    private void readFileUser(Context context, File local_file_user){
        try {
        BufferedReader fin=new BufferedReader(
                new InputStreamReader(new FileInputStream(local_file_user)));
        Log.d("FileManager","local_file_read="+ local_file_user.getAbsolutePath());
        usuari=fin.readLine();
        displayToast(context, "Read Local File User OK!");
        fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ****************************************************************************
    // *   Metode per a comprobar el nom i/o la clau insertada per a comparar     *
    // ****************************************************************************
    public Boolean isEqual(String nom, String valor){
        Log.d("FileManager","Start isEqual()");
        Boolean exists=false;

            for(User user:usersDB){
                Log.d("FileManager","user.getNom()= "+user.getNom()+" | user.getClau()= "+user.getClau()+" | nom= "+nom+" | valor= "+valor);

                if ((nom.equals("nom")) && (user.getNom().equals(valor))){
                    exists=true;
                    Log.d("FileManager","isEqual nom True");
                    break;
                }
                if((nom.equals("clau") && (user.getClau().equals(valor)))){
                    exists=true;
                    Log.d("FileManager","isEqual clau True");
                    break;
                }
            }

            Log.d("FileManager","isEqual exists="+ exists+" nom= "+nom+" valor= "+valor);
        return exists;
    }

    // ****************************************************************************
    // *   Metode per a concatenar base de dades oficial mes la del usuari        *
    // ****************************************************************************
    public void concatDB() throws IOException {
        Log.d("FileManager","Start concatDB");
        StorageReference userDbRef= storageRef.child("db/"+"DB_"+usuari);
        final File localFileDatabase = File.createTempFile("DB_"+usuari, "");
        userDbRef.getFile(localFileDatabase)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        try {
                            FileReader fr = new FileReader(String.valueOf(localFileDatabase));
                            BufferedReader br = new BufferedReader(fr);
                            String linea;
                            while((linea = br.readLine()) != null)
                                dataBaseSQL.add(linea);

                            fr.close();
                            Log.d("FileManager","concatDB Datos Cargados Correctamente!");
                        } catch(Exception e) {
                            Log.e("FileManager","concatDB Excepcion leyendo fichero "+ localFileDatabase + "=> " + e);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("FileManager","Error en concatDB!==>"+exception);
            }
        });
    }

    // ****************************************************************************
    // *   Metode per a pujar la base de dades al Firebase Storage                *
    // ****************************************************************************
    public void uploadDB(final Context context)throws IOException{
        Log.d("FileManager","Start uploadDB()");
        Uri file=Uri.fromFile(new File(context.getFilesDir(),"DB_"+usuari));
        Log.d("FileManager","uploadDB file="+file);
        StorageReference riversRef=storageRef.child("db/"+"DB_"+usuari);
        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl=taskSnapshot.getUploadSessionUri();
                displayToast(context,"uploadDB OK!");
                Log.d("FileManager","uploadDB OK!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FileManager","Error en uploadDB ==>"+e);
            }
        });

    }

    // ****************************************************************************
    // *   Metode per a pujar la image del usuari al Firebase Storage             *
    // ****************************************************************************
    public void uploadImg(final Context context, final Uri uri){
        Log.d("FileManager","Start uploadImg()");
        //Uri file=Uri.fromFile(new File(context.getFilesDir(),usuari+"DB"));
        Log.d("FileManager","uploadImg file="+uri);
        StorageReference riversRef=storageRef.child("images/imgPerfil_"+usuari);
        riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl=taskSnapshot.getUploadSessionUri();
                imgPerfil_toolbar_class.setImageURI(uri);
                displayToast(context,"uploadImg OK!");
                Log.d("FileManager","uploadImg OK!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FileManager","Error en uploadImg ==>"+e);
            }
        });
    }

    // ****************************************************************************
    // *   Metode per a descarregar la base de dades a memoria interna            *
    // ****************************************************************************
    public void downloadDB(final Context context) throws IOException {

        Log.d("FileManager","Start downloadDB()");
        //Uri file=Uri.fromFile(new File(usuari));
        StorageReference riversRef=storageRef.child("db").child("DB_"+usuari);
        final File localFile=File.createTempFile("DB_"+usuari,"");
        riversRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                displayToast(context,"downloadDB OK!");
                Log.d("FileManager","downloadDB OK!");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FileManager","Error en downloadDB ==>"+e);
                usuari=null;
            }
        });
    }

    // *****************************************************************************
    // *   Metode per a descarregar la image de perfil de usuari a memoria interna *
    // *****************************************************************************
    public void downloadImg(final Context context) throws IOException{
        Log.d("FileManager","Start downloadImg()");
        StorageReference riversRef=storageRef.child("images").child("imgPerfil_"+usuari);
        final File localFile=File.createTempFile("imgPerfil_"+usuari,"");
        riversRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                displayToast(context,"downloadImg OK!");
                Log.d("FileManager","downloadImg OK!");
                imgPerfil_toolbar_class.setImageURI(Uri.fromFile(localFile));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FileManager","Error en downloadDB ==>"+e);
            }
        });
    }

    // ****************************************************************************
    // *   Metode per a crear el fitxer de usuari en memoria interna              *
    // ****************************************************************************
    public void createLocalFileUser(Context context) throws IOException {
        Log.d("FileManager","Start createLocalFileUser!");
        File local_file_user= new File(context.getFilesDir(),"user");
        local_file_user.createNewFile();
        BufferedWriter out=new BufferedWriter((new FileWriter(local_file_user)));
        out.write(usuari);
        out.close();
        Log.d("FileManager","createLocalFileUser Created!");
        displayToast(context,"Archiu local de usuari creat!");
        Log.d("FileManager","local_file_create="+ local_file_user.getAbsolutePath());

    }

    // ****************************************************************************
    // *   Metode per a crear fitxer de bases de dades en memoria interna         *
    // ****************************************************************************
    public void createFileDB(Context context) throws IOException {
        Log.d("FileManager","Start createFileDB");
        try {
            File fileDB= new File(context.getFilesDir(),"DB_"+usuari);
            fileDB.createNewFile();
            Log.d("FileManager","createFileDB fileDB="+fileDB.getAbsolutePath());
            Log.d("FileManager","createFileDB Created!!");
            displayToast(context,"Archiu local DB creat!");
        }catch (Exception e){
            Log.e("FileManager","createFileDB ERROR=>"+e);
        }

    }

    public void insertDB(Context context,Marker marker, String type){
        String insert;
        if(type.equals("poblacions")){
            insert="INSERT INTO poblacions(codi, nom, cp, lat, lon) VALUES (";
            insert+=marker.getId()+", "+marker.getTitle()+", "+marker.getSnippet();
            insert+=", "+marker.getPosition().latitude+", "+marker.getPosition().longitude+");";
            try {
                insertarDB(context,insert);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("tendes")){

        }

    }

    public void insertarDB(Context context, String insertar) throws IOException {
        Log.d("FileManager","Start insertarDB");
        File local_fileDB= new File(context.getFilesDir(),"DB_"+usuari);
        BufferedWriter out=new BufferedWriter((new FileWriter(local_fileDB)));
        out.write(insertar);
        out.close();
        uploadDB(this);
        Log.d("FileManager","local_fileDB="+ local_fileDB.getAbsolutePath());

    }

    // *************************************************
    // *   Metodes per a mostrar misatges en pantalla   *
    // *************************************************
    public void displayToast(String text){
        int duration= Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(this,text, duration);
        toast.show();
    }
    // Amb el Context, s´eviten alguns errors de context.
    public void displayToast(Context context, String text){
        int duration= Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(context,text, duration);
        toast.show();
    }
}
