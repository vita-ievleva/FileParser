package fileparser.service.parse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.io.File.separator;


public class LogFileUtils {

    private static Logger logger = LoggerFactory.getLogger(LogFileUtils.class);

    public static List<String> readFileToString(String pathToFile) {
        logger.debug("Getting content of log file: [" + pathToFile + "]");

        String result = null;

        try (Stream<String> stream = Files.lines(Paths.get(pathToFile))) {

            result = stream.map(line ->
                    line.replaceAll("^[\\d]+[.]{1}[\\d.]*[a-zA-Z\\d]*,", ""))
                    .map(line -> line.replaceAll("[\\d]+[:]{1}[\\d:.,,]+", ""))

                    .collect(Collectors.joining("\n", "", ""));

        } catch (IOException e) {
            logger.error("FAILED TO READ LOG FILE FROM SERVER", e);
        }

        if (result != null) return Arrays.asList(result.split("start,newcall,"));
        else return null;
    }

    public static String createUrl(String ipAddress, String appName, String date) {
        logger.debug("Creating Path to File for server ip = " + ipAddress + ", and App = " + appName);
        StringBuilder sb = new StringBuilder();

        System.out.println("\n\n\n\n\n");
        System.out.println(System.getProperty("user.dir"));

        Path s = Paths.get("");
        logger.debug("Current relative path is: " + s);

        if (!ipAddress.contains(".")) { // to test locally
            sb.append(System.getProperty("user.dir"));
        } else {
            sb.append("\\\\").append(ipAddress).append(separator).append("c$");
        }
        sb.append(separator).append("Cisco").append(separator)
                .append("CVP").append(separator)
                .append("VXMLServer").append(separator)
                .append("applications").append(separator)
                .append(appName).append(separator)
                .append("logs").append(separator)
                .append("ActivityLog").append(separator)
                .append("activity_log").append(date).append(".txt");

        logger.debug("Created url is " + sb.toString());
        return sb.toString();
    }

}
