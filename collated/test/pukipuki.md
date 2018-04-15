# pukipuki
###### /java/seedu/address/logic/commands/ShowDueCommandTest.java
``` java
public class ShowDueCommandTest {
    private Model model;
    private LocalDateTime todaysDate;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        todaysDate = LocalDate.now().atStartOfDay();
    }

    @Test
    public void execute_correctMessage_success() {
        ShowDueCommand showDueCommand = prepareCommand(todaysDate);
        String expectedMessage = String.format(ShowDueCommand.MESSAGE_SUCCESS,
            todaysDate.toLocalDate().toString(), "");
        assertCommandSuccess(showDueCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_listsCorrectly_success() {
        ShowDueCommand showDueCommand = prepareCommand(todaysDate);
        ObservableList<Card> list = model.getFilteredCardList();
        showDueCommand.execute();
        assert(!list.isEmpty());

        model.showAllCards();
        showDueCommand = prepareCommand(todaysDate.minusYears(1L));
        showDueCommand.execute();
        assert(list.isEmpty());

        model.showAllCards();
        showDueCommand = prepareCommand(todaysDate.plusYears(1L));
        showDueCommand.execute();
        assert(!list.isEmpty());
    }

    /**
     * Returns a {@code ShowDueCommand} with parameters {@code date}.
     */
    private ShowDueCommand prepareCommand(LocalDateTime date) {
        ShowDueCommand showDueCommand = new ShowDueCommand(date);
        showDueCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return showDueCommand;
    }
}
```
###### /java/seedu/address/logic/commands/ScheduleCommandTest.java
``` java
public class ScheduleCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private LocalDateTime todaysDate;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        todaysDate = LocalDate.now().atStartOfDay();
    }

    @Test
    public void execute_noCardSelectedException_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        ScheduleCommand scheduleCommand = prepareCommand(todaysDate);
        scheduleCommand.executeUndoableCommand();
    }

    @Test
    public void execute_scheduleUpdate_success() throws Exception {
        model.showDueCards(LocalDate.now().atStartOfDay());
        ObservableList<Card> observableList = model.getFilteredCardList();
        Card selectedCard = observableList.get(0);
        model.setSelectedCard(selectedCard);
        LocalDateTime expectedDate = todaysDate.plusYears(1L);
        ScheduleCommand scheduleCommand = prepareCommand(expectedDate);
        scheduleCommand.executeUndoableCommand();
        LocalDateTime actualDate = selectedCard.getSchedule().getNextReview();
        assertTrue(actualDate.equals(todaysDate.plusYears(1L)));
    }

    @Test
    public void equals() {

        ScheduleCommand scheduleCommandOne = new ScheduleCommand(todaysDate);
        ScheduleCommand scheduleCommandTwo = new ScheduleCommand(todaysDate);

        // same object -> returns true
        assertTrue(scheduleCommandOne.equals(scheduleCommandOne));

        // different object, same value -> returns true
        assertTrue(scheduleCommandOne.equals(scheduleCommandTwo));

        // different object, same value -> returns true
        assertTrue(scheduleCommandTwo.equals(scheduleCommandOne));

        // different object, same values -> returns true
        ScheduleCommand scheduleCommandOneCopy = new ScheduleCommand(todaysDate);
        assertTrue(scheduleCommandOne.equals(scheduleCommandOneCopy));

        // different types -> returns false
        assertFalse(scheduleCommandOne.equals(1));

        // null -> returns false
        assertFalse(scheduleCommandOne.equals(null));

        ScheduleCommand scheduleCommandDifferent = new ScheduleCommand(todaysDate.plusYears(1L));

        // different card -> returns false
        assertFalse(scheduleCommandOne.equals(scheduleCommandDifferent));
    }

    /**
     * Returns a {@code ScheduleCommand} with parameters {@code date}.
     */
    private ScheduleCommand prepareCommand(LocalDateTime date) {
        ScheduleCommand scheduleCommand = new ScheduleCommand(date);
        scheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return scheduleCommand;
    }

}
```
###### /java/seedu/address/logic/commands/AnswerCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AnswerCommand}.
 */
