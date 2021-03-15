package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste); // executa a musica

        inicializarSeekBar();
    }

    private void inicializarSeekBar() {

        seekVolume = findViewById(R.id.seekVolume);

        //Configura o audio mananger
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //descobre qual o volume atual e max que o user pode usar

        //recupera os valores de volume maximo e volume atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Configura os valores maximos e progresso de volume para o SeekBar
        seekVolume.setMax(volumeMaximo);
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI); //normalmente a flag é configurada como 0
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void executarSom(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.start(); //executa o som
        }
    }

    public void pausarMusica(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // pausa a musica
        }
    }

    public void pararMusica(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop(); // parar a musica
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste); // configura novamente
        }
    }

    @Override
    protected void onDestroy() { // ao fechar o app
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release(); // libera recursos de midia na memoria
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() { // ao sair do app a musica ira pausar
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}
