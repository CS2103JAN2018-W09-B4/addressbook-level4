package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.ui.UiManager.VALID_THEMES;

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
import seedu.address.model.card.McqCard;
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

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
        String trimmedCard = option.trim();
        if (!Card.isValidCard(trimmedCard)) {
            throw new IllegalValueException(McqCard.MESSAGE_MCQ_CARD_CONSTRAINTS);
        }
        return trimmedCard;
    }

    /**
     * Parses a {@code String front, back, Set<String> options} into an {@code McqCard}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given parameters are invalid.
     */
    public static McqCard parseMcqCard(String front, String back, Set<String> options) throws IllegalValueException {
        requireNonNull(front, back);
        requireAllNonNull(options);
        if (!McqCard.isValidMcqCard(back, options)) {
            throw new IllegalValueException(McqCard.MESSAGE_MCQ_CARD_ANSWER_CONSTRAINTS);
        }
        return new McqCard(front, back, options);
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
        return front.isPresent() ? front : Optional.empty();
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
        return back.isPresent() ? back : Optional.empty();
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

    //@@author jethrokuan
    /**
     * Parses a {@code String tag} into a {@code Tag}
     * Leading and trailing whitespaces will be trimmed
     */
    public static Set<Tag> parseTags(List<String> tagNames) throws IllegalValueException {
        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            if (!Name.isValidName(tagName)) {
                throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
            }
            tags.add(new Tag(new Name(tagName)));
        }

        return tags;
    }
    //@@author
}
