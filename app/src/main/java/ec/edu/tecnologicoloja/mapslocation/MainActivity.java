package ec.edu.tecnologicoloja.mapslocation;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView foto, foto_2;
    public Button tomarFoto, Regresar;
    public String ruta;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foto = findViewById(R.id.imageFoto);
        tomarFoto = findViewById(R.id.btnCamara);
        Regresar = findViewById(R.id.btnRegresar);

        tomarFoto.setOnClickListener(this);
        Regresar.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enableLocalStorage();
        enableMyCamera();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public void onClick(View view) {
        if (tomarFoto == view) {
            Camara();

        } else if (Regresar == view) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
    }


    public void Camara() {
        // Crea una nueva instancia de Intent para capturar una imagen
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Verifica si hay alguna actividad que pueda manejar el intent (aplicación de cámara instalada)
        if (intentCamara.resolveActivity(getPackageManager()) != null) {
            // Si hay una actividad disponible, inicia la captura de imagen y espera el resultado
            startActivityForResult(intentCamara, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Verifica si el resultado proviene de la captura de imagen (requestCode = 1) y si fue exitoso (resultCode = RESULT_OK)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Obtén los datos extras de la imagen capturada
            Bundle extras = data.getExtras();
            // Extrae la imagen capturada del objeto Bundle
            Bitmap bitmap = (Bitmap) extras.get("data");
            // Asigna la imagen capturada a un componente visual (por ejemplo, un ImageView llamado "foto")
            foto.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
            return true;
        }
        if (id == R.id.save) {
            //obtiene de el image view qu se obtiene del on result
            Bitmap imagenBitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
            guardarLocal(imagenBitmap);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void guardarLocal(Bitmap imagenBitmap) {

        try {
            // Llama al método "Guardar" para guardar la imagen en un archivo y obtener una referencia a dicho archivo
            File mapa = Guardar(imagenBitmap);
            // Verifica si se pudo guardar la imagen correctamente
            if (mapa != null) {
                // Obtiene la URI del archivo utilizando FileProvider para compartirlo de manera segura
                Uri uri = FileProvider.getUriForFile(this, "ec.edu.tecnologicoloja.mapslocation.fileprovider", mapa);
                // Muestra un mensaje en un Toast indicando que la imagen se guardó correctamente en el teléfono
                Toast.makeText(MainActivity.this, "Imagen guardada en el telefono", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // En caso de que ocurra un error al guardar la imagen, muestra el error en el registro (log)
            Log.e("error", e.toString());
        }

    }

    public File Guardar(Bitmap imagenBitmap) throws IOException {
        // Define un nombre para la foto, por ejemplo, "foto_"
        String nombreFoto = "foto_";

        // Obtiene el directorio de archivos externos específico para imágenes (por ejemplo, /storage/emulated/0/Android/data/paquete_de_la_app/files/Pictures)
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Crea un archivo temporal con el nombre especificado y la extensión ".jpg" en el directorio de imágenes
        File foto = File.createTempFile(nombreFoto, ".jpg", directorio);

        // Crea un flujo de salida de archivo para escribir los datos de la imagen en el archivo
        FileOutputStream outputStream = new FileOutputStream(foto);

        // Comprime y guarda la imagen en el archivo en formato JPEG con una calidad del 100%
        imagenBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        // Cierra el flujo de salida de archivo
        outputStream.close();

        // Obtiene la ruta absoluta del archivo guardado
        ruta = foto.getAbsolutePath();

        // Devuelve el objeto de archivo que representa la foto guardada
        return foto;

    }

    public void enableLocalStorage() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
    }

    public void enableMyCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,}, 1000);
        }
    }
}
