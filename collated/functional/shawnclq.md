# shawnclq
###### /java/seedu/address/logic/parser/EditCardCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCardCommand object
 */
public class EditCardCommandParser implements Parser<EditCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCardCommand
     * and returns an EditCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCardCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FRONT, PREFIX_BACK, PREFIX_OPTION,
                        PREFIX_ADD_TAG, PREFIX_REMOVE_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));
        }

        EditCardCommand.EditCardDescriptor editCardDescriptor = new EditCardDescriptor();
        try {
            ParserUtil.parseFront(argMultimap.getValue(PREFIX_FRONT))
                    .ifPresent(editCardDescriptor::setFront);
            ParserUtil.parseBack(argMultimap.getValue(PREFIX_BACK))
                    .ifPresent(editCardDescriptor::setBack);
            ParserUtil.parseOptions(argMultimap.getAllValues(PREFIX_OPTION))
                    .ifPresent(editCardDescriptor::setOptions);
            ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_ADD_TAG))
                    .ifPresent(editCardDescriptor::setTagsToAdd);
            ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_REMOVE_TAG))
                    .ifPresent(editCardDescriptor::setTagsToRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editCardDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCardCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCardCommand(index, editCardDescriptor);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String card} into an {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code card} is invalid.
     */
    public static String parseCard(String card) throws IllegalValueException {
        requireNonNull(card);
        String trimmedCard = card.trim();
        if (!Card.isValidCard(trimmedCard)) {
            throw new IllegalValueException(Card.MESSAGE_CARD_CONSTRAINTS);
        }
        return trimmedCard;
    }

    /**
     * Parses a {@code String card} into an {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code String} is invalid.
     */
    public static String parseMcqOption(String option) throws IllegalValueException {
        requireNonNull(option);
        String trimmedOption = option.trim();
        if (!Card.isValidCard(trimmedOption)) {
            throw new IllegalValueException(McqCard.MESSAGE_MCQ_CARD_CONSTRAINTS);
        }
        return trimmedOption;
    }

    /**
     * Parses a {@code String front, back, Set<String> options} into an {@code McqCard}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given parameters are invalid.
     */
    public static McqCard parseMcqCard(String front, String back, List<String> options) throws IllegalValueException {
        requireNonNull(front);
        requireNonNull(back);
        requireAllNonNull(options);
        if (!McqCard.isValidMcqCard(back, options)) {
            throw new IllegalValueException(McqCard.MESSAGE_MCQ_CARD_ANSWER_CONSTRAINTS);
        }
        return new McqCard(front, back, options);
    }

    /**
     * Parses a {@code String card} into an {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code String} is invalid.
     */
    public static FillBlanksCard parseFillBlanksCard(String front, String back) throws IllegalValueException {
        requireAllNonNull(front, back);
        if (!FillBlanksCard.isValidFillBlanksCard(front, back)) {
            throw new IllegalValueException(FillBlanksCard.MESSAGE_FILLBLANKS_CARD_ANSWER_CONSTRAINTS);
        }
        return new FillBlanksCard(front, back);
    }

    /**
     * Parses a {@code Optional<String> front} into an {@code Optional<Card>} if {@code front} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseFront(Optional<String> front) throws IllegalValueException {
        requireNonNull(front);
        if (front.isPresent()) {
            if (!Card.isValidCard(front.get())) {
                throw new IllegalValueException(Card.MESSAGE_CARD_CONSTRAINTS);
            }
        }
        return front.isPresent() ? Optional.of(parseCard(front.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> front} into an {@code Optional<Card>} if {@code front} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseBack(Optional<String> back) throws IllegalValueException {
        requireNonNull(back);
        if (back.isPresent()) {
            if (!Card.isValidCard(back.get())) {
                throw new IllegalValueException(Card.MESSAGE_CARD_CONSTRAINTS);
            }
        }
        return back.isPresent() ? Optional.of(parseCard(back.get())) : Optional.empty();
    }

    /**
     * Parses a {@code List<String>} into a {@code Optional<List<String>>}
     */
    public static Optional<List<String>> parseOptions(List<String> optionValues) throws IllegalValueException {
        requireAllNonNull(optionValues);
        if (optionValues.isEmpty()) {
            return Optional.empty();
        }

        List<String> options = new ArrayList<String>();
        for (String option : optionValues) {
            if (!Card.isValidCard(option)) {
                throw new IllegalValueException(McqCard.MESSAGE_MCQ_CARD_CONSTRAINTS);
            }
            options.add(option.trim());
        }

        return Optional.of(options);
    }
