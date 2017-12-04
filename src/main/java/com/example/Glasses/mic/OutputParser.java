package com.example.Glasses.mic;

/**
 * Program recognises letter user said
 * we may ask for recognition with given file (.wav)
 * or when file is not given we record it with mic recorder
 */

public class OutputParser {

    JavaSoundRecorder javaSoundRecorder = new JavaSoundRecorder();

    public char parse(int miliseconds)
    {
        javaSoundRecorder.record(miliseconds);
        synchronized (this)
        {
            while(javaSoundRecorder.stopper.isAlive())
            {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return recognize("./main/resources/tmp.wav");
    }

    public char parse(String filename)
    {
        return recognize(filename);
    }

    private char recognize(String filename)
    {
        try {
            Recognizer.syncRecognizeFile(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 'a';
    }
}
