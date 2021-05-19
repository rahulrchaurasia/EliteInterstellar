package com.interstellar.elite.core.model;

public class DocProductEnity {
    /**
     * doc_id : 3
     * document_name : Form 29 - Declaration of seller
     * document_description :
     */

    private String doc_id;
    private String document_name;
    private String document_description;


    private String documenturl;

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getDocument_description() {
        return document_description;
    }

    public void setDocument_description(String document_description) {
        this.document_description = document_description;
    }

    public String getDocumenturl() {
        return documenturl;
    }

    public void setDocumenturl(String documenturl) {
        this.documenturl = documenturl;
    }

}