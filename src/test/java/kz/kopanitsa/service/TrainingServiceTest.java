package kz.kopanitsa.service;

import kz.kopanitsa.model.Training;
import kz.kopanitsa.model.User;
import kz.kopanitsa.model.trainingType.TrainingType;
import kz.kopanitsa.model.trainingType.YogaTraining;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.*;

public class TrainingServiceTest {
    @Mock
    private User mockedUser;
    @Mock
    private Training mockedTraining;
    private Map<User, List<Training>> userTrainingMap;
    @InjectMocks
    private TrainingService testTrainingService;

    @BeforeEach
    void setUp() {
        mockedUser = mock(User.class);
        mockedTraining = mock(Training.class);
        userTrainingMap = new HashMap<>();
        testTrainingService = new TrainingService(userTrainingMap);
    }

    @Test
    void addTrainingTest_SuccessfullyAdded(){
        testTrainingService.addTraining(mockedUser, mockedTraining);
        Assertions.assertEquals(1, userTrainingMap.size());
        Assertions.assertEquals(mockedTraining.toString(), userTrainingMap.get(mockedUser).get(0).toString());
    }

    @Test
    void updateTrainingTest_SuccessfullyAdded(){
        when(mockedTraining.getDate()).thenReturn("11-04-2024");
        when(mockedTraining.getTrainingType()).thenReturn(new YogaTraining());
        testTrainingService.addTraining(mockedUser, mockedTraining);
        List<Training> userTrainings = testTrainingService.getUserTrainingMap().get(mockedUser);
        Training existingTraining = userTrainings.get(0);
        Assertions.assertEquals(mockedTraining.getDate(), existingTraining.getDate());
        Assertions.assertEquals(mockedTraining.getTrainingType(), existingTraining.getTrainingType());
        testTrainingService.updateTraining(mockedUser, mockedTraining);
        Assertions.assertEquals(mockedTraining.toString(), userTrainingMap.get(mockedUser).get(0).toString());
    }

    @Test
    void removeTrainingTest_SuccessfullyAdded() {
        testTrainingService.addTraining(mockedUser, mockedTraining);
        testTrainingService.removeTraining(mockedUser, mockedTraining);
        List<Training> userTrainings = testTrainingService.getUserTrainingMap().get(mockedUser);
        Assertions.assertTrue(userTrainings.isEmpty());
    }

    @Test
    void getUserTrainingsTest_SuccessfullyAdded() {
        TrainingType trainingType = new YogaTraining();
        when(mockedTraining.getTrainingType()).thenReturn(trainingType);
        testTrainingService.addTraining(mockedUser, mockedTraining);
        String result = testTrainingService.getUserTrainings(mockedUser);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.length() > 0);
    }

    @Test
    void getUserTrainingsByTypeTest_SuccessfullyAdded() {
        TrainingType trainingType = new YogaTraining();
        String type = trainingType.toString();
        String result = testTrainingService.getUserTrainingsByType(mockedUser, type);
        Assertions.assertNotNull(result);
    }

    @Test
    void getUserTrainingsInDateRangeTest_SuccessfullyAdded() {
        String start = "01-04-2024";
        String end = "11-04-2014";
        String result = testTrainingService.getUserTrainingsInDateRange(mockedUser, start, end);
        Assertions.assertNotNull(result);
    }

    @Test
    void getAdditionalInfoStatisticsTest_SuccessfullyAdded() {
        TrainingType trainingType = new YogaTraining();
        String type = trainingType.toString();
        String result = testTrainingService.getAdditionalInfoStatistics(mockedUser, type);
        Assertions.assertNotNull(result);
    }

    @Test
    void getAllUserTrainingsTest__SuccessfullyAdded() {
        String result = testTrainingService.getAllUserTrainings();
        Assertions.assertNotNull(result);
    }
}
