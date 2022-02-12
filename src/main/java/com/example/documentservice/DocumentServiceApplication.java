package com.example.documentservice;


import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


//login vorname.nachname
//passwort 123456
@SpringBootApplication
public class DocumentServiceApplication {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DocumentServiceApplication.class, args);
    }


    /**
     * Correctly configuring the XStream serializer to avoid security warnings.
     */
    @Autowired
    public void configure(Serializer serializer) {
        if(serializer instanceof XStreamSerializer) {
            XStream xStream = ((XStreamSerializer)serializer).getXStream();
            XStream.setupDefaultSecurity(xStream);
            xStream.allowTypesByWildcard(new String[] { "io.axoniq.demo.**", "org.axonframework.**" });
        }
    }




}

