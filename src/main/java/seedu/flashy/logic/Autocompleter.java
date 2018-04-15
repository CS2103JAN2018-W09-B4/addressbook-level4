package seedu.flashy.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.flashy.logic.commands.AddCardCommand;
import seedu.flashy.logic.commands.ChangeThemeCommand;
import seedu.flashy.logic.commands.ClearCommand;
import seedu.flashy.logic.commands.Command;
import seedu.flashy.logic.commands.DeleteCardCommand;
import seedu.flashy.logic.commands.DeleteCommand;
import seedu.flashy.logic.commands.EditCardCommand;
import seedu.flashy.logic.commands.EditCommand;
import seedu.flashy.logic.commands.ExitCommand;
import seedu.flashy.logic.commands.FindCommand;
import seedu.flashy.logic.commands.HelpCommand;
import seedu.flashy.logic.commands.ListCommand;
import seedu.flashy.logic.commands.RedoCommand;
import seedu.flashy.logic.commands.SelectCardCommand;
import seedu.flashy.logic.commands.SelectCommand;
import seedu.flashy.logic.commands.ShowDueCommand;
import seedu.flashy.logic.commands.UndoCommand;

//@@author yong-jie
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

    /**
     * Checks if the input text given is a valid candidate
     * for autocompletion.
     * @param input The input text
     */
    public static Boolean isValidAutocomplete(String input) {
        return getCommandWords()
                .stream()
                .filter(word -> input.startsWith(word))
                .collect(Collectors.toList())
                .size() > 0;
    }

    private static List<String> getAutocompleteTexts() {
        return getCommandFields("AUTOCOMPLETE_TEXT");
    }

    private static List<String> getCommandWords() {
        return getCommandFields("COMMAND_WORD");
    }

    /**
     * Fetches the field of a class programatically using strings,
     * removing the need for hardcode.
     * @param field A string indicating the field to access
     */
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

        // Must be added in increasing specificity so that add is not
        // overridden by addc, for example.
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
        commands.add(RedoCommand.class);
        commands.add(SelectCommand.class);
        commands.add(SelectCardCommand.class);
        commands.add(ShowDueCommand.class);
        commands.add(UndoCommand.class);

        return commands;
    }
}