```
###### /java/seedu/address/logic/parser/AddCardCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCardCommand object
 */
public class AddCardCommandParser implements Parser<AddCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCardCommand
     * and returns an AddCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCardCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FRONT, PREFIX_BACK, PREFIX_OPTION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_FRONT, PREFIX_BACK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
        }

        try {
            String front = ParserUtil.parseCard(argMultimap.getValue(PREFIX_FRONT).get());
            String back = ParserUtil.parseCard(argMultimap.getValue(PREFIX_BACK).get());
            List<String> options = argMultimap.getAllValues(PREFIX_OPTION);
            Optional<Set<Tag>> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Card card;

            if (options.isEmpty()) {
                if (FillBlanksCard.containsBlanks(front)) {
                    card = ParserUtil.parseFillBlanksCard(front, back);
                } else {
                    card = new Card(front, back);
                }
            } else {
                for (String option: options) {
                    ParserUtil.parseMcqOption(option);
                }
                card = ParserUtil.parseMcqCard(front, back, options);
                card.setType(McqCard.TYPE);
            }

            return new AddCardCommand(card, tags);
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
###### /java/seedu/address/logic/parser/DeleteCardCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCardCommandParser implements Parser<DeleteCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCardCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/model/card/Card.java
``` java
    public static final String MESSAGE_CARD_CONSTRAINTS =
            "Card front and back can take any values, and it should not be blank";

    /*
     * The first character of the card must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CARD_VALIDATION_REGEX = "[^\\s].*";
    public static final String TYPE = "Normal";

    protected final UUID id;
    protected final String front;
    protected final String back;
    protected final Schedule schedule;
    protected String type;

    public Card(String front, String back) {
        this(UUID.randomUUID(), front, back);
    }

    public Card(UUID id, String front, String back) {
        requireAllNonNull(id, front, back);
        checkArgument(isValidCard(front), MESSAGE_CARD_CONSTRAINTS);
        checkArgument(isValidCard(back), MESSAGE_CARD_CONSTRAINTS);
        this.front = front;
        this.back = back;
        this.id = id;
        this.schedule = new Schedule();
        this.type = TYPE;
    }

    public Card(UUID id, String front, String back, Schedule schedule) {
        requireAllNonNull(id, front, back, schedule);
        checkArgument(isValidCard(front), MESSAGE_CARD_CONSTRAINTS);
        checkArgument(isValidCard(back), MESSAGE_CARD_CONSTRAINTS);
        this.front = front;
        this.back = back;
        this.id = id;
        this.schedule = schedule;
        this.type = TYPE;
    }

    public UUID getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }
```
###### /java/seedu/address/model/card/Card.java
``` java
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns true if a given front and back string is valid.
     */
    public static boolean isValidCard(String test) {
        requireAllNonNull(test);
        return test.matches(CARD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherCard = (Card) other;

        // TODO: account for ID equality. Some test cases check for object equality.
        //        return otherCard.getId().toString().equals(this.getId().toString())
        //                && otherCard.getFront().equals(this.getFront())
        //                && otherCard.getBack().equals(this.getBack());

        return otherCard.getFront().equals(this.getFront())
                && otherCard.getBack().equals(this.getBack());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, front, back);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Front: ")
                .append(getFront())
                .append(" Back: ")
                .append(getBack());
        return builder.toString();
    }
```
###### /java/seedu/address/model/card/FillBlanksCard.java
``` java
/**
 * Represents a fill-in-the-blanks Flashcard.
 * Guarantees: Front, Back must not be null.
 *
 */
public class FillBlanksCard extends Card {

