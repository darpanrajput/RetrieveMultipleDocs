package com.darpan.retrievemultipledocs.Model;

import com.google.firebase.firestore.Exclude;

import java.util.Map;

public class nestedNoteObject {
    private String documentId;
    private String title;
    private String description;
    private int priority;
    Map<String, Boolean> tags;
    /*so instead of list of string we would have key value pair*/
    /*tag{
    clever:true,
    old:true,
    have_hair:true

       }*/
    public nestedNoteObject() {
        //public no-arg constructor needed
    }

    public nestedNoteObject(String documentId, String title, int priority, Map<String, Boolean> tags) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.tags = tags;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Map<String, Boolean> getTags() {
        return tags;
    }

    public void setTags(Map<String, Boolean> tags) {
        this.tags = tags;
    }
}
