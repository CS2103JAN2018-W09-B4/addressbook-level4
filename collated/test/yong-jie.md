# yong-jie
###### /java/systemtests/SelectCardCommandSystemTest.java
``` java
public class SelectCardCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void select() {
        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the card list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCardCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_CARD);

        /* Case: select the last card in the card list -> selected */
        Index cardCount = Index.fromOneBased(getTypicalCards().size());
        command = SelectCardCommand.COMMAND_WORD + " " + cardCount.getOneBased();
        assertCommandSuccess(command, cardCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the card list -> selected */
        Index middleIndex = Index.fromOneBased(cardCount.getOneBased() / 2);
        command = SelectCardCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */

        /* Case: filtered card list, select index within bounds of address book and card list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredCardList().size());
        command = SelectCardCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* Case: filtered card list, select index within bounds of address book but out of bounds of card list
         * -> rejected
         */

        selectTag(Index.fromZeroBased(0));
        int invalidIndex = getModel().getAddressBook().getCardList().size();
        System.out.println(getModel().getFilteredCardList());
        assertCommandFailure(SelectCardCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_CARD_DISPLAYED_INDEX);


        /* ----------------------------------- Perform invalid select operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCardCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCardCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredCardList().size() + 1;
        assertCommandFailure(SelectCardCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCardCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCardCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected card.<br>
     * 4. {@code Model}, {@code Storage} and {@code CardListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_CARD_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getCardListPanel()
                .getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code CardListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### /java/seedu/address/logic/parser/ChangeThemeCommandParserTest.java
``` java
public class ChangeThemeCommandParserTest {
    private static final String SPACE = " ";
    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                COMMAND_WORD + SPACE + PREFIX_THEME + VALID_THEME_1,
                new ChangeThemeCommand(CORRESPONDING_THEME_INDEX_1));

        assertParseSuccess(parser,
                COMMAND_WORD + SPACE + PREFIX_THEME + VALID_THEME_2,
                new ChangeThemeCommand(CORRESPONDING_THEME_INDEX_2));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidField_failure() {
        final String expectedMessage = MESSAGE_INVALID_THEME;

        assertParseFailure(parser,
                COMMAND_WORD + SPACE + PREFIX_THEME + INVALID_THEME,
                expectedMessage);
    }

}
```
###### /java/seedu/address/logic/parser/SelectCardCommandParserTest.java
``` java
/**
 * Tests the parsing functionality of {@code SelectCardCommandParser}
 */
public class SelectCardCommandParserTest {

    private SelectCardCommandParser parser = new SelectCardCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCardCommand() {
        assertParseSuccess(parser, "1", new SelectCardCommand(INDEX_FIRST_CARD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseTheme_incorrectString_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTheme(Optional.of(INVALID_THEME));
    }

    @Test
    public void parseTheme_correctString_returnsIndex() throws Exception {
        Integer result = ParserUtil.parseTheme(Optional.of(VALID_THEME));
        assertEquals((Integer) 0, result);
    }
}
```
###### /java/seedu/address/logic/commands/ChangeThemeCommandTest.java
``` java
public class ChangeThemeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_changeTheme_success() {
        CommandResult result = new ChangeThemeCommand(0).execute();
        assertEquals(ChangeThemeCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeThemeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### /java/seedu/address/logic/commands/SelectCardCommandTest.java
``` java
public class SelectCardCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastCardIndex = Index.fromOneBased(model.getFilteredCardList().size());

        assertExecutionSuccess(INDEX_FIRST_CARD);
        assertExecutionSuccess(INDEX_THIRD_CARD);
        assertExecutionSuccess(lastCardIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        assertExecutionSuccess(INDEX_FIRST_CARD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        model.filterCardsByTag(new Tag(new Name("Hi")));

        Index outOfBoundsIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of the list of cards
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getCardList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_CARD);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_CARD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCardCommand} with the given {@code index}, and checks that {@code JumpToCardRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCardCommand selectCardCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectCardCommand.execute();
            assertEquals(String.format(SelectCardCommand.MESSAGE_SELECT_CARD_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToCardRequestEvent lastEvent = (JumpToCardRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCardCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCardCommand selectCardCommand = prepareCommand(index);

        try {
            selectCardCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCardCommand} with parameters {@code index}.
     */
    private SelectCardCommand prepareCommand(Index index) {
        SelectCardCommand selectCardCommand = new SelectCardCommand(index);
        selectCardCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectCardCommand;
    }
}
```