public class AnswerCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private LocalDateTime todaysDate;
    private AnswerCommand answerCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        todaysDate = LocalDate.now().atStartOfDay();
        answerCommand = new AnswerCommand(0);
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    // error states

    @Test
    public void execute_noCardSelected_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        answerCommand.executeUndoableCommand();
    }

    @Test
    public void execute_answerCommand_noCardSelected() {
        assertCommandFailure(answerCommand, model, AnswerCommand.MESSAGE_CARD_NOT_SELECTED);
    }

    @Test
    public void execute_answerCommand_success() {
        for (int confidenceLevel = Schedule.VALID_MIN_CONFIDENCE_LEVEL;
             confidenceLevel <= Schedule.VALID_MAX_CONFIDENCE_LEVEL;
             confidenceLevel++) {
            commandRunner(confidenceLevel);
        }
    }

    @Test
    public void execute_underRange_failure() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        commandRunner(-1);
    }

    @Test
    public void execute_overRange_failure() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        commandRunner(3);
    }

    @Test
    public void execute_answer0_success() {
        ObservableList<Card> list = model.getFilteredCardList();
        model.showDueCards(todaysDate);
        int initialSize = list.size();
        commandRunner(0);
        int finalSize = list.size();
        assertEquals(initialSize, finalSize);
    }

    @Test
    public void execute_answer1_success() {
        ObservableList<Card> list = model.getFilteredCardList();
        model.showDueCards(todaysDate);
        int initialSize = list.size();
        commandRunner(1);
        int finalSize = list.size();
        assertEquals(initialSize, finalSize);
    }

    @Test
    public void execute_answer2_success() {
        ObservableList<Card> list = model.getFilteredCardList();
        model.showDueCards(todaysDate);
        int initialSize = list.size();
        commandRunner(2);
        int finalSize = list.size();
        assertEquals(initialSize - 1, finalSize);
    }

    /**
     * Returns a {@code AnswerCommand} with parameters {@code date}.
     */
    private AnswerCommand prepareCommand(int confidenceLevel) {
        AnswerCommand answerCommand = new AnswerCommand(confidenceLevel);
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return answerCommand;
    }

    /**
     * Runs the answer command, used very often.
     */
    private void commandRunner(int confidenceLevel) {
        model.showDueCards(LocalDate.now().atStartOfDay());
        AnswerCommand answerCommand = prepareCommand(confidenceLevel);
        Card selectedCard = model.getFilteredCardList().get(0);
        model.setSelectedCard(selectedCard);
        String expectedMessage = AnswerCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(answerCommand, model, expectedMessage, model);
    }
}
```
###### /java/seedu/address/logic/parser/AnswerCommandParserTest.java
``` java
public class AnswerCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_nonsenseArguments_throwIllegalArgumentException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertParseSuccess(parser, " " + PREFIX_CONFIDENCE
            + PREFIX_CONFIDENCE, new AnswerCommand(0));
    }

    @Test
    public void parse_outOfRangeMessage_failure() {
        assertParseFailure(parser, " " + PREFIX_CONFIDENCE
            + "99", "Confidence Levels should only be 0, 1 or 2");
    }

    @Test
    public void parse_confidenceLevel_success() {
        assertParseSuccess(parser, " " + PREFIX_CONFIDENCE
            + VALID_CONFIDENCE_LEVEL_0, new AnswerCommand(0));
        assertParseSuccess(parser, " " + PREFIX_CONFIDENCE
            + VALID_CONFIDENCE_LEVEL_1, new AnswerCommand(1));
        assertParseSuccess(parser, " " + PREFIX_CONFIDENCE
            + VALID_CONFIDENCE_LEVEL_2, new AnswerCommand(2));
    }
}
```
###### /java/seedu/address/logic/parser/ScheduleCommandParserTest.java
``` java
public class ScheduleCommandParserTest {
    private LocalDateTime todaysDate;
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Before
    public void setUp() {
        todaysDate = LocalDate.now().atStartOfDay();
    }

