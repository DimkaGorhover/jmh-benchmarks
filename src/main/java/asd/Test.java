package asd;

import java.io.*;

/**
 * @since 2019-08-20
 */
public class Test {

    public static void main(String[] args) throws Exception {


        File logFile = new File(System.getProperty("user.home") + "/log.log");
        System.out.println(logFile.exists());

        new ProcessBuilder()
                .command("java", "-version")
                .directory(new File("."))
                .redirectError(ProcessBuilder.Redirect.appendTo(logFile))
                .redirectOutput(ProcessBuilder.Redirect.appendTo(logFile))
                .start()
                .waitFor();
    }
}
