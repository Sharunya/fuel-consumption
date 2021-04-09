package ru.drvshr.fuel_consumption.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.drvshr.fuel_consumption.enums.ECurrency;
import ru.drvshr.fuel_consumption.enums.EDistanceUnit;
import ru.drvshr.fuel_consumption.enums.EFuelType;
import ru.drvshr.fuel_consumption.enums.ERefuelingType;
import ru.drvshr.fuel_consumption.enums.EVolumeUnits;

/**
 * Описание одной заправки
 */
@SuppressWarnings({"ClassWithTooManyFields" /*Уж сколько есть*/, "JpaDataSourceORMInspection"/*Живой БД не будет*/})
@Entity
@Table(name = "T_REFUELING")
@Builder
@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefuelingEntity {

    @Id
    @Column(name = "REFUELING_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "REFUELING_SEQ")
    @SequenceGenerator(name = "REFUELING_SEQ", sequenceName = "REFUELING_SEQ", allocationSize = 1)
    private Long id;
    /** Краткое описание заправки */
    @Column(name = "DESCRIPTION")
    private String description;
    /** Дата заправки */
    @Column(name = "DATE")
    private LocalDate date;
    /** показание одометра в {@link EDistanceUnit} */
    @Column(name = "ODOMETER")
    private Integer odometer;
    /** предыдущие показания одометра */
    @Column(name = "LAST_ODOMETER")
    private Integer lastOdometer;
    /** Объём заправки в {@link EVolumeUnits} */
    @Column(name = "VOLUME")
    private Integer volume;
    /** Цена топлива в {@link ECurrency} */
    @Column(name = "PRICE")
    private Integer price;
    /** Стоимость заправки в {@link ECurrency} */
    @Column(name = "COST")
    private Integer cost;
    /** Тип топлива */
    @Column(name = "FUEL_TYPE")
    private EFuelType fuelType;
    /**
     *
     */
    @Column(name = "BRAND")
    private String brand;
    /** Заметки по заправке */
    @Column(name = "NOTES")
    private String notes;
    /** Единицы измерения информации */
    @Column(name = "DISTANCE_UNIT")
    @Enumerated(EnumType.ORDINAL)
    private EDistanceUnit distanceUnit;
    /** Вид заправки полная не полная и т.д. */
    @Column(name = "REFUELING_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private ERefuelingType refuelingType;
    /** Единицы измерения объёма */
    @Column(name = "VOLUME_UNITS")
    @Enumerated(EnumType.ORDINAL)
    private EVolumeUnits volumeUnits;
    @Column(name = "CURRENCY")
    @Enumerated(EnumType.ORDINAL)
    private ECurrency currency;

}

