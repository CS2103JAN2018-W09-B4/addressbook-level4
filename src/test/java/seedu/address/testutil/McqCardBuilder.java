package seedu.address.testutil;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.model.card.McqCard;

/**
 * A utility class to help with building Card objects.
 */
public class McqCardBuilder extends CardBuilder {

    public static final String[] DEFAULT_OPTIONS_ARRAY = new String[]{"9th August", "10th August", "9th September"};
    public static final String DEFAULT_BACK = "1";
    public static final LinkedHashSet<String> DEFAULT_OPTIONS =
            new LinkedHashSet<String>(Arrays.asList(DEFAULT_OPTIONS_ARRAY));

    private Set<String> options;

    public McqCardBuilder() {
        super();
        super.back = DEFAULT_BACK;
        options = DEFAULT_OPTIONS;
    }

    /**
     * Initializes the CardBuilder with the data of {@code tagToCopy}.
     */
    public McqCardBuilder(McqCard cardToCopy) {
        super(cardToCopy);
        options = cardToCopy.getOptions();
    }

    /**
     * Sets the {@code options} of the {@code McqCard} that we are building.
     */
    public McqCardBuilder addOption(String option) {
        this.options.add(option);
        return this;
    }

    /**
     * Resets the {@code options} of the {@code McqCard} that we are building.
     */
    public McqCardBuilder resetOptions() {
        this.options = new LinkedHashSet<>();
        return this;
    }

    @Override
    public McqCard build() {
        return new McqCard(id, front, back, options);
    }

}
