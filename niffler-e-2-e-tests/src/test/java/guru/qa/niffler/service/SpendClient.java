package guru.qa.niffler.service;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ParametersAreNonnullByDefault
public interface SpendClient {

    @Nonnull
    SpendJson createSpend(SpendJson spend);

    @Nonnull
    SpendJson update(SpendJson spend);

    @Nonnull
    Optional<SpendJson> findSpendById(UUID id);

    @Nonnull
    List<SpendJson> findAllByUserName(String userName);

    void removeSpend(SpendJson spend);

    @Nonnull
    CategoryJson createCategory(CategoryJson category);

    void removeCategory(CategoryJson category);
}
