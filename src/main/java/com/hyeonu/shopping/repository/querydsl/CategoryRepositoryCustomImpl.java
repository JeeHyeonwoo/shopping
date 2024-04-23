package com.hyeonu.shopping.repository.querydsl;

import com.hyeonu.shopping.domain.Category;
import com.hyeonu.shopping.domain.QCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CateogryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public void delete(Category category) {
        QCategory qCategory = QCategory.category;

        queryFactory
                .update(qCategory)
                .set(qCategory.parentId, category.getParentId())
                .where(qCategory.parentId.eq(category.getId()))
                .execute();

        em.flush();
        em.clear();
    }
}
