package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ParametersAreNonnullByDefault
public interface SpendRepository {
    @Nonnull
    SpendEntity create(SpendEntity spend);

    @Nonnull
    SpendEntity update(SpendEntity spend);

    @Nonnull
    CategoryEntity createCategory(CategoryEntity category);

    @Nonnull
    Optional<CategoryEntity> findCategoryById(UUID id);

    @Nonnull
    Optional<CategoryEntity> findCategoryByUserNameAndCategoryName(String userName, String categoryName);

    @Nonnull
    Optional<SpendEntity> findSpendById(UUID id);

    @Nonnull
    List<SpendEntity> findAllByUserNameAndDescription(String userName, String description);

    void remove(SpendEntity spend);

    void removeCategory(CategoryEntity category);

}
