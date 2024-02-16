package com.burak.backendproject.initialwork.entity.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student"),
    MANAGER("Dean"),
    ASSISTANT_MANAGER("ViceDean");

    public final String name;
    RoleType(String name){
        this.name = name;
    }

}
