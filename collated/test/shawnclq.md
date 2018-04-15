# shawnclq
###### /java/seedu/address/logic/commands/EditCardCommandTest.java
``` java
    @Test
    public void execute_frontBackSpecifiedUnfilteredList_success() throws Exception {
        Card editedCard = new CardBuilder().build();
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(0), editedCard);
        editedCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        // To check whether card ID has changed
        assertEqualCardId(targetCard, editedCard);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mismatchedCardsToFillBlanksCard_failure() throws Exception {
        FillBlanksCard editedCard = new FillBlanksCardBuilder().build();
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_MISMATCHED_CARDS,
                targetCard.getType(), editedCard.getType());

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_mismatchedCardsToMcqCard_failure() throws Exception {
        McqCard editedCard = new McqCardBuilder().build();
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard)
                .withOptions(editedCard.getOptions()).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_MISMATCHED_CARDS,
                targetCard.getType(), editedCard.getType());

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_mismatchedFillBlanksCardsToMcqCard_failure() throws Exception {
        McqCard editedCard = new McqCardBuilder().build();
        FillBlanksCard targetCard = (FillBlanksCard) modelWithFillBlanksCards.getFilteredCardList()
                .get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard)
                .withOptions(editedCard.getOptions()).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);
        editCommand.setData(modelWithFillBlanksCards, new CommandHistory(), new UndoRedoStack());


        String expectedMessage = String.format(EditCardCommand.MESSAGE_MISMATCHED_CARDS,
                targetCard.getType(), editedCard.getType());

        assertCommandFailure(editCommand, modelWithFillBlanksCards, expectedMessage);
    }

    @Test
    public void execute_mismatchedFillBlanksCardsToCard_failure() throws Exception {
        Card editedCard = new CardBuilder().build();
        FillBlanksCard targetCard = (FillBlanksCard) modelWithFillBlanksCards.getFilteredCardList()
                .get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);
        editCommand.setData(modelWithFillBlanksCards, new CommandHistory(), new UndoRedoStack());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_MISMATCHED_CARDS,
                targetCard.getType(), editedCard.getType());

        assertCommandFailure(editCommand, modelWithFillBlanksCards, expectedMessage);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        CardBuilder cardInList = new CardBuilder(lastCard);
        Card editedCard = cardInList.withFront(VALID_NAME_COMSCI).withId(lastCard.getId().toString())
                .build();

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withFront(VALID_NAME_COMSCI)
                .build();
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateCard(lastCard, editedCard);

        // To check whether card ID has changed
        assertEqualCardId(lastCard, editedCard);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
```
###### /java/seedu/address/logic/commands/EditCardCommandTest.java
``` java
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, new EditCardCommand.EditCardDescriptor());
        Card editedCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // To check whether card ID has changed
        assertEqualCardId(targetCard, editedCard);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        Card cardInFilteredList = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        Card editedCard = new CardBuilder(cardInFilteredList).withFront(VALID_NAME_COMSCI).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder().withFront(VALID_NAME_COMSCI).build());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(0), editedCard);

        // To check whether card ID has changed
        assertEqualCardId(cardInFilteredList, editedCard);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCardUnfilteredList_failure() {
        Card firstCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(firstCard).build();
        EditCardCommand editCommand = prepareCommand(INDEX_SECOND_CARD, descriptor);

        assertCommandFailure(editCommand, model, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_duplicateCardFilteredList_failure() {
        // edit card in filtered list into a duplicate in address book
        Card cardInList = model.getAddressBook().getCardList().get(INDEX_SECOND_CARD.getZeroBased());
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder(cardInList).build());

        assertCommandFailure(editCommand, model, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_invalidCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withFront(VALID_NAME_COMSCI).build();
        EditCardCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCardIndexFilteredList_failure() {
        model.filterCardsByTag(new Tag(new Name("Name")));
        Index outOfBoundIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCardList().size());

        EditCardCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditCardDescriptorBuilder().withFront(VALID_NAME_COMSCI).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Card editedCard = new CardBuilder().build();
        Card cardToEdit = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard)
                .withUuid(cardToEdit.getId()).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Card newCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        // To check whether card ID has changed
        assertEqualCardId(cardToEdit, newCard);

        // edit -> first card edited
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered card list to show all cards
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first card edited again
        expectedModel.updateCard(cardToEdit, editedCard);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withFront(VALID_NAME_COMSCI).build();
        EditCardCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Card} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited card in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the card object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCardEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Card editedCard = new CardBuilder().withFront("Jethro Kuan").build();
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);
        Card newCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // To check whether card ID has changed
        assertEqualCardId(targetCard, newCard);

        Tag tag = model.getFilteredTagList().get(0);
        model.filterCardsByTag(tag);

        Card cardToEdit = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        // edit -> edits second card in unfiltered card list / first card in filtered card list
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered card list to show all cards
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateCard(cardToEdit, editedCard);
        assertNotEquals(model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased()), cardToEdit);
        // redo -> edits same second card in unfiltered card list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditCardCommand standardCommand = prepareCommand(INDEX_FIRST_CARD, CS2101_CARD);

        // same values -> returns true
        EditCardCommand.EditCardDescriptor copyDescriptor = new EditCardCommand.EditCardDescriptor(CS2101_CARD);
        EditCardCommand commandWithSameValues = prepareCommand(INDEX_FIRST_CARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_SECOND_CARD, CS2101_CARD)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_FIRST_CARD, CS2103T_CARD)));
    }

    /**
     * Returns an {@code EditCardCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCardCommand prepareCommand(Index index, EditCardDescriptor descriptor) {
        EditCardCommand editCommand = new EditCardCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

}
```
###### /java/seedu/address/logic/parser/AddCardCommandParserTest.java
``` java
    @Test
    public void parse_allFieldsPresentCard_success() {
        Card expectedCard = new CardBuilder().withFront(VALID_FRONT_CS2103T_CARD)
                .withBack(VALID_BACK_CS2103T_CARD).build();
        McqCard expectedMcqCard = (McqCard) new McqCardBuilder().resetOptions()
                .addOption(VALID_MCQ_OPTION_1).addOption(VALID_MCQ_OPTION_2).addOption(VALID_MCQ_OPTION_3)
                .withFront(VALID_MCQ_FRONT)
                .withBack(VALID_MCQ_BACK).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONT_DESC_CS2103T_CARD
                        + CommandTestUtil.BACK_DESC_CS2103T_CARD,
                new AddCardCommand(expectedCard));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONT_DESC_MCQ_CARD + BACK_DESC_MCQ_CARD
                + OPTION_1_DESC_MCQ_CARD + OPTION_2_DESC_MCQ_CARD + OPTION_3_DESC_MCQ_CARD,
                new AddCardCommand(expectedMcqCard));
    }
```
###### /java/seedu/address/logic/parser/AddCardCommandParserTest.java
``` java
    @Test
    public void parse_invalidValueCard_failure() {
        // invalid front
        assertParseFailure(parser, INVALID_FRONT_CARD + CommandTestUtil.BACK_DESC_CS2103T_CARD,
                Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid back
        assertParseFailure(parser, FRONT_DESC_CS2103T_CARD + INVALID_BACK_CARD,
                Card.MESSAGE_CARD_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_FRONT_CS2103T_CARD
                        + CommandTestUtil.BACK_DESC_CS2103T_CARD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FRONT_DESC_CS2103T_CARD + VALID_BACK_CS2103T_CARD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));

        // invalid front for mcq cards
        assertParseFailure(parser, INVALID_FRONT_CARD + OPTION_1_DESC_MCQ_CARD + OPTION_2_DESC_MCQ_CARD
                + OPTION_3_DESC_MCQ_CARD + CommandTestUtil.BACK_DESC_CS2103T_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid back for mcq cards
        assertParseFailure(parser, FRONT_DESC_CS2103T_CARD + OPTION_1_DESC_MCQ_CARD + OPTION_2_DESC_MCQ_CARD
                + OPTION_3_DESC_MCQ_CARD + INVALID_BACK_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid options for mcq cards
        assertParseFailure(parser, FRONT_DESC_MCQ_CARD + INVALID_MCQ_CARD_OPTION + OPTION_2_DESC_MCQ_CARD
                + OPTION_3_DESC_MCQ_CARD + BACK_DESC_MCQ_CARD, McqCard.MESSAGE_MCQ_CARD_CONSTRAINTS);

        // invalid back for mcq cards
        assertParseFailure(parser, FRONT_DESC_MCQ_CARD + OPTION_1_DESC_MCQ_CARD + OPTION_2_DESC_MCQ_CARD
                + OPTION_3_DESC_MCQ_CARD + INVALID_MCQ_CARD_BACK, McqCard.MESSAGE_MCQ_CARD_ANSWER_CONSTRAINTS);
    }
```
###### /java/seedu/address/model/card/McqCardTest.java
``` java
public class McqCardTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(null,
                VALID_MCQ_FRONT, VALID_MCQ_BACK, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(UUID.randomUUID(),
                null, VALID_MCQ_BACK, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(UUID.randomUUID(),
                VALID_MCQ_FRONT, null, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(UUID.randomUUID(),
                VALID_MCQ_FRONT, VALID_MCQ_BACK, null));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(null, VALID_MCQ_BACK,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(VALID_MCQ_FRONT, null,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(VALID_MCQ_FRONT, null,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> new McqCard(VALID_MCQ_FRONT, VALID_MCQ_BACK,
                null));
    }

    @Test
    public void constructor_invalidParam_throwsIllegalArgumentException() {
        String invalidParam = " ";
        List<String> invalidOptionSet = Arrays.asList(new String[] {"", "Hello", "World"});
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(UUID.randomUUID(),
                invalidParam, VALID_MCQ_BACK, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(UUID.randomUUID(),
                VALID_MCQ_FRONT, invalidParam, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(UUID.randomUUID(),
                VALID_MCQ_FRONT, VALID_MCQ_BACK, invalidOptionSet));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(invalidParam, VALID_MCQ_BACK,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(VALID_MCQ_FRONT, invalidParam,
                VALID_MCQ_OPTION_SET));
        Assert.assertThrows(IllegalArgumentException.class, () -> new McqCard(VALID_MCQ_FRONT, VALID_MCQ_BACK,
                invalidOptionSet));
    }

    @Test
    public void isValidMcqCard() {
        Assert.assertThrows(NullPointerException.class, () -> McqCard.isValidMcqCard(null, VALID_MCQ_OPTION_SET));
        Assert.assertThrows(NullPointerException.class, () -> McqCard.isValidMcqCard(VALID_MCQ_BACK, null));

        assertFalse(McqCard.isValidMcqCard("Hello", VALID_MCQ_OPTION_SET));
        assertFalse(McqCard.isValidMcqCard("0", VALID_MCQ_OPTION_SET));
        assertFalse(McqCard.isValidMcqCard("-1", VALID_MCQ_OPTION_SET));
        assertFalse(McqCard.isValidMcqCard("4", VALID_MCQ_OPTION_SET));

        assertTrue(McqCard.isValidMcqCard("1", VALID_MCQ_OPTION_SET));
        assertTrue(McqCard.isValidMcqCard("2", VALID_MCQ_OPTION_SET));
        assertTrue(McqCard.isValidMcqCard("3", VALID_MCQ_OPTION_SET));
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedCardTest.java
``` java
public class XmlAdaptedCardTest {

