package seedu.address.testutil;

import seedu.address.model.card.FillBlanksCard;
//@@author shawnclq
/**
 * A utility class to help with building Card objects.
 */
public class FillBlanksCardBuilder extends CardBuilder {

    public static final String DEFAULT_FRONT = "A square is a polygon with _ side meeting at _ angles";
    public static final String DEFAULT_BACK = "equal, right";

    public FillBlanksCardBuilder() {
        super();
        super.front = DEFAULT_FRONT;
        super.back = DEFAULT_BACK;
    }

    /**
     * Initializes the CardBuilder with the data of {@code tagToCopy}.
     */
    public FillBlanksCardBuilder(FillBlanksCard cardToCopy) {
        super(cardToCopy);
    }

    @Override
    public FillBlanksCard build() {
        return new FillBlanksCard(id, front, back);
    }

}
