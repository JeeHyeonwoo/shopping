package com.hyeonu.shopping.dto.type;

public enum RoleType {
    USER("ROLE_USER"),
    BRAND("ROLE_BRAND"),
    MANAGER("ROLE_MANAGER");

    private String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
