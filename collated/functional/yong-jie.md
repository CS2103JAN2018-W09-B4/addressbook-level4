# yong-jie
###### /java/seedu/address/commons/events/ui/TagListPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Tag List Panel
 */
public class TagListPanelSelectionChangedEvent extends BaseEvent {

    private final TagCard newSelection;

    public TagListPanelSelectionChangedEvent(TagCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TagCard getNewSelection() {
        return newSelection;
    }
}
```
###### /java/seedu/address/commons/events/ui/EmptyCardBackEvent.java
``` java
/**
 * Represents a selection change in the Tag List Panel
 */
public class EmptyCardBackEvent extends BaseEvent {

    public EmptyCardBackEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/CardListPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Card List Panel
 */
public class CardListPanelSelectionChangedEvent extends BaseEvent {

    private final CardCard newSelection;

    public CardListPanelSelectionChangedEvent(CardCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public CardCard getNewSelection() {
        return newSelection;
    }

}
```
###### /java/seedu/address/commons/events/ui/ChangeThemeRequestEvent.java
``` java
/**
 * Indicates a request to change theme
 */
public class ChangeThemeRequestEvent extends BaseEvent {
    public final String theme;

    public ChangeThemeRequestEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/JumpToCardRequestEvent.java
``` java
/**
 * Indicates a request to jump to the list of tags
 */
public class JumpToCardRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToCardRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/SelectCardCommand.java
``` java
/**
 * Selects a card identified using it's last displayed index from the card bank.
 */
public class SelectCardCommand extends Command {
    public static final String COMMAND_WORD = "selectc";

    public static final String PARAMS = "INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the card identified by the index number used in the last card listing.\n"
            + "Parameters: " + PARAMS
            + " Example: " + COMMAND_WORD + " 1";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PARAMS;

    public static final String MESSAGE_SELECT_CARD_SUCCESS = "Selected Card: %1$s";

    private final Index targetIndex;

