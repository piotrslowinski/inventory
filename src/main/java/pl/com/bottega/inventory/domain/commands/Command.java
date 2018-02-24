package pl.com.bottega.inventory.domain.commands;

public interface Command {

    default void validate(Validatable.ValidationErrors validationErrors) {

    }

    default void validatePresence(Validatable.ValidationErrors errors, String field, String value) {
        if (value == null || value.trim().length() == 0) {
            errors.add(field, "can't be blank");
        }
    }

    default void validatePresence(Validatable.ValidationErrors errors, String field, Object value) {
        if (value == null) {
            errors.add(field, "can't be blank");
        }
    }

}
