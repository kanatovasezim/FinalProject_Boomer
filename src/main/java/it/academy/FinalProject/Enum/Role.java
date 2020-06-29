package it.academy.FinalProject.Enum;

public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_PUBLISHER("Publisher"),
    ROLE_ANALYTIC("Analytic"),
    ROLE_USER("User");

    private final String normalName;

    Role(String normalName){
        this.normalName = normalName;
    }

    public String getNormalName(){
        return normalName;
    }
}
