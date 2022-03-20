package com.company.javaCore.save;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGame(String s, GameProgress gameProgress) throws IOException {
        File file = new File(s);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
            outputStream.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<File> listOfFiles(String path) {
        File dir = new File(path);
        List<File> list = new ArrayList<>();
        for (File file : dir.listFiles()) {
            list.add(file);
        }
        return list;
    }

    public static void zipFiles(String zipPath, List<File> list) throws FileNotFoundException {

        try (FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            for (File file : list) {

                FileInputStream fileInputStream = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fileInputStream.read(bytes)) >= 0) {
                    zipOutputStream.write(bytes, 0, length);
                }
                fileInputStream.close();
                File file1 = file;
                file1.delete();

            }

        } catch (IOException e) {
            e.getMessage();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {

        GameProgress game1 = new GameProgress(1, 2, 3, 4);
        GameProgress game2 = new GameProgress(1, 2, 3, 4);
        GameProgress game3 = new GameProgress(1, 2, 3, 4);

        saveGame("d://games/savegames/save1.dat", game1);
        saveGame("d://games/savegames/save2.dat", game2);
        saveGame("d://games/savegames/save3.dat", game3);

        Thread.sleep(50);

        zipFiles("d://games/savegames/save.zip", listOfFiles("d://games/savegames/"));


    }
}
