# jethrokuan
###### /java/systemtests/AddCardCommandSystemTest.java
``` java
public class AddCardCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a tag to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Card toAdd = CS2103T_CARD;
        String command = "   " + AddCardCommand.COMMAND_WORD + "  " + FRONT_DESC_CS2103T_CARD + BACK_DESC_CS2103T_CARD;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding CS2103T card to the list -> CS2103T card deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding CS2103T card to the list -> CS2103T card added again */
        command = RedoCommand.COMMAND_WORD;
        model.addCard(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a Card with all fields same as another card in the card bank -> rejected */
        command = AddCardCommand.COMMAND_WORD + FRONT_DESC_CS2103T_CARD + BACK_DESC_CS2103T_CARD;
        assertCommandFailure(command, AddCardCommand.MESSAGE_DUPLICATE_CARD);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the card list before adding -> added */
        showTagsWithName(KEYWORD_MATCHING_MIDTERMS);
        assertCommandSuccess(ENGLISH_CARD);

        /* ------------------------ Perform add operation while a tag card is selected --------------------------- */

        /* Case: selects first card in the card list, add a card-> added, card selection remains unchanged */
        selectTag(Index.fromOneBased(1));
        assertCommandSuccess(CS2101_CARD);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate card -> rejected */
        command = CardUtil.getAddCardCommand(ENGLISH_CARD);
        assertCommandFailure(command, AddCardCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: missing front -> rejected */
        command = AddCardCommand.COMMAND_WORD + BACK_DESC_MCQ_CARD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));

        /* Case: missing back -> rejected */
        command = AddCardCommand.COMMAND_WORD + FRONT_DESC_CS2101_CARD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + CardUtil.getCardDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid front -> rejected */
        command = AddCardCommand.COMMAND_WORD + INVALID_FRONT_CARD + BACK_DESC_CS2103T_CARD;
        assertCommandFailure(command, Card.MESSAGE_CARD_CONSTRAINTS);

        /* Case: invalid back -> rejected */
        command = AddCardCommand.COMMAND_WORD + FRONT_DESC_CS2103T_CARD + INVALID_BACK_CARD;
        assertCommandFailure(command, Card.MESSAGE_CARD_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCardCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCardCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TagListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Card toAdd) throws DuplicateCardException {
        assertCommandSuccess(CardUtil.getAddCardCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Card)}. Executes {@code command}
     * instead.
     * @see AddCardCommandSystemTest#assertCommandSuccess(Card)
     */
    private void assertCommandSuccess(String command, Card toAdd) throws DuplicateCardException {
        Model expectedModel = getModel();
        expectedModel.addCard(toAdd);

        String expectedResultMessage = String.format(AddCardCommand.MESSAGE_SUCCESS, toAdd.getType(), toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Tag)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code cardListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCardCommandSystemTest#assertCommandSuccess(String, Card)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TagListPanel} remain unchanged.<br>
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
###### /java/seedu/address/logic/parser/ListCommandParserTest.java
``` java
public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validValues_success() {
        assertParseSuccess(parser, "", new ListCommand(false));
        assertParseSuccess(parser, "  ", new ListCommand(false));
        assertParseSuccess(parser, ListCommandParser.PREFIX_NO_TAGS_ONLY,
                new ListCommand(true));
        assertParseSuccess(parser, "  " + ListCommandParser.PREFIX_NO_TAGS_ONLY + "  ",
                new ListCommand(true));
    }

    @Test
    public void parse_invalidValues_failure() {
        String expectedMessage = ListCommandParser.MESSAGE_PARSE_FAILURE;
        assertParseFailure(parser, "-c", expectedMessage);
        assertParseFailure(parser, "hello", expectedMessage);
    }
}
```
###### /java/seedu/address/logic/parser/AddCardCommandParserTest.java
``` java
    @Test
    public void parse_allFieldsPresent_success() {
        Card expectedCard = new CardBuilder()
                .withFront(VALID_FRONT_CS2103T_CARD)
                .withBack(VALID_BACK_CS2103T_CARD)
                .build();

        Set<Tag> expectedTags = new HashSet<>(Arrays.asList(
                new Tag(new Name(VALID_NAME_ENGLISH)),
                new Tag(new Name(VALID_NAME_COMSCI)
        )));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONT_DESC_CS2103T_CARD + BACK_DESC_CS2103T_CARD,
                new AddCardCommand(expectedCard));

        // with tags
        String tagString = " " + PREFIX_TAG + VALID_NAME_ENGLISH + " " + PREFIX_TAG + VALID_NAME_COMSCI;
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONT_DESC_CS2103T_CARD + BACK_DESC_CS2103T_CARD
                        + tagString,
                new AddCardCommand(expectedCard, Optional.of(expectedTags)));
    }