    @Test
    public void parse_allFieldsNotPresentCard_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE, new ScheduleCommand(todaysDate));
    }

    @Test
    public void parse_allFieldsEmptyPermutation_success() {
        String[] powerSetString = preparePowerSetString(LIST_DAY_MONTH_YEAR, true);

        for (String each : powerSetString) {
            assertParseSuccess(parser, each, new ScheduleCommand(todaysDate));
        }
    }

    @Test
    public void parse_allFieldsValidPermutation_success() {
        String[] powerSetString = preparePowerSetString(LIST_DAY_MONTH_YEAR, true);
        LocalDateTime[] powerSetDateTime = preparePowerSetDateTime(LIST_VALID_DAY_MONTH_YEAR);

        for (int i = 0; i < powerSetString.length; i++) {
            assertParseSuccess(parser, powerSetString[i], new ScheduleCommand(powerSetDateTime[i]));
        }
    }

    @Test
    public void parse_invalidFields_failure() {
        String[] powerSetString = preparePowerSetString(LIST_PREFIX_RUBBISH, true);
        String expectedMessage = MESSAGE_INVALID_NUMBER;
        for (String each : powerSetString) {
            if (each.equals("")) {
                continue;
            }
            assertParseFailure(parser, each, expectedMessage);
        }
    }

    @Test
    public void parse_february29_failure() {
        try {
            int year = LocalDate.now().getYear();
            LocalDate date = LocalDate.of(year, 2, 29);
        } catch (DateTimeException dte) {
            String expectedMessage = dte.getMessage();
            assertParseFailure(parser, INVALID_29FEBRUARY, expectedMessage);
        }
    }

    @Test
    public void parse_february30_failure() {
        try {
            int year = LocalDate.now().getYear();
            LocalDate date = LocalDate.of(year, 2, 30);
        } catch (DateTimeException dte) {
            String expectedMessage = dte.getMessage();
            assertParseFailure(parser, INVALID_30FEBRUARY, expectedMessage);
        }
    }

    @Test
    public void parse_march32_failure() {
        String expectedMessage = MESSAGE_DAY_CONSTRAINTS;
        assertParseFailure(parser, INVALID_32MARCH, expectedMessage);
    }

    @Test
    public void parse_april31_failure() {
        try {
            int year = LocalDate.now().getYear();
            LocalDate date = LocalDate.of(year, 4, 31);
        } catch (DateTimeException dte) {
            String expectedMessage = dte.getMessage();
            assertParseFailure(parser, INVALID_31APRIL, expectedMessage);
        }
    }

    @Test
    public void parse_dayOfMonth32_failure() {
        String expectedMessage = MESSAGE_DAY_CONSTRAINTS;
        assertParseFailure(parser, INVALID_32DAY_OF_MONTH, expectedMessage);
    }

    @Test
    public void parse_dayOfMonth0_failure() {
        String expectedMessage = MESSAGE_DAY_CONSTRAINTS;
        assertParseFailure(parser, INVALID_0DAY_OF_MONTH, expectedMessage);
    }
}
```
###### /java/seedu/address/logic/parser/ShowDueCommandParserTest.java
``` java
public class ShowDueCommandParserTest {
    private LocalDateTime todaysDate;
    private ShowDueCommandParser parser = new ShowDueCommandParser();

    @Before
    public void setUp() {
        todaysDate = LocalDate.now().atStartOfDay();
    }

```
###### /java/seedu/address/logic/parser/ShowDueCommandParserTest.java
``` java
    @Test
    public void parse_allFieldsNotPresentCard_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE, new ShowDueCommand(todaysDate));
    }

    @Test
    public void parse_allFieldsEmptyPermutation_success() {
        String[] powerSetString = preparePowerSetString(LIST_DAY_MONTH_YEAR, true);

        for (String each : powerSetString) {
            assertParseSuccess(parser, each, new ShowDueCommand(todaysDate));
        }
    }

    @Test
    public void parse_allFieldsValidPermutation_success() {
        String[] powerSetString = preparePowerSetString(LIST_DAY_MONTH_YEAR, true);
        LocalDateTime[] powerSetDateTime = preparePowerSetDateTime(LIST_VALID_DAY_MONTH_YEAR);

        for (int i = 0; i < powerSetString.length; i++) {
            assertParseSuccess(parser, powerSetString[i], new ShowDueCommand(powerSetDateTime[i]));
        }
    }

    @Test
    public void parse_invalidFields_failure() {
        String[] powerSetString = preparePowerSetString(LIST_PREFIX_RUBBISH, true);
        String expectedMessage = MESSAGE_INVALID_NUMBER;
        for (String each : powerSetString) {
            if (each.equals("")) {
                continue;
            }
            assertParseFailure(parser, each, expectedMessage);
        }
    }

    @Test
    public void parse_february29_failure() {
        try {
            int year = LocalDate.now().getYear();
            LocalDate date = LocalDate.of(year, 2, 29);
        } catch (DateTimeException dte) {
            String expectedMessage = dte.getMessage();
            assertParseFailure(parser, INVALID_29FEBRUARY, expectedMessage);
        }
    }

    @Test
    public void parse_february30_failure() {
        try {
            int year = LocalDate.now().getYear();
            LocalDate date = LocalDate.of(year, 2, 30);
        } catch (DateTimeException dte) {
            String expectedMessage = dte.getMessage();
            assertParseFailure(parser, INVALID_30FEBRUARY, expectedMessage);
        }
    }

    @Test
    public void parse_march32_failure() {
        String expectedMessage = MESSAGE_DAY_CONSTRAINTS;
        assertParseFailure(parser, INVALID_32MARCH, expectedMessage);
    }

    @Test
    public void parse_april31_failure() {
        try {
            int year = LocalDate.now().getYear();
            LocalDate date = LocalDate.of(year, 4, 31);
        } catch (DateTimeException dte) {
            String expectedMessage = dte.getMessage();
            assertParseFailure(parser, INVALID_31APRIL, expectedMessage);
        }
    }

    @Test
    public void parse_dayOfMonth32_failure() {
        String expectedMessage = MESSAGE_DAY_CONSTRAINTS;
        assertParseFailure(parser, INVALID_32DAY_OF_MONTH, expectedMessage);
    }

    @Test
    public void parse_dayOfMonth0_failure() {
        String expectedMessage = MESSAGE_DAY_CONSTRAINTS;
        assertParseFailure(parser, INVALID_0DAY_OF_MONTH, expectedMessage);
    }

}
```
###### /java/seedu/address/model/card/ScheduleTest.java
``` java
public class ScheduleTest {
    private static final double delta = 0.000001;
    private Schedule schedule;
    private LocalDateTime todaysDate;

