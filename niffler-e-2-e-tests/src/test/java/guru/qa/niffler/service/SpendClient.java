package guru.qa.niffler.service;

import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.SpendJson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpendClient {
    SpendJson createSpend(SpendJson spend);

    Optional<SpendJson> findSpendById(UUID id);

    List<SpendEntity> findAllByUserName(String userName);

    void removeSpend(SpendJson spend);
}
