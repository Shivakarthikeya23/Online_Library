package com.example.onlinelibrary;

public class uploadPdf {
    public String name;
    public String uri;

    public uploadPdf() {
    }

    public uploadPdf(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }



    public String getUri() {
        return uri;
    }

}