    @Before
    public void setUp() {
        todaysDate = LocalDate.now().atStartOfDay();
        schedule = new Schedule();
    }

    @Test
    public void getNextReview_success() {
        assertEquals(schedule.getNextReview(), todaysDate);
    }

    @Test
    public void setNextReview_success() {
        LocalDateTime expectedDate = todaysDate.plusYears(1L);
        schedule.setNextReview(expectedDate);
        assertEquals(schedule.getNextReview(), expectedDate);
    }

    @Test
    public void getLastInterval_success() {
        int actual = schedule.getLastInterval();
        assertEquals(actual, Schedule.INITIAL_LAST_INTERVAL);
    }

    @Test
    public void getEasingFactor_success() {
        double actual = schedule.getEasingFactor();
        assertEquals(actual, Schedule.INITIAL_EASING_FACTOR, delta);
    }

    @Test
    public void getHistoricalEasingFactor_success() {
        double actual = schedule.getHistoricalEasingFactor();
        assertEquals(actual, Schedule.INITIAL_HISTORICAL_EASING_FACTOR, delta);
    }

    @Test
    public void getLearningPhase_success() {
        int actual = schedule.getLearningPhase();
        assertEquals(actual, Schedule.INITIAL_LEARNING_PHASE);
    }

    @Test
    public void feedback_getSuccessRate() {
        Schedule s = new Schedule();
        s.feedback(true);
        s.feedback(false);
        s.feedback(true);
        assertEquals(2.0 / 4.0, s.getSuccessRate(), delta);
    }

    @Test
    public void feedback_learningPhaseTest() {
        Schedule s = new Schedule();
        int learningPhase = s.getLearningPhase();
        for (int i = 0; i < learningPhase - 1; i++) {
            s.feedback(true);
            assertEquals(1, s.getLastInterval());
        }
    }

    @Test
    public void feedback_getEasingFactor() {
        Schedule s = new Schedule();
        int learningPhase = s.getLearningPhase();
        for (int i = 0; i < learningPhase - 1; i++) {
            s.feedback(true);
            assertEquals(1, s.getLastInterval());
        }
        s.feedback(true);
        assertEquals(1.1, s.getEasingFactor(), delta);
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        assertEquals(1.3569619443199672, s.getEasingFactor(), delta);
        s.feedback(false);
        s.feedback(false);
        s.feedback(false);
        s.feedback(false);
        assertEquals(0.28007138289996014, s.getEasingFactor(), delta);
    }

    @Test
    public void feedback_algoPositive() {
        Schedule s = new Schedule();
        int learningPhase = s.getLearningPhase();
        for (int i = 0; i < learningPhase - 1; i++) {
            s.feedback(true);
            assertEquals(1, s.getLastInterval());
        }
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        assertEquals(1.1597147845723643, s.getEasingFactor(), delta);
    }

    @Test
    public void feedback_algoNegative() {
        Schedule s = new Schedule();
        int learningPhase = s.getLearningPhase();
        for (int i = 0; i < learningPhase - 1; i++) {
            s.feedback(true);
            assertEquals(1, s.getLastInterval());
        }
        s.feedback(false);
        assertEquals(0.3048048297281299, s.getEasingFactor(), delta);
    }
}
```
