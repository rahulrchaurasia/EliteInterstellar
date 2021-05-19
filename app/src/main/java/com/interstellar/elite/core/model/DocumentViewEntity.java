package com.interstellar.elite.core.model;

public class DocumentViewEntity {
    /**
     * doc_id : 7
     * document_name : Pollution under control (PUC) certificate
     * path :
     */

    private int doc_id;
    private String document_name;
    private String path;


    int docstatus;

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(int docstatus) {
        this.docstatus = docstatus;
    }
}