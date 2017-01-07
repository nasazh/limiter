package net.brown;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Created by nasaz_000 on 2017-01-07.
 */
public class Notification {

    private final TrayIcon trayIcon;
    private final SystemTray tray;
    private int remainingTime = 0;
    private static final Supplier<IntStream> notificationListSupplier = () -> Arrays.stream(new int[]{15, 10, 5, 3, 2, 1});

    public Notification(int remainingTime) throws AWTException {
        this.remainingTime = remainingTime;
        tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("hourglass.png");
        trayIcon = new TrayIcon(image, "Limiter");
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> displayTray());
        tray.add(trayIcon);
    }

    public void setRemainingTime(int remainingTime){
        this.remainingTime = remainingTime;
        if (notificationListSupplier.get().anyMatch(x -> x == remainingTime)){
            displayTray();
        }
    }


    public void displayTray(){
        trayIcon.displayMessage("Time is running out", "Time left:  " + remainingTime, TrayIcon.MessageType.WARNING);
    }

    public void removeTrayIcon(){
        tray.remove(trayIcon);
    }
}
