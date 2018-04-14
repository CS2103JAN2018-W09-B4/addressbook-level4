package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.FillBlanksCard;
import seedu.address.model.card.McqCard;
import seedu.address.model.tag.Name;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_MCQ_BACK_INT = "4";
    private static final String INVALID_MCQ_BACK_VALUE = "Hello World";
    private static final String INVALID_NAME = "M@th";
    private static final String INVALID_THEME = "solarized";

    private static final String VALID_BACK = "Flashy";
    private static final String VALID_FRONT = "What is the best flashcard app?";
    private static final String VALID_MCQ_BACK = "2";
    private static final String VALID_MCQ_FRONT = "Which continent is Singapore in?";
    private static final String VALID_NAME = "Math";
    private static final List<String> VALID_MCQ_OPTIONS =
            Arrays.asList("Australia", "Asia", "Africa");
    private static final String VALID_FILLBLANKS_FRONT =
            "A _ is a four sided polygon with equal sides meeting at right angles.";
    private static final String INVALID_FILLBLANKS_ARGUMENT = " ";
    private static final String VALID_FILLBLANKS_BACK = "square";
    private static final String INVALID_FILLBLANKS_BACK_ARGUMENTS = "square, too, many, arguments";

    private static final String VALID_OPTION = "Asia";
    private static final String VALID_THEME = "light";
    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_TAG, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TAG, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((Optional<String>) null));
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseName(INVALID_NAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseName(Optional.of(INVALID_NAME)));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
        assertEquals(Optional.of(expectedName), ParserUtil.parseName(Optional.of(VALID_NAME)));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
        assertEquals(Optional.of(expectedName), ParserUtil.parseName(Optional.of(nameWithWhitespace)));
    }

    @Test
    public void parseCard_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCard((String) null));
    }

    @Test
    public void parseCard_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseCard(WHITESPACE));
    }

    @Test
    public void parseCard_validValueWithoutWhitespace_returnsName() throws Exception {
        assertEquals(VALID_FRONT, ParserUtil.parseCard(VALID_FRONT));
    }

    @Test
    public void parseCard_validValueWithWhitespace_returnsTrimmedCard() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_FRONT + WHITESPACE;
        assertEquals(VALID_FRONT, ParserUtil.parseCard(nameWithWhitespace));
    }

    @Test
    public void parseMcqOption_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMcqOption((String) null));
    }

    @Test
    public void parseMcqOption_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseMcqOption(WHITESPACE));
    }

    @Test
    public void parseMcqOption_validValueWithoutWhitespace_returnsString() throws Exception {
        assertEquals(VALID_OPTION, ParserUtil.parseMcqOption(VALID_OPTION));
    }

    @Test
    public void parseMcqOption_validValueWithWhitespace_returnsString() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_OPTION + WHITESPACE;
        assertEquals(VALID_OPTION, ParserUtil.parseMcqOption(nameWithWhitespace));
    }

    @Test
    public void parseMcqCard_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMcqCard((String) null,
                VALID_MCQ_BACK, VALID_MCQ_OPTIONS));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMcqCard(VALID_MCQ_FRONT,
                (String) null, VALID_MCQ_OPTIONS));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMcqCard(VALID_MCQ_FRONT,
                VALID_MCQ_BACK, (List<String>) null));
    }

    @Test
    public void parseMcqCard_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseMcqCard(VALID_MCQ_FRONT,
                INVALID_MCQ_BACK_INT, VALID_MCQ_OPTIONS));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseMcqCard(VALID_MCQ_FRONT,
                INVALID_MCQ_BACK_VALUE, VALID_MCQ_OPTIONS));
    }

    @Test
    public void parseMcqCard_validValue_returnsMcqCard() throws Exception {
        McqCard expectedMcqCard = new McqCard(VALID_MCQ_FRONT, VALID_MCQ_BACK, VALID_MCQ_OPTIONS);
        assertEquals(expectedMcqCard, ParserUtil.parseMcqCard(VALID_MCQ_FRONT, VALID_MCQ_BACK, VALID_MCQ_OPTIONS));
    }


    @Test
    public void parseFillBlanksCard_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseFillBlanksCard(null,
                VALID_FILLBLANKS_BACK));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseFillBlanksCard(VALID_FILLBLANKS_FRONT,
                null));
    }

    @Test
    public void parseFillBlanksCard_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseFillBlanksCard(VALID_FILLBLANKS_FRONT,
                INVALID_FILLBLANKS_BACK_ARGUMENTS));
    }

    @Test
    public void parseFillBlanksCard_validValue_returnsMcqCard() throws Exception {
        FillBlanksCard expectedFillBlanksCard = new FillBlanksCard(VALID_FILLBLANKS_FRONT, VALID_FILLBLANKS_BACK);
        assertEquals(expectedFillBlanksCard, ParserUtil.parseFillBlanksCard(VALID_FILLBLANKS_FRONT,
                VALID_FILLBLANKS_BACK));
    }

    @Test
    public void parseFront_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseFront((Optional<String>) null));
    }

    @Test
    public void parseFront_invalidValue_throwsIllegalValueException () {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseFront(Optional.of(WHITESPACE)));
    }

    @Test
    public void parseFront_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseFront(Optional.empty()).isPresent());
    }

    @Test
    public void parseFront_validValueWithoutWhitespace_returnsFront() throws Exception {
        assertEquals(Optional.of(VALID_FRONT), ParserUtil.parseFront(Optional.of(VALID_FRONT)));
    }

    @Test
    public void parseBack_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBack((Optional<String>) null));
    }

    @Test
    public void parseBack_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBack(Optional.of(WHITESPACE)));
    }

    @Test
    public void parseBack_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBack(Optional.empty()).isPresent());
    }

    @Test
    public void parseBack_validValueWithoutWhitespace_returnsFront() throws Exception {
        assertEquals(Optional.of(VALID_BACK), ParserUtil.parseBack(Optional.of(VALID_BACK)));
    }

    @Test
    public void parseOptions_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseOptions((List<String>) null));
    }

    @Test
    public void parseOptions_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseOptions(Arrays.asList(WHITESPACE,
                WHITESPACE, WHITESPACE)));
    }

    @Test
    public void parseOptions_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseOptions(Arrays.asList()).isPresent());
    }

    @Test
    public void parseOptions_validValueWithoutWhitespace_returnsFront() throws Exception {
        assertEquals(Optional.of(VALID_MCQ_OPTIONS), ParserUtil.parseOptions(VALID_MCQ_OPTIONS));
    }

    //@@author yong-jie
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