    private static final String INVALID_FRONT = "";
    private static final String INVALID_BACK = "";
    private static final String INVALID_ID = "";
    private static final String INVALID_BACK_FILLBLANKS = "equal";

    private static final String VALID_FRONT = "what is 1+1?";
    private static final String VALID_BACK = "2";
    private static final String VALID_ID = UUID.randomUUID().toString();

    @Test
    public void toModelType_validCardDetails_returnsCard() throws Exception {
        XmlAdaptedCard card = new XmlAdaptedCard(MATHEMATICS_CARD);
        assertEquals(card.toModelType(), MATHEMATICS_CARD);
    }

    @Test
    public void toModelType_validMcqCardDetails_returnsCard() throws Exception {
        XmlAdaptedCard card = new XmlAdaptedCard(MATHEMATICS_MCQ_CARD);
        assertEquals(card.toModelType(), MATHEMATICS_MCQ_CARD);
    }

    @Test
    public void toModelType_validFillBlanksCardDetails_returnsCard() throws Exception {
        XmlAdaptedCard card = new XmlAdaptedCard(MATHEMATICS_FILLBLANKS_CARD);
        assertEquals(card.toModelType(), MATHEMATICS_FILLBLANKS_CARD);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalArgumentException() {
        XmlAdaptedCard card = new XmlAdaptedCard(INVALID_ID, VALID_FRONT, VALID_BACK, null, Card.TYPE);
        Assert.assertThrows(IllegalArgumentException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(null, VALID_FRONT, VALID_BACK, null, Card.TYPE);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidFront_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, INVALID_FRONT, VALID_BACK, null, Card.TYPE);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullFront_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, null, VALID_BACK, null, Card.TYPE);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidBack_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, VALID_FRONT, INVALID_BACK, null, Card.TYPE);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_nullBack_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, VALID_FRONT, null, null, Card.TYPE);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidMcqCard_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, VALID_FRONT, VALID_BACK,
                Arrays.asList("1"), McqCard.TYPE);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

    @Test
    public void toModelType_invalidFillBlanksCard_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_ID, MATHEMATICS_FILLBLANKS_CARD.getFront(),
                INVALID_BACK_FILLBLANKS, null, FillBlanksCard.TYPE);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }
}

