package ru.drvshr.fuel_consumption.services.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.drvshr.fuel_consumption.config.AppConfig;

/**
 * Запрос среднего значения за период
 */
@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AverageFuelConsumptionRequest {
    @JsonProperty("DATE_FROM")
    @JsonSerialize(using = AppConfig.LocalDateSerializer.class)
    @JsonDeserialize(using = AppConfig.LocalDateDeserializer.class)
    LocalDate dateFrom;
    @JsonProperty("DATE_TO")
    @JsonSerialize(using = AppConfig.LocalDateSerializer.class)
    @JsonDeserialize(using = AppConfig.LocalDateDeserializer.class)
    LocalDate dateTo;
}
