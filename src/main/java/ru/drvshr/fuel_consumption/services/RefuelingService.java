package ru.drvshr.fuel_consumption.services;

import java.sql.Date;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.Getter;
import lombok.ToString;
import ru.drvshr.fuel_consumption.model.RefuelingEntity;
import ru.drvshr.fuel_consumption.repositories.IRefuelingRepository;
import ru.drvshr.fuel_consumption.services.dto.AverageFuelConsumptionRequest;
import ru.drvshr.fuel_consumption.services.dto.Refueling;

/**
 *
 */
@ToString
@Service
public class RefuelingService {
    @Getter
    private final IRefuelingRepository refuelingRepository;

    public RefuelingService(IRefuelingRepository refuelingRepository) {this.refuelingRepository = refuelingRepository;}

    public Refueling saveRefueling(@Nullable Refueling refuelingReq) throws IllegalStateException, NotFoundException {
        Refueling refueling = Optional.ofNullable(refuelingReq).orElseThrow(() -> new IllegalStateException("Запрос пустой!"));
        RefuelingEntity entityFromBD;
        Long refuelingId = refueling.getId();
        if (refuelingId != null) {
            /* Проверяем есть-ли объект с таким ID в БД и по хорошему надо-бы сравнить с входящим, а вдруг не изменился */
            entityFromBD = getEntityFromBD(refuelingId);
        }
        RefuelingEntity entity = mappingRequestToRefuelingEntity(refueling);

        return mappingRefuelingEntityToRequest(refuelingRepository.save(entity));
    }

    public Refueling search(@Nullable Long refuelingId) throws NotFoundException {
        Long id = Optional.ofNullable(refuelingId).orElseThrow(() -> new IllegalStateException("ID отсутствует"));
        return mappingRefuelingEntityToRequest(getEntityFromBD(id));
    }

    public void delete(@Nullable Long refuelingId) throws EmptyResultDataAccessException {
        Long id = Optional.ofNullable(refuelingId).orElseThrow(() -> new IllegalStateException("ID отсутствует"));
        refuelingRepository.deleteById(id);
    }

    public List<Refueling> averageConsumption(@Nullable AverageFuelConsumptionRequest request) throws EmptyResultDataAccessException {
        AverageFuelConsumptionRequest requestOptional = Optional.ofNullable(request).orElseThrow(() -> new IllegalStateException("Запрос пустой!"));
        LocalDate dateFrom = Optional.ofNullable(requestOptional.getDateFrom()).orElse(LocalDate.MIN);
        LocalDate dateTo = Optional.ofNullable(requestOptional.getDateTo()).orElse(LocalDate.MAX);
        List<RefuelingEntity> refuelingEntities = refuelingRepository.averageConsumption(Date.valueOf(dateFrom), Date.valueOf(dateTo));
        return refuelingEntities //
                                 .stream() //
                                 .map(RefuelingService::mappingRefuelingEntityToRequest) //
                                 .collect(Collectors.toList());
    }

    public List<Refueling> getAll() {
        return refuelingRepository.findAll().stream().map(RefuelingService::mappingRefuelingEntityToRequest).collect(Collectors.toList());
    }

    private RefuelingEntity getEntityFromBD(Long refuelingId) throws NotFoundException {
        return refuelingRepository //
                                   .findById(refuelingId) //
                                   .orElseThrow(() -> new NotFoundException(MessageFormat.format("Заправка с id={0} отсутствует", refuelingId.toString())));
    }

    public static RefuelingEntity mappingRequestToRefuelingEntity(Refueling refueling) {
        return (new RefuelingEntity()).setId(refueling.getId())
                                      .setDescription(refueling.getDescription())
                                      .setDate(Date.valueOf(refueling.getDate()))
                                      .setOdometer(refueling.getOdometer())
                                      .setLastOdometer(refueling.getLastOdometer())
                                      .setVolume(refueling.getVolume())
                                      .setPrice(refueling.getPrice())
                                      .setCost(refueling.getCost())
                                      .setFuelType(refueling.getFuelType())
                                      .setBrand(refueling.getBrand())
                                      .setNotes(refueling.getNotes());
    }

    public static Refueling mappingRefuelingEntityToRequest(RefuelingEntity refuelingEntity) {
        return (new Refueling()).setId(refuelingEntity.getId())
                                .setDescription(refuelingEntity.getDescription())
                                .setDate(refuelingEntity.getDate().toLocalDate())
                                .setOdometer(refuelingEntity.getOdometer())
                                .setLastOdometer(refuelingEntity.getLastOdometer())
                                .setVolume(refuelingEntity.getVolume())
                                .setPrice(refuelingEntity.getPrice())
                                .setCost(refuelingEntity.getCost())
                                .setFuelType(refuelingEntity.getFuelType())
                                .setBrand(refuelingEntity.getBrand())
                                .setNotes(refuelingEntity.getNotes());
    }

}
