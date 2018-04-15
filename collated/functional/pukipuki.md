# pukipuki
###### /java/seedu/address/logic/commands/AnswerCommand.java
``` java
/**
 * Answers a selected flashcard
 */
public class AnswerCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "answer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Answer a selected flashcard. "
            + "Parameters: "
            + PREFIX_CONFIDENCE + "CONFIDENCE LEVEL";

    public static final String MESSAGE_SUCCESS = "Your card has been scheduled.";
    public static final String MESSAGE_CARD_NOT_SELECTED = "Cannot answer to no card, please select a card first.";

    private final int confidenceLevel;

    /**
     * Creates an AnswerCommand to answer the selected {@code Card}
     */
    public AnswerCommand(int confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.answerSelectedCard(confidenceLevel);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (NoCardSelectedException e) {
            throw new CommandException(MESSAGE_CARD_NOT_SELECTED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerCommand // instanceof handles nulls
                && confidenceLevel == (((AnswerCommand) other).confidenceLevel));
    }
}
```
###### /java/seedu/address/logic/commands/ScheduleCommand.java
``` java
/**
 * Schedule the selected flashcard
 */
public class ScheduleCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule card to be reviewed on the specified date.\n"
            + "Optional Parameters: if parameter is not given, will default to today's day, month or year."
            + PREFIX_DAY + "30 "
            + PREFIX_MONTH + "2 "
            + PREFIX_YEAR + "2018";
    public static final String MESSAGE_SUCCESS = "Card scheduled for review on %s";
    public static final String MESSAGE_CARD_NOT_SELECTED = "Cannot answer to no card, please select a card first.";

    private final LocalDateTime date;

    /**
     * Creates an ScheduleCommand to schedule the selected {@code Card}
     */
    public ScheduleCommand(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.setNextReview(this.date);
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.date.toLocalDate().toString()));
        } catch (NoCardSelectedException e) {
            throw new CommandException(MESSAGE_CARD_NOT_SELECTED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && date.equals(((ScheduleCommand) other).date));
    }
}
```
###### /java/seedu/address/logic/commands/ShowDueCommand.java
``` java
/**
 * Lists all cards in the card book.
 */
public class ShowDueCommand extends Command {
    public static final String COMMAND_WORD = "showdue";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all cards due on a date.\n"
        + "Optional Parameters: if parameter is not given, will default to today's day, month or year."
        + PREFIX_DAY + "30 "
        + PREFIX_MONTH + "2 "
        + PREFIX_YEAR + "2018";

    public static final String MESSAGE_SUCCESS = "Listed all cards due before, %s.\n%s";
    public static final String MESSAGE_COMPLETED = "But you have no cards due. Hurray!";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;
    private final LocalDateTime date;

    public ShowDueCommand(LocalDateTime date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(this.date);
        model.showDueCards(this.date);
        return new CommandResult(String.format(MESSAGE_SUCCESS, date.toLocalDate().toString(), correctButEmpty()));
    }

    /**
     * The commands work properly, but need to notify user that there are no cards to study for that date.
     * @return
     */
    public String correctButEmpty() {
        if (model.getFilteredCardList().isEmpty()) {
            return MESSAGE_COMPLETED;
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ShowDueCommand // instanceof handles nulls
            && date.equals(((ShowDueCommand) other).date));
    }
}
```
###### /java/seedu/address/logic/parser/ScheduleCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {
    public static final String MESSAGE_NOT_MORE_THAN_ONE = "Only one of each d/ m/ y/ argument allowed.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);

        if (!argMultimap.getPreamble().isEmpty()
            && !anyPrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        } else if (moreThanOnePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            throw new ParseException(MESSAGE_NOT_MORE_THAN_ONE
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        String dayString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_DAY));
        String monthString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_MONTH));
        String yearString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_YEAR));

        try {
            LocalDateTime date = ParserUtil.parseDate(dayString, monthString, yearString);
            return new ScheduleCommand(date);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if any of the prefix is present.
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if more than one of the same prefix is present.
     * {@code ArgumentMultimap}.
     */
    private static boolean moreThanOnePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java

    /**
     * Parses a {@code String confidenceLevel} into an {@code Integer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code confidenceLevel} is invalid.
     */
    public static int parseConfidenceLevel(String confidenceLevelString) throws IllegalValueException {
        requireNonNull(confidenceLevelString);
        String trimmedConfidenceLevelString = confidenceLevelString.trim();
        try {
            if (!Schedule
                .isValidConfidenceLevel(trimmedConfidenceLevelString)) {
                throw new IllegalValueException(Schedule.MESSAGE_ANSWER_CONSTRAINTS);
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(Schedule.MESSAGE_ANSWER_CONSTRAINTS);
        }
        return Integer.parseInt(confidenceLevelString);
    }

    /**
     * Parses {@code String dayString, String monthString, String yearString} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given date is invalid.
     */
    public static LocalDateTime parseDate(String dayString, String monthString, String yearString)
        throws IllegalValueException, DateTimeException {

        try {
            int year = getYear(yearString);
            int month = getMonth(monthString);
            int day = getDay(dayString);
            if (!Schedule.isValidDay(day)) {
                throw new IllegalValueException(Schedule.MESSAGE_DAY_CONSTRAINTS);
            } else if (!Schedule.isValidMonth(month)) {
                throw new IllegalValueException(Schedule.MESSAGE_MONTH_CONSTRAINTS);
            }
            LocalDateTime date = LocalDate.of(year, month, day).atStartOfDay();
            return date;
        } catch (DateTimeException dte) {
            throw new IllegalValueException(dte.getMessage());
        } catch (NumberFormatException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }

    /**
     * Helper functions for parseDate
     */
    public static String trimDateArgs(Optional<String> args) {
        if (args.isPresent()) {
            return args.get();
        } else {
            return "";
        }
    }

    /**
     * Helper functions for parseDate
     */
    public static int getYear(String yearString) {
        if (yearString.equals("")) {
            return LocalDate.now().getYear();
        } else {
            try {
                return Integer.parseInt(yearString);
            } catch (NumberFormatException e) {
                throw new NumberFormatException(MESSAGE_INVALID_NUMBER);
            }
        }
    }

    /**
     * Helper functions for parseDate
     */
    public static int getMonth(String monthString) {
        if (monthString.equals("")) {
            return LocalDate.now().getMonthValue();
        } else {
            try {
                return Integer.parseInt(monthString);
            } catch (NumberFormatException e) {
                throw new NumberFormatException(MESSAGE_INVALID_NUMBER);
            }
        }
    }

    /**
     * Helper functions for parseDate
     */
    public static int getDay(String dayString) {
        if (dayString.equals("")) {
            return LocalDate.now().getDayOfMonth();
        } else {
            try {
                return Integer.parseInt(dayString);
            } catch (NumberFormatException e) {
                throw new NumberFormatException(MESSAGE_INVALID_NUMBER);
            }
        }
    }
```
###### /java/seedu/address/logic/parser/AnswerCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AnswerCommandParser implements Parser<AnswerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnswerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONFIDENCE);

        if (!arePrefixesPresent(argMultimap, PREFIX_CONFIDENCE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }

        try {
            int confidenceLevel = ParserUtil.parseConfidenceLevel(argMultimap.getValue(PREFIX_CONFIDENCE).get());
            return new AnswerCommand(confidenceLevel);
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
###### /java/seedu/address/logic/parser/ShowDueCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ShowDueCommand object
 */
public class ShowDueCommandParser implements Parser<ShowDueCommand> {
    public static final String MESSAGE_NOT_MORE_THAN_ONE = "Only one of each d/ m/ y/ argument allowed.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the ShowDueCommand
     * and returns an ShowDueCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowDueCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);

        if (!argMultimap.getPreamble().isEmpty()
            && !anyPrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDueCommand.MESSAGE_USAGE));
        } else if (moreThanOnePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            throw new ParseException(MESSAGE_NOT_MORE_THAN_ONE
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDueCommand.MESSAGE_USAGE));
        }

        String dayString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_DAY));
        String monthString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_MONTH));
        String yearString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_YEAR));
        try {
            LocalDateTime date = ParserUtil.parseDate(dayString, monthString, yearString);
            return new ShowDueCommand(date);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if any of the prefix is present.
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if more than one of the same prefix is present.
     * {@code ArgumentMultimap}.
     */
    private static boolean moreThanOnePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }
}
```
###### /java/seedu/address/model/card/Schedule.java
``` java

/**
 * Holds the Schedule information for a Card
 */
public class Schedule implements Comparable<Schedule> {

    public static final int VALID_CONFIDENCE_LEVEL_0 = 0;
    public static final int VALID_CONFIDENCE_LEVEL_1 = 1;
    public static final int VALID_CONFIDENCE_LEVEL_2 = 2;
    public static final int VALID_MIN_CONFIDENCE_LEVEL = 0;
    public static final int VALID_MAX_CONFIDENCE_LEVEL = 2;
    public static final String MESSAGE_ANSWER_CONSTRAINTS =
        "Confidence Levels should only be 0, 1 or 2";
    public static final String MESSAGE_DAY_CONSTRAINTS =
        "There are at most 31 and at least 1 day.";
    public static final String MESSAGE_MONTH_CONSTRAINTS =
        "There are at most 12 and at least 1 month.";
    public static final int INITIAL_LEARNING_PHASE = 3;
    public static final int INITIAL_LAST_INTERVAL = 1;
    public static final double INITIAL_EASING_FACTOR = 1.3;
    public static final double INITIAL_HISTORICAL_EASING_FACTOR = 1.3;
    private final double lowerBoundRememberRate = 0.85;

    private LocalDateTime nextReview;
    private int learningPhase = INITIAL_LEARNING_PHASE;
    private int lastInterval = INITIAL_LAST_INTERVAL;
    private double easingFactor = 1.3;
    private double historicalEasingFactor = 1.3;
    private int success = 0;
    private int failure = 0;

    public Schedule() {
        this.nextReview = LocalDate.now().atStartOfDay();
    }

    public Schedule(LocalDateTime date) {
        this.nextReview = date;
    }

    /**
     * Check if it is a valid confidence level between 0 1 2
     *
     * @param confidenceLevel
     * @return true/false
     */
    public static boolean isValidConfidenceLevel(int confidenceLevel) {
        return confidenceLevel >= VALID_MIN_CONFIDENCE_LEVEL
            && confidenceLevel <= VALID_MAX_CONFIDENCE_LEVEL;
    }

    /**
     * Check if it is a valid confidence level between 0 1 2 string version
     *
     * @param confidenceLevelString
     * @return true/false
     */
    public static boolean isValidConfidenceLevel(String confidenceLevelString)
        throws NumberFormatException {
        try {
            int confidenceLevel = Integer.parseInt(confidenceLevelString);
            return isValidConfidenceLevel(confidenceLevel);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(nfe.getMessage());
        }
    }

    /**
     * Check if it is a valid Month between 1 to 12
     *
     * @param month
     * @return true/false
     */
    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * Check if it is a valid day between 1 to 31
     *
     * @param day
     * @return true/false
     */
    public static boolean isValidDay(int day) {
        return day >= 1 && day <= 31;
    }

    /**
     * Predicate for filtering cards before a {@code date}
     * @param date before this date
     * @return
     */
    public static Predicate<Card> before(LocalDateTime date) {
        return c -> c.getSchedule().getNextReview()
            .isBefore(date.plusDays(1).minusNanos(1));
    }

    public static Comparator<Card> getByDate() {
        Comparator<Card> byDate = Comparator.comparing(Card::getSchedule);
        return byDate;
    }

    public LocalDateTime getNextReview() {
        return nextReview;
    }

    public void setNextReview(LocalDateTime date) {
        this.nextReview = date;
    }

    public int getLastInterval() {
        return lastInterval;
    }

    public double getEasingFactor() {
        return easingFactor;
    }

    public double getSuccessRate() {
        double successRate = success / (double) (success + failure + 1);
        return successRate;
    }

    public double getHistoricalEasingFactor() {
        return historicalEasingFactor;
    }

    public int getLearningPhase() {
        return learningPhase;
    }

    public void setRelativeNextReviewToNow() {
        this.nextReview = LocalDateTime.now();
    }

    /**
     * Feedback router to switch between what to do given a certain
     * confidenceLevel input
     */
    public boolean feedbackHandlerRouter(int confidenceLevel) {
        checkArgument(isValidConfidenceLevel(confidenceLevel), MESSAGE_ANSWER_CONSTRAINTS);

        boolean tooEasy = false;
        switch (confidenceLevel) {
        case (0):
            // to push card to back of filtered queue
            setRelativeNextReviewToNow();
            break;
        case (1):
            feedback(false);
            setRelativeNextReviewToNow();
            break;
        case (2):
            feedback(true);
            tooEasy = true;
            break;
        default:
            break;
        }
        return tooEasy;
    }

    /**
     * Feedback algorithm takes in whether the answer is correct.
     */
    public void feedback(boolean isSuccess) {
        if (isSuccess) {
            success++;
        } else {
            failure++;
        }

        int total = success + failure;
        double successRate = (double) success / (double) (total + 1);

        if (total >= learningPhase) {
            double newEasingFactor = historicalEasingFactor
                * log(lowerBoundRememberRate)
                / log(successRate);

            if (isSuccess) {
                easingFactor = Math.max(newEasingFactor, 1.1);
            } else {
                easingFactor = Math.min(newEasingFactor, 1.1);
            }

            double count = total - learningPhase + 1;
            double pastFactor = (count - 1.0) / count;
            double nextFactor = 1.0 / count;

            historicalEasingFactor =
                historicalEasingFactor * pastFactor
                    + easingFactor * nextFactor;

            lastInterval = (int) Math.ceil(easingFactor * lastInterval);
        }
        nextReview = nextReview.plusDays((long) lastInterval);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Schedule // instanceof handles nulls
            && this.learningPhase == (((Schedule) other).learningPhase)
            && this.lowerBoundRememberRate
            == (((Schedule) other).lowerBoundRememberRate)
            && this.lastInterval == (((Schedule) other).lastInterval)
            && this.easingFactor == (((Schedule) other).easingFactor)
            && this.success == (((Schedule) other).success)
            && this.failure == (((Schedule) other).failure));
    }

    @Override
    public int compareTo(Schedule otherSchedule) {
        return this.nextReview.compareTo(otherSchedule.getNextReview());
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * get cards due for review before {@code date}
     * @param date before this date
     * @return
     */
    public ObservableList<Card> getReviewList(LocalDateTime date) {
        requireNonNull(date);
        return getReviewList(date, cards.asObservableList());
    }

    /**
     * get cards due for review before {@code date} from a {@code cardsList}
     * @param date before this date
     * @param cardsList from this list
     * @return
     */
    public ObservableList<Card> getReviewList(LocalDateTime date, ObservableList<Card> cardsList) {
        requireAllNonNull(date, cardsList);

        FXCollections.sort(cardsList, Schedule.getByDate());
        logger.fine("Sorting filteredCards List.");

        cardsList = new FilteredList<Card>(cardsList, Schedule.before(date));
        logger.fine("Filtering filteredCards List.");

        ObservableList<Card> filteredList =  FXCollections.observableArrayList();
        for (Card each : cardsList) {
            filteredList.add(each);
        }

        return filteredList;
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public Card getSelectedCard() {
        return selectedCard;
    }

    @Override
    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    @Override
    public void answerSelectedCard(int confidenceLevel) throws NoCardSelectedException {
        if (selectedCard == null) {
            logger.warning("no card selected, unable to apply to null.");
            throw new NoCardSelectedException();
        }
        boolean isTooEasy = selectedCard.getSchedule().feedbackHandlerRouter(confidenceLevel);
        logger.fine("sending answer feedback to scheduler, confidenceLevel: " + confidenceLevel);
        if (isTooEasy) {
            filteredCards.remove(selectedCard);
            emptyAndUnselectCard();
        } else {
            showDueCards(beforeThisDate);
        }
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void setNextReview(LocalDateTime date) throws NoCardSelectedException {
        if (selectedCard == null) {
            logger.warning("no card selected, unable to apply to null.");
            throw new NoCardSelectedException();
        }
        selectedCard.getSchedule().setNextReview(date);
        logger.fine("Setting next review date of card to: " + date.toString());
        showDueCards(beforeThisDate);
    }

    @Override
    public void showDueCards(LocalDateTime date) {
        if (date == null) {
            beforeThisDate = LocalDate.now().atStartOfDay();
        } else {
            beforeThisDate = date;
        }
        filteredCards.setAll(this.addressBook.getReviewList(beforeThisDate, filteredCards));
        logger.fine("Showing cards due before: " + beforeThisDate.toString());
        emptyAndUnselectCard();
    }

    @Override
    public void emptyAndUnselectCard() {
        logger.fine("Clearing back of card.");
        EventsCenter.getInstance().post(new EmptyCardBackEvent());
        this.selectedCard = null;
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Subscribe
    private void handleCardListPanelSelectionEvent(CardListPanelSelectionChangedEvent event) {
        this.selectedCard = event.getNewSelection().card;
    }
```
