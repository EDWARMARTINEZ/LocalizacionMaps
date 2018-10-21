package maps.gr02_20182.compumovil.udea.edu.co;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    double latitud = 6.25184;
    double longitud = -75.56359;
    private static int marcadorIr = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
       // LatLng caucasia = new LatLng(7.98654, -75.19349);
        //mMap.addMarker(new MarkerOptions().position(caucasia).title("RestMobil Caucasia"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(caucasia, 7));

        if(marcadorIr == 0){
            marcadorCaucasia(7.98654, -75.19349);
        }else  if(marcadorIr == 1){
            marcadorCaucasia(7.98654, -75.19349);
        }else  if(marcadorIr == 2){
            marcadorMedellin(latitud, longitud);
        }else if(marcadorIr == 3){
            myUbicacion();
        }else  if(marcadorIr == 4){
            marcadorCaucasia(7.98654, -75.19349);
            marcadorMedellin(latitud, longitud);
            myUbicacion();
        }



        //Tipos de Mapas
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }


    private void marcadorCaucasia(double latitud, double longitud) {
        //Icono personalizado de mapas dos marcadores
        LatLng caucasia = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions()
                .position(caucasia)
                .title("RestMobil Caucasia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(caucasia, 7));

    }


    private void marcadorMedellin(double latitud, double longitud) {
        //Icono personalizado de mapas dos marcadores
        LatLng medellin = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions()
                .position(medellin)
                .title("RestMobil Medellin")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_restaurante_foreground)).anchor(0.0f, 1.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(medellin, 7));

    }

    private void marcadorHouse(double latitud, double longitud) {
        LatLng ubicacion = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions()
                .position(ubicacion)
                .title("Aquí Estoy")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ic_person_background)).anchor(0.0f, 1.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 7));
    }

    private void myUpdateLocalizacion(Location location) {
        if(location !=null)
        {
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            marcadorHouse(latitud, longitud);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myUpdateLocalizacion(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void myUbicacion() {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                 return;
        }
        LocationManager geolocalitation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = geolocalitation.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Location location = geolocalitation.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        myUpdateLocalizacion(location);
        //geolocalitation.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,locationListener);
        geolocalitation.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locationListener);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscar, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_caucasia) {
            marcadorIr = 1;
            openRefrescar();
        }else if (id == R.id.nav_medellin) {
            marcadorIr = 2;
            openRefrescar();
        } else if (id == R.id.nav_house) {
            marcadorIr = 3;
            openRefrescar();
        } else if (id == R.id.nav_marcadores) {
            marcadorIr = 4;
            openRefrescar();
        }
        return super.onOptionsItemSelected(item);
    }


    private void openRefrescar() {
        Intent miIntent = new Intent(this, MapsActivity.class);
        startActivity(miIntent);
    }






}
