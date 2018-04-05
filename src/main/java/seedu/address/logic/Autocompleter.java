package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCardCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCardCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShowDueCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Abstracts the logic of collating the commands, COMMAND_WORDs and AUTOCOMPLETE_TEXTs, and
 * determining the eligibility of command box text replacement.
 */
public class Autocompleter {

    public static String getAutocompleteText(String input) {
        return getAutocompleteTexts()
                .stream()
                .filter(text -> text.startsWith(input))
                .collect(Collectors.toList())
                .get(0);
    }

    public static Boolean isValidAutocomplete(String input) {
        return getCommandWords()
                .stream()
                .filter(word -> input.startsWith(word))
                .collect(Collectors.toList())
                .size() > 0;
    }

    private static List<String> getAutocompleteTexts() {
        return getCommandFields("MESSAGE_USAGE");
    }

    private static List<String> getCommandWords() {
        return getCommandFields("COMMAND_WORD");
    }

    private static List<String> getCommandFields(String field) {
        return getCommandClasses().stream().map(command -> {
            try {
                return (String) command.getField(field).get(null);
            } catch (NoSuchFieldException e) {
                return "";
            } catch (IllegalAccessException e) {
                return "";
            }
        }).collect(Collectors.toList());
    }

    private static List<Class<? extends Command>> getCommandClasses() {
        List<Class<? extends Command>> commands = new ArrayList<>();

        // Must be added in decreasing specificity so that addc is not
        // overridden by add, for example.
        commands.add(AddCommand.class);
        commands.add(AddCardCommand.class);
        commands.add(ChangeThemeCommand.class);
        commands.add(ClearCommand.class);
        commands.add(DeleteCommand.class);
        commands.add(DeleteCardCommand.class);
        commands.add(EditCommand.class);
        commands.add(EditCardCommand.class);
        commands.add(ExitCommand.class);
        commands.add(FindCommand.class);
        commands.add(HelpCommand.class);
        commands.add(ListCommand.class);
        commands.add(ListCardCommand.class);
        commands.add(RedoCommand.class);
        commands.add(SelectCommand.class);
        commands.add(SelectCardCommand.class);
        commands.add(ShowDueCommand.class);
        commands.add(UndoCommand.class);

        return commands;
    }
}
