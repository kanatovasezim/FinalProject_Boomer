package it.academy.FinalProject.Enum;

public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_PUBLISHER("Publisher"),
    ROLE_ANALYTIC("Analytic"),
    ROLE_USER("User");

    private final String name;

    Role(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
}
