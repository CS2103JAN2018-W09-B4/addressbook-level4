package seedu.flashy.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.flashy.commons.exceptions.DataConversionException;
import seedu.flashy.commons.util.XmlUtil;

/**
 * Stores cardbank data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given cardbank data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableCardBank cardBank)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, cardBank);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns flashy book in the file or an empty flashy book
     */
    public static XmlSerializableCardBank loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCardBank.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
