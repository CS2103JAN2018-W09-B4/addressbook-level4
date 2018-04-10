package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;

/**
 * A UI component that displays information about a {@code Card}.
 */
public class CardCard extends UiPart<Region> {
    private static final String FXML = "CardListCard.fxml";

    public final Card card;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label front;

    @FXML
    private FlowPane tags;

    public CardCard(Card card, int displayedIndex, List<Tag> tagList) {
        super(FXML);
        this.card = card;
        id.setText(displayedIndex + ". ");
        front.setText(card.getFront());
        tagList.forEach(tag -> tags.getChildren().add(new Label(tag.getName().toString())));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CardCard)) {
            return false;
        }

        CardCard card = (CardCard) other;
        return id.getText().equals(card.id.getText())
                && card.equals(card.card);
    }
}
