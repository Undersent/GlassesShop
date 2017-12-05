package com.example.Glasses.mic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterMapper {

    static CharacterMapper singleton;

    private Map<Character,List<String>> characterListMap;

    private CharacterMapper()
    {
        characterListMap = new HashMap<>();
        characterListMap.put('a', Arrays.asList("a"));
        characterListMap.put('b', Arrays.asList("b","be"));
        characterListMap.put('c', Arrays.asList("c","see","sea"));
        characterListMap.put('d', Arrays.asList("d","the"));
        characterListMap.put('0', Arrays.asList("0","zero"));
        characterListMap.put('1', Arrays.asList("1","one"));
        characterListMap.put('2', Arrays.asList("2","two"));
    }

    public Character getCharacterForTranscription(String transcription) throws CharacterNotRecognizedException {
        if(transcription == null)
            throw new CharacterNotRecognizedException();

        for(Map.Entry<Character,List<String>> entry: characterListMap.entrySet())
        {
            for(String s: entry.getValue())
            {
                if(s.equals(transcription))
                    return entry.getKey();
            }
        }

        throw new CharacterNotRecognizedException();
    }

    public static synchronized CharacterMapper getInstance( ) {
        if (singleton == null)
            singleton=new CharacterMapper();
        return singleton;
    }

}