```
###### /java/seedu/address/testutil/McqCardBuilder.java
``` java
/**
 * A utility class to help with building Card objects.
 */
public class McqCardBuilder extends CardBuilder {

    public static final String[] DEFAULT_OPTIONS_ARRAY = new String[]{"9th August", "10th August", "9th September"};
    public static final String DEFAULT_BACK = "1";
    public static final List<String> DEFAULT_OPTIONS = Arrays.asList(DEFAULT_OPTIONS_ARRAY);

    private List<String> options;

    public McqCardBuilder() {
        super();
        super.back = DEFAULT_BACK;
        options = DEFAULT_OPTIONS;
    }

    /**
     * Initializes the CardBuilder with the data of {@code tagToCopy}.
     */
    public McqCardBuilder(McqCard cardToCopy) {
        super(cardToCopy);
        options = cardToCopy.getOptions();
    }

    /**
     * Sets the {@code options} of the {@code McqCard} that we are building.
     */
    public McqCardBuilder addOption(String option) {
        this.options.add(option);
        return this;
    }

    /**
     * Resets the {@code options} of the {@code McqCard} that we are building.
     */
    public McqCardBuilder resetOptions() {
        this.options = new ArrayList<>();
        return this;
    }

    @Override
    public McqCard build() {
        return new McqCard(id, front, back, options);
    }

}
```
###### /java/seedu/address/testutil/TypicalFillBlanksCards.java
``` java
/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalFillBlanksCards {

    public static final FillBlanksCard MATHEMATICS_FILLBLANKS_CARD = new FillBlanksCardBuilder().build();
    public static final FillBlanksCard PHYSICS_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("When an electron goes from _ energy to a _ energy level, it emits a photon")
            .withBack("higher, lower").build();
    public static final FillBlanksCard SCIENCE_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("Density is defined by _ divided by _")
            .withBack("mass, volume").build();
    public static final FillBlanksCard HISTORY_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("World War II occured between year _ and _")
            .withBack("1939, 1945").build();
    public static final FillBlanksCard ECONOMICS_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("A demand curve is _ sloping while a supply curve is _ sloping")
            .withBack("downward, upward").build();
    public static final FillBlanksCard GEOGRAPHY_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("In terms of continent, Singapore is in _, Canada is in _, "
                    + "England is in _ and New Zealand is in _")
            .withBack("Asia, North America, Europe, Australia").build();

    private TypicalFillBlanksCards() {} // prevents instantiation

    public static List<FillBlanksCard> getTypicalFillBlanksCards() {
        return Arrays.asList(MATHEMATICS_FILLBLANKS_CARD, HISTORY_FILLBLANKS_CARD, PHYSICS_FILLBLANKS_CARD,
                SCIENCE_FILLBLANKS_CARD, ECONOMICS_FILLBLANKS_CARD, GEOGRAPHY_FILLBLANKS_CARD);
    }

}
```
###### /java/seedu/address/testutil/TypicalMcqCards.java
``` java
/**
 * A utility class containing a list of {@code McqCard} objects to be used in tests.
 */
