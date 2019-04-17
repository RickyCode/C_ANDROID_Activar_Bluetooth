package com.instagram.rickydelrioguzman.activarbluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    
    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0; //Independiente del otro código
    private static final int CODIGO_SOLICITUD_DESHABILITAR_BLUETOOTH = 1; //Independiente del otro código
    private Context context;
    private Activity activity;
    
    public boolean checarStatusPermiso(){
        // Revisa si el permiso está dado de alta en la aolicación:
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        // Se revisa si el permiso fue otorgado o no:
        return (resultado == PackageManager.PERMISSION_GRANTED); //PERMISSION_GRANTED tiene el identificador de que un permiso fue otorgado. Variable general de codigos de permiso.
//        if ( resultado == PackageManager.PERMISSION_GRANTED){ //PERMISSION_GRANTED tiene el identificador de que un permiso fue otorgado. Variable general de codigos de permiso.
//            return true;
//        }else{
//            return false;
//        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Retorna el resultado del permiso.
        // Se ejecuta una vez que ya se dio de alta el permiso.
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        // Gestión del permiso que acabamos de otorgar:
        // requestCode es el código del permiso otorgado
        // "cacheamos el callback"
        switch (requestCode){
            case CODIGO_SOLICITUD_PERMISO:
                if (checarStatusPermiso()) {
                    Toast.makeText(MainActivity.this, "YA está activo el permiso para bluetooth", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "NO está activo el permiso para bluetooth", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    
    public void solicitarPermiso(){
        // Esta función solicita el permiso al usuario.
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){
            Toast.makeText(MainActivity.this, "El permiso ya fue otorgado, si deseas desactivarlo puedes ir a los ajustes de la aplicación", Toast.LENGTH_SHORT).show();
        }else{
        Toast.makeText(MainActivity.this, "Se solicitará el permiso para el bluetooth", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH}, //Se pueden solicitar varios permisos a la vez
                CODIGO_SOLICITUD_PERMISO);

        }
    }
    
    public void habilitarBluetooth(View v) {
    
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) { // Si el dispositvo no tiene bluetooth
            Toast.makeText(MainActivity.this, "Tu dispositivo no tiene bluetooth", Toast.LENGTH_SHORT).show();
        } else {
        
            Toast.makeText(MainActivity.this, "Tu dispositivo sí tiene bluetooth", Toast.LENGTH_SHORT).show();
            solicitarPermiso();
    
            if (!mBluetoothAdapter.isEnabled()) {
                Intent habilitarBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(habilitarBluetoothIntent, CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
                Toast.makeText(MainActivity.this, "BLUETOOTH ACTIVADO", Toast.LENGTH_SHORT).show();
    
            }
        
        }
    }
    
//    public void deshabilitarBluetooth(View v) {
//        solicitarPermiso();
//
//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (mBluetoothAdapter == null) { // Si el dispositvo no tiene bluetooth
//            Toast.makeText(MainActivity.this, "Tu dispositivo no tiene bluetooth", Toast.LENGTH_SHORT).show();
//        } else {
//
//            if (!mBluetoothAdapter.isEnabled()) {
//                Intent habilitarBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(habilitarBluetoothIntent, CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
//            }
//
//        }
//    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;
    }
}
