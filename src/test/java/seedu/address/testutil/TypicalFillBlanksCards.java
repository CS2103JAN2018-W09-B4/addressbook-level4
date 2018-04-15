package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.FillBlanksCard;

//@@author shawnclq
/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalFillBlanksCards {

    public static final FillBlanksCard MATHEMATICS_FILLBLANKS_CARD = new FillBlanksCardBuilder().build();
    public static final FillBlanksCard PHYSICS_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("When an electron goes from _ energy to a _ energy level, it emits a photon")
            .withBack("higher, lower").build();
    public static final FillBlanksCard SCIENCE_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("Density is defined by _ divided by _")
            .withBack("mass, volume").build();
    public static final FillBlanksCard HISTORY_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("World War II occured between year _ and _")
            .withBack("1939, 1945").build();
    public static final FillBlanksCard ECONOMICS_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("A demand curve is _ sloping while a supply curve is _ sloping")
            .withBack("downward, upward").build();
    public static final FillBlanksCard GEOGRAPHY_FILLBLANKS_CARD = (FillBlanksCard) new FillBlanksCardBuilder()
            .withFront("In terms of continent, Singapore is in _, Canada is in _, "
                    + "England is in _ and New Zealand is in _")
            .withBack("Asia, North America, Europe, Australia").build();

    private TypicalFillBlanksCards() {} // prevents instantiation

    public static List<FillBlanksCard> getTypicalFillBlanksCards() {
        return Arrays.asList(MATHEMATICS_FILLBLANKS_CARD, HISTORY_FILLBLANKS_CARD, PHYSICS_FILLBLANKS_CARD,
                SCIENCE_FILLBLANKS_CARD, ECONOMICS_FILLBLANKS_CARD, GEOGRAPHY_FILLBLANKS_CARD);
    }

}
