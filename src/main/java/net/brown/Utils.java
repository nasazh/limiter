package net.brown;

import java.io.IOException;

/**
 * Created by nasaz_000 on 2017-01-07.
 */
public class Utils {

    static public void shutdown() throws IOException {

        System.out.println("Shutdown");
        /**
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("shutdown -s -t 0");
         */
    }
}
