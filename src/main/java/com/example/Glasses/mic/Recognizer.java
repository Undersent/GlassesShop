package com.example.Glasses.mic;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Recognizer {

    public static void main(String[] args) {
        try {
            syncRecognizeFile("./main/resources/audio2.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String syncRecognizeFile(String fileName) throws Exception, IOException {
        SpeechClient speech = SpeechClient.create();
        String out = null;

        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteString audioBytes = ByteString.copyFrom(data);

        // Configure request with local raw PCM audio
        RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setLanguageCode("en-US")
                .setSampleRateHertz(16000)
                .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder()
                .setContent(audioBytes)
                .build();

        // Use blocking call to get audio transcript
        RecognizeResponse response = speech.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();

        for (SpeechRecognitionResult result : results) {
            // There can be several alternative transcripts for a given chunk of speech. Just use the
            // first (most likely) one here.
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            out = alternative.getTranscript();
            System.out.printf("Transcription: %s%n", out);
            System.out.printf("Confidence: %f%n", alternative.getConfidence());

        }
        speech.close();
        return out;
    }
}
