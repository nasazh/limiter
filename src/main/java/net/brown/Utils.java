package net.brown;

import java.io.IOException;

public class Utils {

    static public void shutdown() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("shutdown -s -t 0");
    }
}
