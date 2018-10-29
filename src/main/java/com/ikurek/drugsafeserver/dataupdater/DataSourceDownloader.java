package com.ikurek.drugsafeserver.dataupdater;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import static com.ikurek.drugsafeserver.dataupdater.DataUpdaterConstants.FILE_NAME;
import static com.ikurek.drugsafeserver.dataupdater.DataUpdaterConstants.FILE_URL;

public class DataSourceDownloader {

    public void validateFilePresence() {
        File f = new File(FILE_NAME);
        if (f.exists() && !f.isDirectory()) f.delete();
    }

    public void downloadDrugListAsXML() {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(FILE_URL).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

            bufferedInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}