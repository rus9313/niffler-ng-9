package guru.qa.niffler.data.repository.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.jpa.EntityManagers.em;

@ParametersAreNonnullByDefault
public class SpendRepositoryHibernate implements SpendRepository {
    private static final Config CFG = Config.getInstance();

    private final EntityManager entityManager = em(CFG.userdataJdbcUrl());

    @Nonnull
    @Override
    public SpendEntity create(SpendEntity spend) {
        entityManager.joinTransaction();
        entityManager.persist(spend);
        return spend;
    }

    @Nonnull
    @Override
    public SpendEntity update(SpendEntity spend) {
        entityManager.joinTransaction();
        entityManager.merge(spend);
        return spend;
    }

    @Nonnull
    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        entityManager.joinTransaction();
        entityManager.persist(category);
        return category;
    }

    @Nonnull
    @Override
    public Optional<CategoryEntity> findCategoryById(UUID id) {
        return Optional.ofNullable(
                entityManager.find(CategoryEntity.class, id)
        );
    }

    @Nonnull
    @Override
    public Optional<CategoryEntity> findCategoryByUserNameAndCategoryName(String userName, String categoryName) {
        try {
            return Optional.of(
                    entityManager.createQuery("select c from CategoryEntity c where c.username =: username and c.name =: name", CategoryEntity.class)
                            .setParameter("username", userName)
                            .setParameter("name", categoryName)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    @Override
    public Optional<SpendEntity> findSpendById(UUID id) {
        return Optional.ofNullable(
                entityManager.find(SpendEntity.class, id)
        );
    }

    @Nonnull
    @Override
    public List<SpendEntity> findAllByUserNameAndDescription(String userName, String description) {
        return entityManager.createQuery("select s from SpendEntity s where s.username =: username and s.name =: name", SpendEntity.class)
                .setParameter("username", userName)
                .setParameter("name", description)
                .getResultList();
    }

    @Override
    public void remove(SpendEntity spend) {
        SpendEntity se = entityManager.find(SpendEntity.class, spend.getId());
        if (se != null) {
            entityManager.joinTransaction();
            entityManager.remove(se);
        }
    }

    @Override
    public void removeCategory(CategoryEntity category) {
        CategoryEntity ce = entityManager.find(CategoryEntity.class, category.getId());
        if (ce != null) {
            entityManager.joinTransaction();
            entityManager.remove(ce);
        }
    }
}
