package ru.drvshr.fuel_consumption.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.drvshr.fuel_consumption.model.RefuelingEntity;

/**
 *
 */
@Repository
public interface IRefuelingRepository extends CrudRepository<RefuelingEntity, Long>, JpaRepository<RefuelingEntity, Long>, PagingAndSortingRepository<RefuelingEntity, Long>, JpaSpecificationExecutor<RefuelingEntity> {

    @Query("select p from RefuelingEntity p where p.date between :dateFrom and :dateTo")
    List<RefuelingEntity> averageConsumption(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