public class TypicalMcqCards {

    public static final McqCard MATHEMATICS_MCQ_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("1").addOption("2").addOption("3")
            .withId("3647849-d900-4f0e-8573-e3c9ab40864054")
            .withFront("What is 1 + 1?")
            .withBack("2").build();
    public static final McqCard CHEMISTRY_MCQ_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("Covalent bonding").addOption("Ionic bonding")
            .withId("f581860a-d5db-4925-aeab-1f1e442457f3")
            .withFront("What is the bonding between non-metals")
            .withBack("1").build();
    public static final McqCard HISTORY_MCQ_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("1944").addOption("1946").addOption("1945")
            .withId("3f92bd2c-affc-499c-a9a0-28b46fd61791")
            .withFront("When did World War II end?")
            .withBack("3").build();
    public static final McqCard GEOGRAPHY_MCQ_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("Asia").addOption("Africa").addOption("South America").addOption("North America")
            .addOption("Europe").addOption("Australia").addOption("Antartica")
            .withId("8c19a1f2-cfa2-4060-b9e3-67bd1ef101e0")
            .withFront("Which continent is Singapore in?")
            .withBack("1").build();

    // Manually added
    public static final McqCard PHYSICS_MCQ_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("Air").addOption("Gravity").addOption("Electricity")
            .withId("7d59e0a2-4e64-4540-abdf-ce0fa552edb7")
            .withFront("Why do things fall?")
            .withBack("2").build();
    public static final McqCard ENGLISH_MCQ_CARD = (McqCard) new McqCardBuilder()
            .resetOptions()
            .addOption("Noun").addOption("Verb").addOption("Adverb").addOption("Adjectives")
            .withId("8f4716c7-f82e-462f-bad1-e59f98b8624e")
            .withFront("What are action words?")
            .withBack("2").build();

