package kz.kopanitsa.model.trainingType;

import lombok.*;

/**
 * Класс, представляющий кардио тренировку.
 *
 * Этот класс наследуется от класса TrainingType и представляет собой конкретный тип тренировки.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class YogaTraining extends TrainingType {
    /**
     * Название типа тренировки.
     */
    private String trainingTypeName = "Йога";

    /**
     * Получает название типа тренировки.
     *
     * @return название типа тренировки
     * @see TrainingType#getTrainingTypeName()
     * Реализация абстрактного метода из класса TrainingType.
     */
    @Override
    public String getTrainingTypeName() {
        return trainingTypeName;
    }
}
