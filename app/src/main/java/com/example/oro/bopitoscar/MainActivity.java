package com.example.oro.bopitoscar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    int actividad, conta;
    boolean izq, der, mueve;
    ImageView cambioImagen, imagenStart;
    TextView textResultado, orientation;




    //Ag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager manager = (SensorManager)getSystemService( SENSOR_SERVICE );
        textResultado = findViewById(R.id.textResultado);
        orientation = findViewById(R.id.orientation);
        cambioImagen = (ImageView)findViewById(R.id.cambioImagen);

        manager.registerListener( this,
                manager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER ),
                SensorManager.SENSOR_DELAY_GAME );

        cambioImagen.setImageResource(R.drawable.entrada);
        MediaPlayer sonido = MediaPlayer.create( this, R.raw.tono);
        sonido.start();

        imagenStart = (ImageView)findViewById(R.id.start);
        imagenStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actividad = 0;
                juego( true );
            }
        });

    }

    public void juego( boolean res ) {
        if( res ){
            try {
                Thread.sleep(1600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            actividad = (int)(Math.random() * 3) + 1;

            if( actividad == 1 ){
                cambioImagen.setImageResource(R.drawable.varitaizq);
                MediaPlayer jarvis = MediaPlayer.create( this, R.raw.giraizq );
                jarvis.start();
                orientation.setText("Gira izquierda!");
                izq = true;
                der = false; mueve = false;
            }
        }
        if( actividad == 2 ){
            cambioImagen.setImageResource(R.drawable.varitaderecha);
            MediaPlayer voz = MediaPlayer.create( this, R.raw.gderecha );
            voz.start();
            orientation.setText("Gira derecha!");
            der = true;
            izq = false; mueve = false;
        }
        if( actividad == 3 ){
            cambioImagen.setImageResource(R.drawable.logo);
            MediaPlayer voz = MediaPlayer.create( this, R.raw.agita );
            voz.start();
            orientation.setText("Agita!");
            mueve = true;
            der = false; izq = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if( izq ){
            int v = (int) event.values[0];
            if ( v > 10  ){
                conta++;
                textResultado.setText("ronda: " + conta );
                izq = false;
                juego( true );
            }
        }
        if( der ){
            int v = (int) event.values[0];
            if ( v < -10 ){
                conta++;
                textResultado.setText("ronda: " + conta );
                der = false;
                juego( true );
            }
        }
        if( mueve ){
            int v = (int) event.values[1];
            if ( v < 0 ){
                conta++;
                textResultado.setText("ronda: " + conta );
                mueve = false;
                juego( true );
            }
        }
    }
}