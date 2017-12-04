package com.example.Glasses.mic;

import javax.sound.sampled.*;
import java.io.*;

/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class JavaSoundRecorder {

    boolean done = false;
    public Thread stopper;
    // record duration, in milliseconds
    static final long RECORD_TIME = 2000;  // 2 seconds

    // path of the wav file
    File wavFile = new File("./resources/tmp.wav");

    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // the line from which audio data is captured
    TargetDataLine line;


    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    /**
     * Captures the sound and record into a WAV file
     */
    void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            // start recording
            AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        done = true;
        System.out.println("Finished");
    }

    public boolean isDone() {
        return done;
    }

    public void record(int miliseconds)
    {
        done = false;
        final String out = null;
        // creates a new thread that waits for a specified
        // of time before stopping
        stopper = new Thread(() -> {
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            finish();
        });

        stopper.start();

        // start recording
        start();
    }

    /**
     * Entry to run the program
     */
//    public static void main(String[] args) {
//        final JavaSoundRecorder recorder = new JavaSoundRecorder();
//
//        // creates a new thread that waits for a specified
//        // of time before stopping
//        Thread stopper = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(RECORD_TIME);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//                recorder.finish();
//                try {
//                    Recognizer.syncRecognizeFile("./resources/audio2.wav");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        stopper.start();
//
//        // start recording
//        recorder.start();
//    }
}