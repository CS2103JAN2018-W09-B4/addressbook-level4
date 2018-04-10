package seedu.address.testutil;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting string.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    /**
     * Returns the middle index of the tag in the {@code model}'s tag list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getCardBank().getTagList().size() / 2);
    }

    /**
     * Returns the last index of the tag in the {@code model}'s tag list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getCardBank().getTagList().size());
    }

    /**
     * Returns the tag in the {@code model}'s tag list at {@code index}.
     */
    public static Tag getTag(Model model, Index index) {
        return model.getCardBank().getTagList().get(index.getZeroBased());
    }

    /**
     * Returns the middle index of the card in the {@code model}'s card list.
     */
    public static Index getCardMidIndex(Model model) {
        return Index.fromOneBased(model.getCardBank().getCardList().size() / 2);
    }

    /**
     * Returns the last index of the tag in the {@code model}'s tag list.
     */
    public static Index getCardLastIndex(Model model) {
        return Index.fromOneBased(model.getCardBank().getCardList().size());
    }

    /**
     * Returns the card in the {@code model}'s card list at {@code index}.
     */
    public static Card getCard(Model model, Index index) {
        return model.getCardBank().getCardList().get(index.getZeroBased());
    }
}
