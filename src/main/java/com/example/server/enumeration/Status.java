package com.example.server.enumeration;

public enum Status {
    SERVER_UP("SERVER_UO"),
    SERVER_DOWN("SERVER_DOWN");
    private final String status;

    Status(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
}
