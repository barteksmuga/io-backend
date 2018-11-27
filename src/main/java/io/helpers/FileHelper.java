package io.helpers;

public class FileHelper {

    public static String getOriginalFileNameWithouExtension (String originalFileName) {
        if (!originalFileName.contains(".")) {
            return originalFileName;
        }
        int lastDotIndex = originalFileName.lastIndexOf(".");
        return originalFileName.substring(0, lastDotIndex);
    }
}
