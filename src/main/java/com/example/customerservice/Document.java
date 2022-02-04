package com.example.customerservice;

import com.example.customerservice.commands.CreateDocumentCommand;
import com.example.customerservice.commands.DeleteDocumentCommand;
import com.example.customerservice.commands.PatchDocumentCommand;
import com.example.customerservice.commands.UpdateDocumentCommand;
import com.example.customerservice.dto.DocumentDTO;
import com.example.customerservice.events.CreatedDocumentEvent;
import com.example.customerservice.events.DeletedDocumentEvent;
import com.example.customerservice.events.UpdatedDocumentEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.json.JsonStructure;
import javax.json.JsonValue;

import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Aggregate
public class Document {

    @AggregateIdentifier
    private UUID id;
    private String title;
    private String domain;
    private String source;
    private String contributor;
    private String citationInformation;
    private List<DataField> dataFields;
    private List<Task> tasks;
    private File data;
    private boolean augmented;
    private UUID rootDocument;

    //Axon Framework needs a non-arg constructor to create an empty aggregate instance beofre initializing it using past events
    public Document() {
    }

    // Command handler contains the business logic and validations.
    @CommandHandler
    public Document(CreateDocumentCommand cmd) {
        //Publish the message internally to this aggregate and aggregate members and later on to the event bus
        UUID documentId = UUID.randomUUID();
        this.title = cmd.getTitle();
        this.domain = cmd.getDomain();
        this.source = cmd.getSource();
        this.contributor = cmd.getContributor();
        this.citationInformation = cmd.getCitationInformation();
        this.dataFields = cmd.getDataFields();
        this.tasks = cmd.getTasks();
        this.data = cmd.getData();
        System.out.println("Create Command has received the following file: " + cmd.getData());
        this.augmented = cmd.isAugmented();
        this.rootDocument = cmd.getRootDocument();
        AggregateLifecycle.apply(new CreatedDocumentEvent(documentId, title, domain, source, contributor,
                citationInformation, dataFields, tasks, data, augmented, rootDocument));
    }

    @CommandHandler
    public void handle(DeleteDocumentCommand cmd) {
        AggregateLifecycle.apply(new DeletedDocumentEvent(id));
    }

    @CommandHandler
    public void handle(UpdateDocumentCommand cmd) {
        this.title = cmd.getTitle();
        this.domain = cmd.getDomain();
        this.source = cmd.getSource();
        this.contributor = cmd.getContributor();
        this.citationInformation = cmd.getCitationInformation();
        this.dataFields = cmd.getDataFields();
        this.tasks = cmd.getTasks();
        this.data = cmd.getData();
        this.augmented = cmd.isAugmented();
        this.rootDocument = cmd.getRootDocument();
        AggregateLifecycle.apply(new UpdatedDocumentEvent(id, title, domain, source, contributor,
                citationInformation, dataFields, tasks, data, augmented, rootDocument));
    }

    @CommandHandler
    public void handle(PatchDocumentCommand cmd) {
        // Build a DTO Object, convert it to JSON, apply patch and the update the aggregate
    /*
        try {
            //DocumentDTO currentState = new DocumentDTO(this.domain, this.source, this.contributor, this.citationInformation,
            //        this.dataFields, this.tasks, this.data, this.augmented, this.rootDocument);

            JsonStructure target = cmd.getObjectMapper().convertValue(this, JsonStructure.class);
            JsonValue patchedDocument = cmd.getPatch().apply(target);
            //DocumentDTO modifiedDocument = cmd.getObjectMapper().convertValue(patchedDocument, this.getClass());

            //System.out.println(modifiedDocument);



        } catch (IOException e) {
            e.printStackTrace();
        }
*/

    }

    @EventSourcingHandler
    public void handle(UpdatedDocumentEvent event) {
        id = event.getId();
    }

    @EventSourcingHandler
    public void handle(CreatedDocumentEvent event) {
        //Its mandatory in an EventSourcing Handler to set the aggregate identifier
        id = event.getId();
    }

    @EventSourcingHandler
    public void handle(DeletedDocumentEvent event) {
        id = event.getId();
        //Will mark the Aggregate instance calling the function as being 'deleted'. According to the Axon documentation this function
        //should be called from an @EventSourcingHandler annotated function
        markDeleted();
    }
}
