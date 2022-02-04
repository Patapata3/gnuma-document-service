package com.example.customerservice.events;

import com.example.customerservice.DataField;
import com.example.customerservice.Task;
import java.io.File;
import java.util.List;
import java.util.UUID;

public class CreatedDocumentEvent {

    private final UUID id;
    private final String title;
    private final String domain;
    private final String source;
    private final String contributor;
    private final String citationInformation;
    private final List<DataField> dataFields;
    private final List<Task> tasks;
    private final File data;
    private final boolean augmented;
    private final UUID rootDocument;

    public CreatedDocumentEvent(UUID id, String title, String domain, String source, String contributor, String citationInformation,
                                List<DataField> dataFields, List<Task> tasks, File data,
                                boolean augmented, UUID rootDocument) {
        this.id = id;
        this.title = title;
        this.domain = domain;
        this.source = source;
        this.contributor = contributor;
        this.citationInformation = citationInformation;
        this.dataFields = dataFields;
        this.tasks = tasks;
        this.data = data;

        System.out.println("Hello from the Created Document Event " + this.data);
        this.augmented = augmented;
        this.rootDocument = rootDocument;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDomain() {
        return domain;
    }

    public String getSource() {
        return source;
    }

    public String getContributor() {
        return contributor;
    }

    public String getCitationInformation() {
        return citationInformation;
    }

    public List<DataField> getDataFields() {
        return dataFields;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public File getData() {
        return data;
    }

    public boolean getAugmented() {
        return augmented;
    }

    public UUID getRootDocument() {
        return rootDocument;
    }

    @Override
    public String toString() {
        return "CreatedDocumentEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", domain='" + domain + '\'' +
                ", source='" + source + '\'' +
                ", contributor='" + contributor + '\'' +
                ", citationInformation='" + citationInformation + '\'' +
                ", dataFields=" + dataFields +
                ", tasks=" + tasks +
                ", data=" + data +
                ", augmented=" + augmented +
                ", rootDocument=" + rootDocument +
                '}';
    }
}
