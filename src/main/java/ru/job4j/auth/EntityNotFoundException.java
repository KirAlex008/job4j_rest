package ru.job4j.auth;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String id) {
        super("entity not found" + id);
    }

}
