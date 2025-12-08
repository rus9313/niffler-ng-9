package guru.qa.niffler.utils;

import com.github.javafaker.Faker;

import javax.annotation.Nonnull;

public class RandomDataUtils {
    private static final Faker faker = new Faker();

    @Nonnull
    public static String randomUsername() {
        return faker.name().username();
    }

    @Nonnull
    public static String randomCategoryName() {
        return "Category " + faker.funnyName().name();
    }

    @Nonnull
    public static String randomName() {
        return faker.name().name();
    }

    @Nonnull
    public static String randomSurname() {
        return faker.name().lastName();
    }

    @Nonnull
    public static String randomSentence(int wordsCount) {
        return faker.lorem().sentence(wordsCount);
    }

    @Nonnull
    public static String randomString(int len1, int len2) {
        return faker.lorem().characters(len1, len2);
    }
}