```
###### /java/seedu/address/logic/parser/AddCardCommandParserTest.java
``` java
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE);
        // missing name prefix
        assertParseFailure(parser, VALID_FRONT_CS2103T_CARD,
                expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, VALID_MCQ_BACK,
                expectedMessage);

        // missing front prefix
        assertParseFailure(parser, FRONT_DESC_CS2103T_CARD,
                expectedMessage);

        // missing back prefix
        assertParseFailure(parser, BACK_DESC_CS2103T_CARD,
                expectedMessage);
    }
```
###### /java/seedu/address/logic/parser/AddCardCommandParserTest.java
``` java
    @Test
    public void parse_invalidValue_failure() {
        // invalid front
        assertParseFailure(parser, INVALID_FRONT_CARD + BACK_DESC_CS2103T_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid back
        assertParseFailure(parser, FRONT_DESC_CS2103T_CARD + INVALID_BACK_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FRONT_DESC_CS2103T_CARD + BACK_DESC_CS2103T_CARD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
    }
```
###### /java/seedu/address/logic/parser/EditCardCommandParserTest.java
``` java
    @Test
    public void parser_allFieldsPresent_success() {
        EditCardCommand.EditCardDescriptor expected = new EditCardDescriptorBuilder()
                .withFront(VALID_FRONT_CS2103T_CARD)
                .withBack(VALID_BACK_CS2103T_CARD)
                .build();

        EditCardCommand.EditCardDescriptor expectedWithAddedTags = new EditCardDescriptorBuilder(expected)
                .withTagsToAdd(new HashSet<>(Arrays.asList(ENGLISH_TAG, COMSCI_TAG)))
                .build();

        EditCardCommand.EditCardDescriptor expectedWithRemovedTags = new EditCardDescriptorBuilder(expected)
                .withTagsToRemove(new HashSet<>(Arrays.asList(BIOLOGY_TAG, MATHEMATICS_TAG)))
                .build();

        EditCardCommand.EditCardDescriptor expectedWithTags = new EditCardDescriptorBuilder(expected)
                .withTagsToAdd(new HashSet<>(Arrays.asList(ENGLISH_TAG, COMSCI_TAG)))
                .withTagsToRemove(new HashSet<>(Arrays.asList(BIOLOGY_TAG, MATHEMATICS_TAG)))
                .build();

        // without tags
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + FRONT_DESC_CS2103T_CARD
                        + BACK_DESC_CS2103T_CARD,
                new EditCardCommand(INDEX_FIRST_CARD, expected));

        // with add tags
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + FRONT_DESC_CS2103T_CARD
                        + BACK_DESC_CS2103T_CARD
                        + " " + PREFIX_ADD_TAG + ENGLISH_TAG.getName()
                        + " " +  PREFIX_ADD_TAG + COMSCI_TAG.getName(),
                new EditCardCommand(INDEX_FIRST_CARD, expectedWithAddedTags));

        // with remove tags
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + FRONT_DESC_CS2103T_CARD
                        + BACK_DESC_CS2103T_CARD
                        + " " + PREFIX_REMOVE_TAG + BIOLOGY_TAG.getName()
                        + " " +  PREFIX_REMOVE_TAG + MATHEMATICS_TAG.getName(),
                new EditCardCommand(INDEX_FIRST_CARD, expectedWithRemovedTags));

        // with both add and remove tags
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + FRONT_DESC_CS2103T_CARD
                        + BACK_DESC_CS2103T_CARD
                        + " " + PREFIX_ADD_TAG + ENGLISH_TAG.getName()
                        + " " +  PREFIX_ADD_TAG + COMSCI_TAG.getName()
                        + " " + PREFIX_REMOVE_TAG + BIOLOGY_TAG.getName()
                        + " " +  PREFIX_REMOVE_TAG + MATHEMATICS_TAG.getName(),
                new EditCardCommand(INDEX_FIRST_CARD, expectedWithTags));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid front
        assertParseFailure(parser, "1" + INVALID_FRONT_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid back
        assertParseFailure(parser, "1" + INVALID_FRONT_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid addTag
        assertParseFailure(parser, "1" + INVALID_ADD_TAG_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid removeTag
        assertParseFailure(parser, "1" + INVALID_REMOVE_TAG_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + FRONT_DESC_CS2103T_CARD, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + FRONT_DESC_CS2103T_CARD, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noFieldSpecified_failure() {
        assertParseFailure(parser, "1", EditCardCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = targetIndex.getOneBased() + FRONT_DESC_CS2103T_CARD;
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withFront(VALID_FRONT_CS2103T_CARD).build();
        EditCardCommand expectedCommand = new EditCardCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parser_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = targetIndex.getOneBased() + FRONT_DESC_CS2103T_CARD + FRONT_DESC_CS2101_CARD;

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withFront(VALID_FRONT_CS2101_CARD)
                .build();

        EditCardCommand expectedCommand = new EditCardCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
```
###### /java/seedu/address/logic/commands/ListCommandTest.java
``` java
public class ListCommandTest {
    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommand = new ListCommand(false);
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTagAtIndex(model, INDEX_FIRST_TAG); // filter tags
        EventsCenter.getInstance().post(new JumpToTagRequestEvent(INDEX_FIRST_TAG)); //filter cards
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/EditCardCommandTest.java
``` java
    @Test
    public void execute_someTagsAdded_success() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.addTags(lastCard, new HashSet<>(Arrays.asList(MATHEMATICS_TAG, COMSCI_TAG)));

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToAdd(new HashSet<>(Arrays.asList(MATHEMATICS_TAG, COMSCI_TAG)))
                .build();

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, lastCard);
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someTagsRemoved_success() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());
        Tag tag = model.getTags(lastCard).get(0);

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToRemove(new HashSet<>(Arrays.asList(tag)))
                .build();

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, lastCard);
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.removeTags(lastCard, new HashSet<>(Arrays.asList(tag)));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCardNewTagCreated_success() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        Tag newTag = new Tag(new Name("Machine Learning"));
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToAdd(new HashSet<>(Arrays.asList(newTag)))
                .build();

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, lastCard);
        EditCardCommand editCardCommand = prepareCommand(indexLastCard, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addTags(lastCard, new HashSet<>(Arrays.asList(newTag)));

        assertCommandSuccess(editCardCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeCardNoTag_failure() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToRemove(new HashSet<>(Arrays.asList(ENGLISH_TAG)))
                .build();

        String expectedMessage = String.format(MESSAGE_TAG_NOT_FOUND, ENGLISH_TAG.getName());
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_removeCardNoEdge_failure() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToRemove(new HashSet<>(Arrays.asList(MATHEMATICS_TAG)))
                .build();

        String expectedMessage = String.format(MESSAGE_CARD_NO_TAG, MATHEMATICS_TAG.getName());
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        assertCommandFailure(editCommand, model, expectedMessage);
    }
```
###### /java/seedu/address/storage/XmlAdaptedCardTagTest.java
``` java
public class XmlAdaptedCardTagTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlAdaptedCardTagTest/");
    private static final File TYPICAL_FILE = new File(TEST_DATA_FOLDER + "typicalCardTag.xml");
    private static final File INVALID_CARD_ID_FILE = new File(TEST_DATA_FOLDER + "invalidCardTag_missingCardId.xml");
    private static final File INVALID_CARDS_FILE = new File(TEST_DATA_FOLDER + "invalidCardTag_missingCards.xml");
    private static final File INVALID_TAG_ID_FILE = new File(TEST_DATA_FOLDER + "invalidCardTag_missingTagId.xml");
    private static final File INVALID_TAGS_FILE = new File(TEST_DATA_FOLDER + "invalidCardTag_missingTags.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalFile_success() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(TYPICAL_FILE,
                XmlAdaptedCardTag.class);
        CardTag cardTagFromFile = dataFromFile.toModelType();
        CardTag typicalCardTag = TypicalCardTag.getTypicalCardTag();
        assertEquals(cardTagFromFile, typicalCardTag);
    }

    @Test
    public void toModelType_missingCardId_throwsIllegalValueException() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(INVALID_CARD_ID_FILE,
                XmlAdaptedCardTag.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_missingCards_throwsIllegalValueException() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(INVALID_CARDS_FILE,
                XmlAdaptedCardTag.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_missingTagId_throwsIllegalValueException() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_ID_FILE,
                XmlAdaptedCardTag.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_missingTags_throwsIllegalValueException() throws Exception {
        XmlAdaptedCardTag dataFromFile = XmlUtil.getDataFromFile(INVALID_TAGS_FILE,
                XmlAdaptedCardTag.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
```
###### /java/seedu/address/model/cardtag/CardTagTest.java
``` java
public class CardTagTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddressBook addressBook = TypicalAddressBook.getTypicalAddressBook();

    private CardTag cardTag;

    @Before
    public void setUp() throws Exception {
        cardTag = new CardTag();

        cardTag.addEdge(MATHEMATICS_CARD, PHYSICS_TAG);
        cardTag.addEdge(COMSCI_CARD, PHYSICS_TAG);

        cardTag.addEdge(CHEMISTRY_CARD, BIOLOGY_TAG);
        cardTag.addEdge(COMSCI_CARD, BIOLOGY_TAG);
    }

    @Test
    public void addEdge_worksForNewEdges() {
        assertTrue(cardTag.isConnected(MATHEMATICS_CARD, PHYSICS_TAG));
        assertTrue(cardTag.isConnected(CHEMISTRY_CARD, BIOLOGY_TAG));
    }

    @Test
    public void addEdge_whereEdgeExistsThrowsDuplicateEdgeException() throws DuplicateEdgeException {
        thrown.expect(DuplicateEdgeException.class);
        cardTag.addEdge(COMSCI_CARD, PHYSICS_TAG);
    }

    @Test
    public void getCards_withEdges() {
        assertEquals(cardTag.getCards(PHYSICS_TAG, addressBook.getCardList()), Stream.of(MATHEMATICS_CARD, COMSCI_CARD)
                .collect(Collectors.toList()));
        assertEquals(cardTag.getCards(BIOLOGY_TAG, addressBook.getCardList()), Stream.of(CHEMISTRY_CARD, COMSCI_CARD)
                .collect(Collectors.toList()));
    }

    @Test
    public void getCards_withoutEdges() {
        assertEquals(cardTag.getCards(MATHEMATICS_TAG, addressBook.getCardList()),
                Stream.of().collect(Collectors.toList()));
    }

    @Test
    public void getTags_withEdges() {
        assertEquals(cardTag.getTags(MATHEMATICS_CARD, addressBook.getTagList()),
                Stream.of(PHYSICS_TAG).collect(Collectors.toList()));
        assertEquals(cardTag.getTags(COMSCI_CARD, addressBook.getTagList()), Stream.of(PHYSICS_TAG, BIOLOGY_TAG)
                .collect(Collectors.toList()));
    }

    @Test
    public void getTags_withoutEdges() {
        assertEquals(cardTag.getTags(GEOGRAPHY_CARD, addressBook.getTagList()),
                Stream.of().collect(Collectors.toList()));
    }

    @Test
    public void isConnected_works() {
        assertEquals(cardTag.isConnected(MATHEMATICS_CARD, BIOLOGY_TAG), false);
        assertEquals(cardTag.isConnected(COMSCI_CARD, BIOLOGY_TAG), true);
    }

    @Test
    public void removeEdge_works() throws EdgeNotFoundException {
        cardTag.removeEdge(COMSCI_CARD, BIOLOGY_TAG);
        assertTrue(!cardTag.getTags(COMSCI_CARD, addressBook.getTagList()).contains(BIOLOGY_TAG));
        assertTrue(!cardTag.getCards(BIOLOGY_TAG, addressBook.getCardList()).contains(COMSCI_CARD));
    }

    @Test
    public void removeEdge_noTagsLeft_removedFromCardMap() throws EdgeNotFoundException {
        cardTag.removeEdge(MATHEMATICS_CARD, PHYSICS_TAG);
        assertEquals(cardTag.getCardMap().size(), 2);
    }

    @Test
    public void removeEdge_noCardsLeft_removedFromTagMap() throws EdgeNotFoundException {
        // Case: Biology Tag still has 1 card associated -> still in Tag Map
        cardTag.removeEdge(CHEMISTRY_CARD, BIOLOGY_TAG);
        assertEquals(cardTag.getTagMap().size(), 2);

        // Case: Biology Tag has no more cards associated -> removed from Tag Map
        cardTag.removeEdge(COMSCI_CARD, BIOLOGY_TAG);
        assertEquals(cardTag.getTagMap().size(), 1);
    }

    @Test
    public void removeEdge_onNonExistingEdgeThrowsEdgeNotFoundException() throws EdgeNotFoundException {
        thrown.expect(EdgeNotFoundException.class);
        cardTag.removeEdge(MATHEMATICS_CARD, BIOLOGY_TAG);
    }
}
```
###### /java/seedu/address/testutil/EditCardDescriptorBuilder.java
``` java
    /**
     * Sets the {@code tagsToAdd} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withTagsToAdd(Set<Tag> tags) {
        descriptor.setTagsToAdd(tags);
        return this;
    }

    /**
     * Sets the {@code tagsToRemove} of the {@code EditCardDescriptor} t hat we are building.
     */
    public EditCardDescriptorBuilder withTagsToRemove(Set<Tag> tags) {
        descriptor.setTagsToRemove(tags);
        return this;
    }
```
