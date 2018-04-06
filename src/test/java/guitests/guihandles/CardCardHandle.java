package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle for {@code CardCard}
 */
public class CardCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String FRONT_FIELD_ID = "#front";

    private final Label idLabel;
    private final Label frontLabel;

    public CardCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.frontLabel = getChildNode(FRONT_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getFront() {
        return frontLabel.getText();
    }
}
