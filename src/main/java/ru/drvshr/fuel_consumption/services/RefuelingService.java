package ru.drvshr.fuel_consumption.services;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.Getter;
import lombok.ToString;
import ru.drvshr.fuel_consumption.model.RefuelingEntity;
import ru.drvshr.fuel_consumption.repositories.IRefuelingRepository;
import ru.drvshr.fuel_consumption.services.dto.Refueling;

/**
 *
 */
@ToString
@Service
public class RefuelingService {
    @Autowired
    @Getter
    private IRefuelingRepository refuelingRepository;

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
                                      .setDate(refueling.getDate())
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
                                .setDate(refuelingEntity.getDate())
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
