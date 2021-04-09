package ru.drvshr.fuel_consumption.services.dto;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.drvshr.fuel_consumption.config.AppConfig;
import ru.drvshr.fuel_consumption.enums.ECurrency;
import ru.drvshr.fuel_consumption.enums.EDistanceUnit;
import ru.drvshr.fuel_consumption.enums.EFuelType;
import ru.drvshr.fuel_consumption.enums.ERefuelingType;
import ru.drvshr.fuel_consumption.enums.EVolumeUnits;

/**
 * Описание одной заправки
 */
@SuppressWarnings("ClassWithTooManyFields") // Уж сколько есть
@Builder
@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Refueling {

    @Nullable
    @JsonProperty("REFUELING_ID")
    private Long id;

    /** Краткое описание заправки */
    @JsonProperty("DESCRIPTION")
    private String description;

    /** Дата заправки */
    @JsonProperty("DATE")
    @JsonSerialize(using = AppConfig.LocalDateSerializer.class)
    @JsonDeserialize(using = AppConfig.LocalDateDeserializer.class)
    private LocalDate date;

    /** показание одометра в {@link EDistanceUnit} */
    @JsonProperty("ODOMETER")
    private Integer odometer;

    /** предыдущие показания одометра */
    @JsonProperty("LAST_ODOMETER")
    private Integer lastOdometer;

    /** Объём заправки в {@link EVolumeUnits} */
    @JsonProperty("VOLUME")
    private Integer volume;

    /** Цена топлива в {@link ECurrency} */
    @JsonProperty("PRICE")
    private Integer price;

    /** Стоимость заправки в {@link ECurrency} */
    @JsonProperty("COST")
    private Integer cost;

    /** Тип топлива */
    @JsonProperty("FUEL_TYPE")
    private EFuelType fuelType;

    /** */
    @JsonProperty("BRAND")
    private String brand;

    /** Заметки по заправке */
    @JsonProperty("NOTES")
    private String notes;


    /** Единицы измерения информации */
    @JsonProperty("DISTANCE_UNIT")
    private EDistanceUnit distanceUnit = EDistanceUnit.KILOMETER;

    /** Вид заправки полная не полная и т.д. */
    @JsonProperty("REFUELING_TYPE")
    private ERefuelingType refuelingType = ERefuelingType.FULL;

    /** Единицы измерения объёма */
    @JsonProperty("VOLUME_UNITS")
    private EVolumeUnits volumeUnits = EVolumeUnits.LITER;

    @JsonProperty("CURRENCY")
    private ECurrency currency = ECurrency.RUB;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Refueling)) {
            return false;
        }
        Refueling other = (Refueling)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$id = this.getId();
        Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Object this$description = this.getDescription();
        Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        Object this$date = this.getDate();
        Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) {
            return false;
        }
        Object this$odometer = this.getOdometer();
        Object other$odometer = other.getOdometer();
        if (this$odometer == null ? other$odometer != null : !this$odometer.equals(other$odometer)) {
            return false;
        }
        Object this$lastOdometer = this.getLastOdometer();
        Object other$lastOdometer = other.getLastOdometer();
        if (this$lastOdometer == null ? other$lastOdometer != null : !this$lastOdometer.equals(other$lastOdometer)) {
            return false;
        }
        Object this$volume = this.getVolume();
        Object other$volume = other.getVolume();
        if (this$volume == null ? other$volume != null : !this$volume.equals(other$volume)) {
            return false;
        }
        Object this$price = this.getPrice();
        Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) {
            return false;
        }
        Object this$cost = this.getCost();
        Object other$cost = other.getCost();
        if (this$cost == null ? other$cost != null : !this$cost.equals(other$cost)) {
            return false;
        }
        Object this$fuelType = this.getFuelType();
        Object other$fuelType = other.getFuelType();
        if (this$fuelType == null ? other$fuelType != null : !this$fuelType.equals(other$fuelType)) {
            return false;
        }
        Object this$brand = this.getBrand();
        Object other$brand = other.getBrand();
        if (this$brand == null ? other$brand != null : !this$brand.equals(other$brand)) {
            return false;
        }
        Object this$notes = this.getNotes();
        Object other$notes = other.getNotes();
        if (this$notes == null ? other$notes != null : !this$notes.equals(other$notes)) {
            return false;
        }
        Object this$distanceUnit = this.getDistanceUnit();
        Object other$distanceUnit = other.getDistanceUnit();
        if (this$distanceUnit == null ? other$distanceUnit != null : !this$distanceUnit.equals(other$distanceUnit)) {
            return false;
        }
        Object this$refuelingType = this.getRefuelingType();
        Object other$refuelingType = other.getRefuelingType();
        if (this$refuelingType == null ? other$refuelingType != null : !this$refuelingType.equals(other$refuelingType)) {
            return false;
        }
        Object this$volumeUnits = this.getVolumeUnits();
        Object other$volumeUnits = other.getVolumeUnits();
        if (this$volumeUnits == null ? other$volumeUnits != null : !this$volumeUnits.equals(other$volumeUnits)) {
            return false;
        }
        Object this$currency = this.getCurrency();
        Object other$currency = other.getCurrency();
        return this$currency == null ? other$currency == null : this$currency.equals(other$currency);
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        Object $odometer = this.getOdometer();
        result = result * PRIME + ($odometer == null ? 43 : $odometer.hashCode());
        Object $lastOdometer = this.getLastOdometer();
        result = result * PRIME + ($lastOdometer == null ? 43 : $lastOdometer.hashCode());
        Object $volume = this.getVolume();
        result = result * PRIME + ($volume == null ? 43 : $volume.hashCode());
        Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        Object $cost = this.getCost();
        result = result * PRIME + ($cost == null ? 43 : $cost.hashCode());
        Object $fuelType = this.getFuelType();
        result = result * PRIME + ($fuelType == null ? 43 : $fuelType.hashCode());
        Object $brand = this.getBrand();
        result = result * PRIME + ($brand == null ? 43 : $brand.hashCode());
        Object $notes = this.getNotes();
        result = result * PRIME + ($notes == null ? 43 : $notes.hashCode());
        Object $distanceUnit = this.getDistanceUnit();
        result = result * PRIME + ($distanceUnit == null ? 43 : $distanceUnit.hashCode());
        Object $refuelingType = this.getRefuelingType();
        result = result * PRIME + ($refuelingType == null ? 43 : $refuelingType.hashCode());
        Object $volumeUnits = this.getVolumeUnits();
        result = result * PRIME + ($volumeUnits == null ? 43 : $volumeUnits.hashCode());
        Object $currency = this.getCurrency();
        result = result * PRIME + ($currency == null ? 43 : $currency.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {return other instanceof Refueling;}
}

