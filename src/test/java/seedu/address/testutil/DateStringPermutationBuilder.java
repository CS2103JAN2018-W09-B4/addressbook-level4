package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * parsing of date is made more intelligent and flexible
 * therefore there are a lot more test cases possible
 * need to make power set of input scenarios
 */
public class DateStringPermutationBuilder {
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
                    default:
                        break;
                    }
                }
            }
            powerSet[i] = temp;
        }
        return powerSet;
    }
}
