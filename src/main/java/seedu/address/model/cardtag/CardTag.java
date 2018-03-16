package seedu.address.model.cardtag;

public class CardTag {
    private static CardTag ourInstance = new CardTag();

    public static CardTag getInstance() {
        return ourInstance;
    }

    private CardTag() {
    }
}
