package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String cardBankFilePath = "data/cardBank.xml";
    private String cardBankName = "MyCardBank";
    private String theme = "view/DarkTheme.css";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getCardBankFilePath() {
        return cardBankFilePath;
    }

    public void setCardBankFilePath(String cardBankFilePath) {
        this.cardBankFilePath = cardBankFilePath;
    }

    public String getCardBankName() {
        return cardBankName;
    }

    public void setCardBankName(String cardBankName) {
        this.cardBankName = cardBankName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(cardBankFilePath, o.cardBankFilePath)
                && Objects.equals(cardBankName, o.cardBankName)
                && Objects.equals(theme, o.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, cardBankFilePath, cardBankName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + cardBankFilePath);
        sb.append("\nCardBank name : " + cardBankName);
        sb.append("\nTheme : " + theme);
        return sb.toString();
    }

}
