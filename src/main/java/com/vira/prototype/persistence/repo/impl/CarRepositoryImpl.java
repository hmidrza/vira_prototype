package com.vira.prototype.persistence.repo.impl;

import com.vira.prototype.persistence.model.Car;
import com.vira.prototype.persistence.repo.infc.CarRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CarRepositoryImpl implements CarRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List findAll() {
        return entityManager.createNativeQuery(" select * from car c ",Car.class)
                .getResultList();
    }
}
