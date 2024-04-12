package kz.kopanitsa.model;

import kz.kopanitsa.model.trainingType.TrainingType;
import lombok.*;

import java.util.HashMap;

/**
 * Класс, представляющий тренировку.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Training {

    /**
     * Дата тренировки в формате "yyyy-MM-dd" (String).
     */
    private String date;

    /**
     * Тип тренировки.
     */
    private TrainingType trainingType;

    /**
     * Продолжительность тренировки в минутах.
     */
    private int durationMinutes;

    /**
     * Количество сожженных калорий.
     */
    private int caloriesBurned;

    /**
     * Дополнительная информация о тренировке в виде пар "ключ"-"значение".
     */
    private HashMap<String, Integer> additionalInfo;
}