    public static final String MESSAGE_FILLBLANKS_CARD_ANSWER_CONSTRAINTS =
            "Fill Blanks Card back should have the same number of answers as there are blanks";
    public static final String TYPE = "FillBlanks";
    public static final String BLANK = "_";

    public FillBlanksCard(String front, String back) {
        this(UUID.randomUUID(), front, back);
    }

    public FillBlanksCard(UUID id, String front, String back) {
        super(id, front, back);
        super.setType(TYPE);
        checkArgument(isValidFillBlanksCard(front, back), MESSAGE_FILLBLANKS_CARD_ANSWER_CONSTRAINTS);
    }

    /**
     * Returns true if a given front and back string is valid.
     */
    public static boolean isValidFillBlanksCard(String front, String back) {
        requireAllNonNull(front, back);
        return (front.split(BLANK, -1).length) == back.split(",").length + 1;
    }

    /**
     * Returns true if a given string contains blanks.
     */
    public static boolean containsBlanks(String test) {
        return (test.indexOf(BLANK)) != -1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FillBlanksCard)) {
            return false;
        }

        FillBlanksCard otherCard = (FillBlanksCard) other;

        return otherCard.getFront().equals(this.getFront())
                && otherCard.getBack().equals(this.getBack());
    }
}
```
###### /java/seedu/address/model/card/McqCard.java
``` java
/**
 * Represents a MCQ Flashcard.
 * Guarantees: Front, Back, Options must not be null.
 *
 */
public class McqCard extends Card {

    public static final String MESSAGE_MCQ_CARD_CONSTRAINTS =
            "MCQ Card front, back and options can take any values, and it should not be blank";
    public static final String MESSAGE_MCQ_CARD_ANSWER_CONSTRAINTS =
            "MCQ Card back should be an integer between 1 and the number of options input";
    public static final String TYPE = "MCQ";

    private final ObservableList<String> optionsList = FXCollections.observableArrayList();

    public McqCard(String front, String back, List<String> options) {
        this(UUID.randomUUID(), front, back, options);
    }

    public McqCard(UUID id, String front, String back, List<String> options) {
        super(id, front, back);
        super.setType(TYPE);
        requireAllNonNull(options);
        for (String option: options) {
            checkArgument(super.isValidCard(option), super.MESSAGE_CARD_CONSTRAINTS);
        }
        optionsList.addAll(options);

        assert CollectionUtil.elementsAreUnique(optionsList);
    }

    /**
     * Returns all options in this list as a Set.
     * This set is mutable and change-insulated against options list.
     */
    @Override
    public List<String> getOptions() {
        assert CollectionUtil.elementsAreUnique(optionsList);
        return optionsList;
    }

    /**
     * Replaces the options in the list with those in the argument options list.
     */
    public void setOptions(List<String> options) {
        requireAllNonNull(options);
        optionsList.addAll(options);

        assert CollectionUtil.elementsAreUnique(optionsList);
    }

    /**
     * Returns true if the list contains an equivalent String as the given argument.
     */
    public boolean containsOption(String option) {
        requireNonNull(option);
        return optionsList.contains(option);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<String> asObservableList() {
        assert CollectionUtil.elementsAreUnique(optionsList);
        return FXCollections.unmodifiableObservableList(optionsList);
    }

    /**
     * Returns true if a given front and back string is valid.
     */
    public static boolean isValidMcqCard(String back, List<String> options) {
        requireAllNonNull(back, options);
        int backInt;
        try {
            backInt = Integer.valueOf(back);
        } catch (NumberFormatException e) {
            return false;
        }
        return (backInt >= 1) && (backInt <= options.size());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof McqCard)) {
            return false;
        }

        McqCard otherCard = (McqCard) other;

        return otherCard.getFront().equals(this.getFront())
                && otherCard.getBack().equals(this.getBack())
                && otherCard.getOptions().containsAll(optionsList)
                && optionsList.containsAll(otherCard.getOptions());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(super.id, super.front, back, optionsList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Front: ").append(getFront());
        for (String option: optionsList) {
            builder.append(" Option: ").append(option);
        }
        builder.append(" Back: ").append(getBack());
        return builder.toString();
    }

}
```