    public SelectCardCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Card> lastShownList = model.getFilteredCardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToCardRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_CARD_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCardCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCardCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ChangeThemeCommand.java
``` java
/**
 * Changes the theme of the UI.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String PARAMS = PREFIX_THEME + "THEME";

    public static final String MESSAGE_SUCCESS = "Theme changed successfully";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the colour scheme of the application."
            + "Parameters: "
            + PARAMS
            +  " "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_THEME + "light ";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PARAMS;

    private final String[] themeStrings = {"view/LightTheme.css", "view/DarkTheme.css"};
    private final String newTheme;

    public ChangeThemeCommand(Integer themeIndex) {
        newTheme = themeStrings[themeIndex];
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(newTheme));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && newTheme.equals(((ChangeThemeCommand) other).newTheme));
    }
}
```
###### /java/seedu/address/logic/parser/SelectCardCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SelectCardCommand object
 */
public class SelectCardCommandParser implements Parser<SelectCardCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCardCommand
     * and returns an SelectCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCardCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/ChangeThemeCommandParser.java
``` java
/**
 * Parses input arguments and returns a new ChangeThemeCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeThemeCommand
     * and returns an ChangeThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ChangeThemeCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_THEME);
        if (!arePrefixesPresent(argMultimap, PREFIX_THEME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }

        try {
            Integer theme = ParserUtil.parseTheme(argMultimap.getValue(PREFIX_THEME));
            return new ChangeThemeCommand(theme);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String theme} into an {@code Integer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code theme} is invalid.
     */
    public static Integer parseTheme(Optional<String> theme) throws IllegalValueException {
        final ArrayList<String> validThemes = new ArrayList<String>(Arrays.asList(VALID_THEMES));
        requireNonNull(theme);
        if (!validThemes.contains(theme.get())) {
            throw new IllegalValueException(MESSAGE_INVALID_THEME);
        }
        return validThemes.indexOf(theme.get());
    }
```
###### /java/seedu/address/logic/Autocompleter.java
``` java
/**
 * Abstracts the logic of collating the commands, COMMAND_WORDs and AUTOCOMPLETE_TEXTs, and
 * determining the eligibility of command box text replacement.
 */
public class Autocompleter {

    public static String getAutocompleteText(String input) {
        return getAutocompleteTexts()
                .stream()
                .filter(text -> text.startsWith(input))
                .collect(Collectors.toList())
                .get(0);
    }

    /**
     * Checks if the input text given is a valid candidate
     * for autocompletion.
     * @param input The input text
     */
    public static Boolean isValidAutocomplete(String input) {
        return getCommandWords()
                .stream()
                .filter(word -> input.startsWith(word))
                .collect(Collectors.toList())
                .size() > 0;
    }

    private static List<String> getAutocompleteTexts() {
        return getCommandFields("AUTOCOMPLETE_TEXT");
    }

    private static List<String> getCommandWords() {
        return getCommandFields("COMMAND_WORD");
    }

    /**
     * Fetches the field of a class programatically using strings,
     * removing the need for hardcode.
     * @param field A string indicating the field to access
     */
    private static List<String> getCommandFields(String field) {
        return getCommandClasses().stream().map(command -> {
            try {
                return (String) command.getField(field).get(null);
            } catch (NoSuchFieldException e) {
                return "";
            } catch (IllegalAccessException e) {
                return "";
            }
        }).collect(Collectors.toList());
    }

    private static List<Class<? extends Command>> getCommandClasses() {
        List<Class<? extends Command>> commands = new ArrayList<>();

        // Must be added in increasing specificity so that add is not
        // overridden by addc, for example.
        commands.add(AddCardCommand.class);
        commands.add(ChangeThemeCommand.class);
        commands.add(ClearCommand.class);
        commands.add(DeleteCommand.class);
        commands.add(DeleteCardCommand.class);
        commands.add(EditCommand.class);
        commands.add(EditCardCommand.class);
        commands.add(ExitCommand.class);
        commands.add(FindCommand.class);
        commands.add(HelpCommand.class);
        commands.add(ListCommand.class);
        commands.add(RedoCommand.class);
        commands.add(SelectCommand.class);
        commands.add(SelectCardCommand.class);
        commands.add(ShowDueCommand.class);
        commands.add(UndoCommand.class);

        return commands;
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void showAllCards() {
        filteredCards.setAll(this.addressBook.getCardList());
    }

    /**
     * Synchronises the card list with that of the card bank.
     */
    private void updateFilteredCardList() {
        if (selectedTag == null) {
            showAllCards();
            return;
        }
        filterCardsByTag(selectedTag);
    }

    @Override
    public void filterCardsByTag(Tag tag) {
        filteredCards.setAll(addressBook
            .getCardTag()
            .getCards(tag, addressBook.getCardList()));
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Subscribe
    private void handleTagListPanelSelectionEvent(TagListPanelSelectionChangedEvent event) {
        selectedTag = event.getNewSelection().tag;
        filterCardsByTag(selectedTag);
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Checks {@code CommandBox}'s text field to see if it is populated with a valid
     * command word. Then replaces text with autocompleter's text to guide the user
     * on the relevant parameters.
     */
    private void autocomplete() {
        if (!Autocompleter.isValidAutocomplete(commandTextField.getText())) {
            return;
        }
        replaceText(Autocompleter.getAutocompleteText(commandTextField.getText()));
    }
```
###### /java/seedu/address/ui/CardListPanel.java
``` java
/**
 * Panel containing a list of cards
 */
public class CardListPanel extends UiPart<Region> {
    private static final String FXML = "CardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    private final CardTag cardTag;
    private final ObservableList<Tag> tagList;

    @FXML
    private ListView<CardCard> cardListView;

    public CardListPanel(ObservableList<Card> filteredCardList, CardTag cardTag, ObservableList<Tag> tagList) {
        super(FXML);
        this.cardTag = cardTag;
        this.tagList = tagList;
        setConnections(filteredCardList);
        registerAsAnEventHandler(this);
    }

    /**
     * Creates a new CardCard given the index.
     *
     * Computes the card's tags, and adds in this information.
     *
     * @param card Given Card
     * @param displayedIndex  given Index
     * @return new CardCard instance
     */
    public CardCard createCardCard(Card card, int displayedIndex) {
        List<Tag> tags = cardTag.getTags(card, tagList);
        return new CardCard(card, displayedIndex, tags);
    }

    public void setConnections(ObservableList<Card> cardList) {
        ObservableList<CardCard> mappedList = EasyBind.map(
                cardList, (card) -> createCardCard(card, cardList.indexOf(card) + 1));
        cardListView.setItems(mappedList);
        cardListView.setCellFactory(listView -> new CardListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        cardListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in card list panel changed to : '" + newValue + "'");
                        raise(new CardListPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TagCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            cardListView.scrollTo(index);
            cardListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToCardRequestEvent(JumpToCardRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CardCard}.
     */
    class CardListViewCell extends ListCell<CardCard> {
        @Override
        protected void updateItem(CardCard card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(card.getRoot());
            }
        }
    }
}
```
###### /java/seedu/address/ui/CardBack.java
``` java
/**
 * A UI component that displays information about a {@code Card}.
 */
public class CardBack extends UiPart<Region> {
    private static final String FXML = "CardBack.fxml";

    @FXML
    private Label cardBack;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public CardBack() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleCardListPanelSelectionChangedEvent(CardListPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Card card = event.getNewSelection().card;
        if (card.getType().equals(McqCard.TYPE)) {
            cardBack.setText(card.getOptions()
                    .get(Integer.valueOf(card.getBack()) - 1));
        } else {
            cardBack.setText(card.getBack());
        }
    }

    @Subscribe
    private void handleEmptyCardBackEvent(EmptyCardBackEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        cardBack.setText("");
    }
}
```
###### /java/seedu/address/ui/CardCard.java
``` java
/**
 * A UI component that displays information about a {@code Card}.
 */
public class CardCard extends UiPart<Region> {
    private static final String FXML = "CardListCard.fxml";

    public final Card card;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label front;

    @FXML
    private FlowPane tags;

    @FXML
    private FlowPane options;

    public CardCard(Card card, int displayedIndex, List<Tag> tagList) {
        super(FXML);
        this.card = card;
        id.setText(Integer.toString(displayedIndex));
        front.setText(card.getFront());
        tagList.forEach(tag -> tags.getChildren().add(new Label(tag.getName().toString())));
        if (card.getType().equals(McqCard.TYPE)) {
            for (int i = 0; i < card.getOptions().size(); i++) {
                String option = card.getOptions().get(i);
                options.getChildren().add(new Label((i + 1) + ") " + option));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CardCard)) {
            return false;
        }

        CardCard card = (CardCard) other;
        return id.getText().equals(card.id.getText())
                && card.equals(card.card);
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeRequestEvent event) {
        String currentTheme = UiManager.getCurrentTheme();
        primaryStage.getScene().getStylesheets().removeAll(currentTheme);
        primaryStage.getScene().getStylesheets().add(event.theme);
        UiManager.setCurrentTheme(event.theme);
        this.prefs.setTheme(event.theme);
    }
}
```
###### /resources/view/CardBack.fxml
``` fxml
<HBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
      id="cardPane" fx:id="cardPane" maxWidth="300">
    <GridPane HBox.hgrow="ALWAYS" styleClass="card-back">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="120" GridPane.columnIndex="0" spacing="5">
            <HBox>
                <Pane HBox.hgrow="ALWAYS" />
                <Label fx:id="type" text="back" styleClass="card-type, cell_small_label" />
            </HBox>
            <Label fx:id="cardBack" styleClass="card-text" wrapText="true" />
            <Separator />
        </VBox>
    </GridPane>
</HBox>
```
