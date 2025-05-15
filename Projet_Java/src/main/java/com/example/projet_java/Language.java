package com.example.projet_java;

public class Language {
    private int id;
    private String name;

    public Language() {}

    public Language(String name) {
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}