package com.darpan.retrievemultipledocs;

class Note1{
    private String documentId;
    private String title;
    private String description;
    private int priority;

    public Note1(String title, String description, int priority) {

    }

    public Note1(String documentId, String title, String description, int priority) {

        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

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
}
