package ru.drvshr.fuel_consumption.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ru.drvshr.fuel_consumption.model.RefuelingEntity;

/**
 *
 */
@Repository
public interface IRefuelingRepository extends CrudRepository<RefuelingEntity, Long>, JpaRepository<RefuelingEntity, Long>, PagingAndSortingRepository<RefuelingEntity, Long>, JpaSpecificationExecutor<RefuelingEntity> {

}
