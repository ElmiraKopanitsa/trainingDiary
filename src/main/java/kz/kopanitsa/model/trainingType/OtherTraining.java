package kz.kopanitsa.model.trainingType;

import lombok.*;

/**
 * Класс, представляющий тренировку, тип которой зависит от названия,
 * заданного пользователем.
 *
 * Этот класс наследуется от класса TrainingType и представляет собой конкретный тип тренировки.
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class OtherTraining extends TrainingType {

    /**
     * Название типа тренировки, которое задается пользователем.
     */
    private String trainingTypeName;

    /**
     * Конструктор класса OtherTraining.
     *
     * Создает новый объект OtherTraining с указанным названием типа тренировки.
     *
     * @param trainingTypeName название типа тренировки
     */
    public OtherTraining(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

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
