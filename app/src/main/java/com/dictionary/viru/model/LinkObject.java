package com.dictionary.viru.model;

public class LinkObject {

    public Href self;
    public Href first;
    public Href prev;
    public Href next;
    public Href last;

    public class Href {
        public String href;
    }
}
