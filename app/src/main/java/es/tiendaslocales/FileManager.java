package es.tiendaslocales;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class FileManager {
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final StorageReference storageRef = storage.getReference();
    private static final StorageReference dbRef=storageRef.child("db/TLDB.sql");
    private static final StorageReference gsRefdb=storage.getReferenceFromUrl("gs://tendes-locals.appspot.com/db/TLDB.sql");
    private static final StorageReference httpdbRef=storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/tendes-locals.appspot.com/o/db%2FTLDB.sql?alt=media&token=0ca5b766-59bf-4cce-9bcc-4045d552de45");
    private static final StorageReference verRef=storageRef.child("db/versionDB");
    private static final StorageReference gsRefver=storage.getReferenceFromUrl("gs://tendes-locals.appspot.com/db/versionDB");
    private static final StorageReference httpverRef=storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/tendes-locals.appspot.com/o/db%2FversionDB?alt=media&token=104c776f-a391-49c1-9e78-04a603608fee");
    private static final StorageReference imagesRef = storageRef.child("images");
    private int version;
    private ArrayList<String> dbExecSQL;



    public FileManager(){

    }
    public int FileVersion() throws IOException {

        final File localFileVersion = File.createTempFile("version", "db");

        verRef.getFile(localFileVersion)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...

                        try {
                            FileReader fr = new FileReader(String.valueOf(taskSnapshot));
                            BufferedReader br = new BufferedReader(fr);
                            version = Integer.parseInt(br.readLine());
                            fr.close();
                            Log.d("Firebase: ","version= "+version);
                        }
                        catch(Exception e) {
                            System.out.println("Excepcion leyendo fichero "+ localFileVersion + ": " + e);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });



        return version;
    }
    public ArrayList<String> FileDatabase() throws IOException {
        //final ArrayList<String> dbExecSQL = new ArrayList<>();

        final File localFileVersion = File.createTempFile("TLDB", "sql");

        dbRef.getFile(localFileVersion)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...

                        try {
                            FileReader fr = new FileReader(String.valueOf(localFileVersion));
                            BufferedReader br = new BufferedReader(fr);

                            String linea;
                            while((linea = br.readLine()) != null)
                                dbExecSQL.add(linea);

                            fr.close();
                        }
                        catch(Exception e) {
                            System.out.println("Excepcion leyendo fichero "+ localFileVersion + ": " + e);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

        return dbExecSQL;
    }
}
