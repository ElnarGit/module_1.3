package org.elnar.crudapp.repository.gson;

import java.util.List;
import java.util.function.Function;

public class IdGenerated {
    public static <T> long generatedNextId(List<T> items, Function<T, Long> idExtractor) {
        return items.stream()
                .mapToLong(idExtractor::apply)
                .max().orElse(0) + 1;
    }
}