    // Manually added - McqCard's details found in {@code CommandTestUtil}
    public static final McqCard CS2103T_MCQ_CARD = (McqCard) new McqCardBuilder()
            .withFront(VALID_FRONT_CS2103T_CARD)
            .withBack(VALID_BACK_CS2103T_CARD).build();
    public static final McqCard CS2101_MCQ_CARD = (McqCard) new McqCardBuilder()
            .withFront(VALID_FRONT_CS2101_CARD)
            .withBack(VALID_BACK_CS2101_CARD).build();

    private TypicalMcqCards() {} // prevents instantiation

    public static List<McqCard> getTypicalMcqCards() {
        return new ArrayList<>(Arrays.asList(MATHEMATICS_MCQ_CARD, CHEMISTRY_MCQ_CARD, GEOGRAPHY_MCQ_CARD,
                HISTORY_MCQ_CARD, PHYSICS_MCQ_CARD, ENGLISH_MCQ_CARD));
    }
}
```
###### /java/seedu/address/testutil/CardBuilder.java
``` java
/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_FRONT = "When is national day in Singapore?";
    public static final String DEFAULT_BACK = "9th August";

    protected UUID id;
    protected String front;
    protected String back;
    protected Schedule schedule;

    public CardBuilder() {
        id = UUID.randomUUID();
        front = DEFAULT_FRONT;
        back = DEFAULT_BACK;
        schedule = new Schedule();
    }

    /**
     * Initializes the CardBuilder with the data of {@code tagToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        id = cardToCopy.getId();
        front = cardToCopy.getFront();
        back = cardToCopy.getBack();
        schedule = cardToCopy.getSchedule();
    }


    /**
     * Sets the {@code id} of the {@code Card} that we are building.
     */
    public CardBuilder withId(String id) {
        this.id = UUID.fromString(id);
        return this;
    }

    /**
     * Sets the {@code front} of the {@code Card} that we are building.
     */
    public CardBuilder withFront(String front) {
        this.front = front;
        return this;
    }

    /**
     * Sets the {@code back} of the {@code Card} that we are building.
     */
    public CardBuilder withBack(String back) {
        this.back = back;
        return this;
    }

    /**
     * Sets the {@code schedule} of the {@code Card} that we are building.
     */
    public CardBuilder withSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public Card build() {
        return new Card(id, front, back, schedule);
    }

}
```
###### /java/seedu/address/testutil/EditCardDescriptorBuilder.java
``` java
    private EditCardDescriptor descriptor;

    public EditCardDescriptorBuilder() {
        descriptor = new EditCardCommand.EditCardDescriptor();
    }

    public EditCardDescriptorBuilder(EditCardDescriptor descriptor) {
        this.descriptor = new EditCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCardDescriptor} with fields containing {@code card}'s details
     */
    public EditCardDescriptorBuilder(Card card) {
        descriptor = new EditCardDescriptor();
        descriptor.setFront(card.getFront());
        descriptor.setBack(card.getBack());
    }

    /**
     * Sets the {@code Front} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withFront(String front) {
        descriptor.setFront(front);
        return this;
    }

    /**
     * Sets the {@code Back} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withBack(String back) {
        descriptor.setBack(back);
        return this;
    }

    /**
     * Sets the {@code UUID} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withUuid(UUID uuid) {
        descriptor.setId(uuid);
        return this;
    }

    /**
     * Sets the {@code Options} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withOptions(List<String> options) {
        descriptor.setOptions(options);
        return this;
    }
```
###### /java/seedu/address/testutil/FillBlanksCardBuilder.java
``` java
/**
 * A utility class to help with building Card objects.
 */
