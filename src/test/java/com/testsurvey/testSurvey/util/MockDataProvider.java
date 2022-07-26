package com.testsurvey.testSurvey.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.SneakyThrows;

public class MockDataProvider {

    @SneakyThrows
    public static String getInvalidQuestion() {
        return readFileAsString("src/test/java/resources/invalidQuestion.json");
    }

    @SneakyThrows
    public static String getInvalidQuestionDisable() {
        return readFileAsString("src/test/resources/samples/invalidQuestionDisable.json");
    }

    @SneakyThrows
    public static String getValidQuestion() {
        return readFileAsString("src/test/resources/samples//validQuestion.json");
    }

    @SneakyThrows
    public static String getValidQuestionDisable() {
        return readFileAsString("src/test/resources/samples/validQuestionDisable.json");
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
