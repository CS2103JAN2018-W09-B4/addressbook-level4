package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_THEME = new Prefix("t/");
    public static final Prefix PREFIX_FRONT = new Prefix("f/");
    public static final Prefix PREFIX_BACK = new Prefix("b/");
    public static final Prefix PREFIX_OPTION = new Prefix("o/");
    public static final Prefix PREFIX_CONFIDENCE = new Prefix("c/");
    public static final Prefix PREFIX_DAY = new Prefix("d/");
    public static final Prefix PREFIX_MONTH = new Prefix("m/");
    public static final Prefix PREFIX_YEAR = new Prefix("y/");

    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_ADD_TAG = new Prefix("+t/");
    public static final Prefix PREFIX_REMOVE_TAG = new Prefix("-t/");

}
