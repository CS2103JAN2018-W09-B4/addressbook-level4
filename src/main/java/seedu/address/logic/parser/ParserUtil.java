package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.ui.UiManager.VALID_THEMES;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.card.Card;
import seedu.address.model.card.FillBlanksCard;
import seedu.address.model.card.McqCard;
import seedu.address.model.card.Schedule;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";
    public static final String MESSAGE_INVALID_THEME =
        "Theme must be one of " + String.join(", ", VALID_THEMES);
    public static final String MESSAGE_INVALID_NUMBER = "Not a number, please put a valid number.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

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

    //@@author pukipuki

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
    //@@author

    //@@author jethrokuan

    /**
     * Parses a {@code String tag} into a {@code Tag}
     * Leading and trailing whitespaces will be trimmed
     */
    public static Optional<Set<Tag>> parseTags(List<String> tagNames) throws IllegalValueException {
        if (tagNames.isEmpty()) {
            return Optional.empty();
        }

        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            if (!Name.isValidName(tagName)) {
                throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
            }
            tags.add(new Tag(new Name(tagName.trim())));
        }

        return Optional.of(tags);
    }
    //@@author
}
