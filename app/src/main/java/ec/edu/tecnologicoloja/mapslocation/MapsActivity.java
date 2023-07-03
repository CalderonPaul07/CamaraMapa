package ec.edu.tecnologicoloja.mapslocation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ec.edu.tecnologicoloja.mapslocation.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    ImageButton IMG_Ubicar, ubicar,cam;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    public static final String MyPREFERENCES = "MyPrefs";
    EditText tf_latitud, tf_longitud;
    public static final String latitude = "lat";
    public static final String longitude = "lon";


    double longitud, latitud;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableMyLocation();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        tf_latitud = findViewById(R.id.ET_latitud);
        tf_longitud = findViewById(R.id.ET_longitud);
        IMG_Ubicar = findViewById(R.id.imagemylocation);
        ubicar = findViewById(R.id.ubicacion_guardada);
        cam=findViewById(R.id.camara);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        IMG_Ubicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiPosicion();
            }
        });
        ubicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mi_dato();

            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MapsActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void MiPosicion() {
        LocationManager objLocation = null;
        LocationListener objLocListener;
        objLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objLocListener = new MyPosition();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 1000);
        }

        objLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, objLocListener);

        if (objLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (MyPosition.latitude != 0) {
                latitud = MyPosition.latitude;
                longitud = MyPosition.longitude;
                tf_longitud.setText(longitud + "");
                tf_latitud.setText(latitud + "");
                Toast.makeText(MapsActivity.this, "Latitud" + latitud + "Longitud" + longitud, Toast.LENGTH_SHORT).show();
//                Se declara una variable de tipo LatLng (Latitud, Longitud) y en ella se instancia los valores obtenidos de mi posicion
                LatLng miPosicio = new LatLng(latitud, longitud);
//                Se agrega esa variable latlng en posicion y se agrega un nombre a esa marca
                Marker mi_ubicacion = mMap.addMarker(new MarkerOptions().position(miPosicio).title("Mi casa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                Se realiza un zoom de 16.0 al momento de obtener posicion
                float zoomLevel = 18.0f;

//                De acuerdo a donde se este mueve el mapa hacia los valores determinados antes y se agrega el zoom
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(miPosicio, zoomLevel));

            }

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //    LatLng mi=new LatLng(-2.10 , -1.20);
        //     mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

//        Botón zoom
//        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapLongClickListener(this);
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @Override
    public void onMapLongClick(LatLng latLng) {

        Toast.makeText(MapsActivity.this, "Latitud" + latLng.latitude + "\n Longitud" + latLng.longitude, Toast.LENGTH_LONG).show();
        Marker mi_ubicacion = mMap.addMarker(new MarkerOptions().position(latLng).title("Ubición Guardada").icon(bitmapDescriptorFromVector(this, R.drawable.ubicacion_actual)));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(latitude, (float) latLng.latitude);
        editor.putFloat(longitude, (float) latLng.longitude);
        editor.apply();

    }

    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }
    }


    public void mi_dato() {
        double la = sharedPreferences.getFloat(latitude, 0);
        double lo = sharedPreferences.getFloat(longitude, 0);
        LatLng miPosicio = new LatLng(la, lo);
        //Se agrega esa variable latlng en posicion y se agrega un nombre a esa marca
        //Se realiza un zoom de 16.0 al momento de obtener posicion
        // float zoomLevel = 16.0f;
        //Se realiza un zoom de 16.0 al momento de obtener posicion
        float zoomLevel = 6.0f;
        //De acuerdo a donde se este mueve el mapa hacia los valores determinados antes y se agrega el zoom
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(miPosicio, zoomLevel));

    }
}