public class FillBlanksCardBuilder extends CardBuilder {

    public static final String DEFAULT_FRONT = "A square is a polygon with _ side meeting at _ angles";
    public static final String DEFAULT_BACK = "equal, right";

    public FillBlanksCardBuilder() {
        super();
        super.front = DEFAULT_FRONT;
        super.back = DEFAULT_BACK;
    }

    /**
     * Initializes the CardBuilder with the data of {@code tagToCopy}.
     */
    public FillBlanksCardBuilder(FillBlanksCard cardToCopy) {
        super(cardToCopy);
    }

    @Override
    public FillBlanksCard build() {
        return new FillBlanksCard(id, front, back);
    }

}
```
###### /java/systemtests/DeleteCardCommandSystemTest.java
``` java
public class DeleteCardCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first card in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCardCommand.COMMAND_WORD + "      " + INDEX_FIRST_CARD.getOneBased()
                + "       ";
        Card deletedCard = removeCard(expectedModel, INDEX_FIRST_CARD);
        String expectedResultMessage = String.format(MESSAGE_DELETE_CARD_SUCCESS, deletedCard);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last card in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastCardIndex = getCardLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastCardIndex);

        /* Case: undo deleting the last card in the list -> last card restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last card in the list -> last card deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeCard(modelBeforeDeletingLast, lastCardIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle card in the list -> deleted */
        Index middleCardIndex = getCardMidIndex(getModel());
        assertCommandSuccess(middleCardIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */
        /* Case: filtered card list, delete index within bounds of address book and card list -> deleted */
        //showCardsWithName(KEYWORD_MATCHING_MIDTERMS);
        Index index = INDEX_FIRST_CARD;
        assertTrue(index.getZeroBased() < getModel().getFilteredCardList().size());
        assertCommandSuccess(index);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCardCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCardCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getCardList().size() + 1);
        command = DeleteCardCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCardCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCardCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Card} at the specified {@code index} in {@code model}'s address book.
     * @return the removed card
     */
    private Card removeCard(Model model, Index index) {
        Card targetCard = getCard(model, index);
        try {
            model.deleteCard(targetCard);
        } catch (CardNotFoundException pnfe) {
            throw new AssertionError("targetCard is retrieved from model.");
        }
        return targetCard;
    }

    /**
     * Deletes the card at {@code toDelete} by creating a default {@code DeleteCardCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCardCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Card deletedCard = removeCard(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_CARD_SUCCESS, deletedCard);

        assertCommandSuccess(
                DeleteCardCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCardCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
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
###### /java/systemtests/EditCardCommandSystemTest.java
``` java
public class EditCardCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_CARD;
        String command = " " + EditCardCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + FRONT_DESC_CS2101_CARD;
        Card editedCard = new CardBuilder().withFront(VALID_FRONT_CS2101_CARD).withBack(MATHEMATICS_CARD.getBack())
                .build();
        assertCommandSuccess(command, index, editedCard);

        /* Case: undo editing the last card in the list -> last card restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last card in the list -> last card edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateCard(
                getModel().getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased()), editedCard);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a card with new values same as existing values -> edited */
        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + FRONT_DESC_MATHEMATICS_CARD;
        assertCommandSuccess(command, index, MATHEMATICS_CARD);

        Card cardToEdit = getModel().getFilteredCardList().get(index.getZeroBased());
        editedCard = new CardBuilder(cardToEdit).build();

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " 0" + FRONT_DESC_CS2101_CARD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " -1" + FRONT_DESC_CS2101_CARD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredCardList().size() + 1;
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " " + invalidIndex + FRONT_DESC_CS2101_CARD,
                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + FRONT_DESC_CS2101_CARD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased(),
                EditCardCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_CARD.getOneBased() + INVALID_FRONT_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        /* Case: edit a card with new values same as another card's values -> rejected */
        assertTrue(getModel().getAddressBook().getCardList().contains(CHEMISTRY_CARD));
        index = INDEX_FIRST_CARD;
        assertFalse(getModel().getFilteredCardList().get(index.getZeroBased()).equals(CHEMISTRY_CARD));
        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased()
                + FRONT_DESC_CHEMISTRY_CARD + BACK_DESC_CHEMISTRY_CARD;
        assertCommandFailure(command, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Card, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCardCommandSystemTest#assertCommandSuccess(String, Index, Card, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Card editedCard) {
        assertCommandSuccess(command, toEdit, editedCard, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCardCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the card at index {@code toEdit} being
     * updated to values specified {@code editedCard}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCardCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */

    private void assertCommandSuccess(String command, Index toEdit, Card editedCard,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateCard(
                    expectedModel.getFilteredCardList().get(toEdit.getZeroBased()), editedCard);
            //expectedModel.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        } catch (DuplicateCardException | CardNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedCard is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCardCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        //expectedModel.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
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
