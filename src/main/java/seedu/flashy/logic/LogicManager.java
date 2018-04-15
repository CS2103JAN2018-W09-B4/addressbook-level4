package seedu.flashy.logic;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.flashy.commons.core.ComponentManager;
import seedu.flashy.commons.core.LogsCenter;
import seedu.flashy.logic.commands.Command;
import seedu.flashy.logic.commands.CommandResult;
import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.logic.parser.CardBankParser;
import seedu.flashy.logic.parser.exceptions.ParseException;
import seedu.flashy.model.Model;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.cardtag.CardTag;
import seedu.flashy.model.tag.Tag;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final CardBankParser cardBankParser;
    private final UndoRedoStack undoRedoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        cardBankParser = new CardBankParser();
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = cardBankParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return model.getFilteredTagList();
    }

    @Override
    public ObservableList<Card> getFilteredCardList() {
        return model.getFilteredCardList();
    }

    @Override
    public CardTag getCardTag() {
        return model.getCardBank().getCardTag();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return FXCollections.unmodifiableObservableList(model.getCardBank().getTagList());
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
