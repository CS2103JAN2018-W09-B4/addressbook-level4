package seedu.flashy.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.Schedule;

/**
 * Class that creates a card array based on an array of relative days
 */
public class CardArrayBuilder {

    /**
     * Returns an {@code card[]} with prefered schedule days based on {@code days[]}.
     */
    public static Card[] getMapDaysToCardArray(int[] days) {
        Card[] cardArray = new Card[days.length];
        int index = 0;
        LocalDateTime todaysDate = LocalDate.now().atStartOfDay();
        LocalDateTime setDate;
        for (int i : days) {
            setDate = todaysDate.plusDays(i);
            Schedule schedule = new Schedule(setDate);
            Card toAdd = new CardBuilder()
                .withFront(setDate.toLocalDate().toString())
                .withSchedule(schedule).build();
            cardArray[index++] = toAdd;
        }
        return cardArray;
    }
}
