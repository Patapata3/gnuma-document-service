package com.example.documentservice.parser;

import com.example.documentservice.Sentence;
import com.example.documentservice.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;

public class DocumentParser {
    private final String TOKEN = "token";
    private final String NER_TAG = "ner_tags";

    public DocumentParser() {

    }

    public List<Sentence> parseDocument(byte[] data)  {
        System.out.println("Hallo beginne mit dem Parsen");

        List<Sentence> sentences = new ArrayList<Sentence>();
        int id = 0;
        Map<Integer, String> metaTags = new HashMap<Integer, String>();
        try {
            BufferedReader b = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));

            String readLine = "";


            List<Token> tokens = null;
            int line=0;
            while ((readLine = b.readLine()) != null) {
                if(line != 0) {
                    if(readLine.trim().isEmpty()) {
                        sentences.add(new Sentence(id, tokens));
                        id = id+1;
                        tokens = new ArrayList<Token>();

                    }
                    else {
                        Map<String, Object> metaTagsToken = new HashMap<String, Object>();
                        String[] splitted = readLine.split(" ");
                        for(int i = 0; i < splitted.length; i++) {
                            metaTagsToken.put(metaTags.get(i), splitted[i]);
                        }
                        String token = (String) metaTagsToken.get(TOKEN);
                        String ner_tag = (String) metaTagsToken.get(NER_TAG);
                        metaTagsToken.remove(TOKEN);
                        metaTagsToken.remove(NER_TAG);
                        tokens.add(new Token(token, ner_tag, metaTagsToken));
                    }
                }
                else {
                    tokens = new ArrayList<Token>();

                    String[] splitted = readLine.split("\t");
                    for(int i = 0; i < splitted.length; i++) {
                        metaTags.put(i, splitted[i]);
                    }
                }
                line = line+1;
            }
            sentences.add(new Sentence(id, tokens));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return sentences;
    }

    public String process(String line) {
        return line;
    }
}