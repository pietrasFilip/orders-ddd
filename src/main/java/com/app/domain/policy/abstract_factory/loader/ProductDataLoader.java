package com.app.domain.policy.abstract_factory.loader;


import com.app.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductDataLoader extends DataLoader<List<ProductEntity>> {
}
