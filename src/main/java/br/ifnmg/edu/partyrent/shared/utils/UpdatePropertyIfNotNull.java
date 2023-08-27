package br.ifnmg.edu.partyrent.shared.utils;

import java.util.function.Consumer;

public class UpdatePropertyIfNotNull {
    public <T> void execute(T newValue, Consumer<T> updateFunction) {
        if (newValue != null) {
            updateFunction.accept(newValue);
        }
    }
}
