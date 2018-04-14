package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_0DAY_OF_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_29FEBRUARY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_30FEBRUARY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_31APRIL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_32DAY_OF_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_32MARCH;
import static seedu.address.logic.commands.CommandTestUtil.LIST_DAY_MONTH_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.LIST_PREFIX_RUBBISH;
import static seedu.address.logic.commands.CommandTestUtil.LIST_VALID_DAY_MONTH_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_NUMBER;
import static seedu.address.model.card.Schedule.MESSAGE_DAY_CONSTRAINTS;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ShowDueCommand;

//@@author pukipuki
public class ShowDueCommandParserTest {
    private LocalDateTime todaysDate;
    private ShowDueCommandParser parser = new ShowDueCommandParser();

    @Before
    public void setUp() {
        todaysDate = LocalDate.now().atStartOfDay();
    }

    //@@author pukipuki
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

    /**
     * Generates all possible inputs for optional Schedule inputs
     */
    public static String[] preparePowerSetString(String[] list, boolean haveSpaces) {
        int sizeOfSet = (int) Math.pow(2, list.length);
        String space = "";
        if (haveSpaces) {
            space = " ";
        }
        String[] powerSet = new String[sizeOfSet];
        for (int i = 0; i < sizeOfSet; i++) {
            String temp = "";
            for (int j = 0; j < list.length; j++) {
                if ((i & (1L << j)) != 0) {
                    temp = temp + space + list[j];
                }
            }
            powerSet[i] = temp;
        }
        return powerSet;
    }

    /**
     * Generates all possible {@code LocalDateTime} answer output for optional Schedule inputs
     */
    public static LocalDateTime[] preparePowerSetDateTime(long[] list) {
        int sizeOfSet = (int) Math.pow(2, list.length);
        LocalDateTime[] powerSet = new LocalDateTime[sizeOfSet];
        for (int i = 0; i < sizeOfSet; i++) {
            LocalDateTime temp = LocalDate.now().atStartOfDay();
            for (int j = 0; j < list.length; j++) {
                if ((i & (1L << j)) != 0) {
                    switch (j) {
                    case (0):
                        temp.plusDays(list[j]);
                        break;
                    case (1):
                        temp.plusMonths(list[j]);
                        break;
                    case (2):
                        temp.plusYears(list[j]);
                        break;
                    default :
                        break;
                    }
                }
            }
            powerSet[i] = temp;
        }
        return powerSet;
    }
}
