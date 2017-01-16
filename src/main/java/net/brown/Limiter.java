package net.brown;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Scanner;

import static net.brown.Utils.shutdown;

public class Limiter {

    private static final long MINUTE_IN_MILLIS = 1000 * 60;

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || args.length == 1){
            System.out.println("Username and/or time limit are not correctly set");
            return;
        }
        String userName = System.getProperty("user.name");
        if (!userName.equals(args[0])) {
            return;
        }
        int allowedTime = 0;
        try {
           allowedTime = Integer.valueOf(args[1]);
        } catch (NumberFormatException e){
            System.out.println("Time limit is not correctly set");
            return;
        }

        int usedTime = readUsedTime(userName);
        Notification notification = new Notification(allowedTime);
        while (usedTime < allowedTime) {
            Thread.sleep(MINUTE_IN_MILLIS);
            usedTime = incrementUsedTime(userName);
            int remainingTime = allowedTime - usedTime;
            notification.setRemainingTime(remainingTime);
        }

        notification.removeTrayIcon();
        shutdown();
    }

    private static int incrementUsedTime(String userName) throws Exception {
        LocalDate date = LocalDate.now();
        Path filePath = Paths.get(userName + date + ".limit");
        Scanner scanner = new Scanner(filePath);
        if (scanner.hasNextInt()) {
            int currentTime = scanner.nextInt();
            String bytesToWrite = "" + ++currentTime;
            Files.write(filePath, bytesToWrite.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            return currentTime;
        } else {
            return Integer.MAX_VALUE;
        }

    }

    private static int readUsedTime(String userName) throws IOException {
        LocalDate date = LocalDate.now();
        Path filePath = Paths.get(userName + date + ".limit");
        File file = filePath.toFile();
        if (file.exists()) {
            Scanner scanner = new Scanner(filePath);
            return scanner.nextInt();
        } else {
            Files.write(filePath, "0".getBytes());
            return 0;
        }
    }
}
