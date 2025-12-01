package guru.qa.niffler.service;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpendClient {
    SpendJson createSpend(SpendJson spend);

    SpendJson update(SpendJson spend);

    Optional<SpendJson> findSpendById(UUID id);

    List<SpendJson> findAllByUserName(String userName);

    void removeSpend(SpendJson spend);

    CategoryJson createCategory(CategoryJson category);

    void removeCategory(CategoryJson category);
}
