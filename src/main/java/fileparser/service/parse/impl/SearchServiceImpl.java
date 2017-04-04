package fileparser.service.parse.impl;

import fileparser.dao.CVPServersDao;
import fileparser.model.Server;
import fileparser.service.parse.PatternService;
import fileparser.service.parse.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static fileparser.service.parse.util.LogFileUtils.createUrl;
import static fileparser.service.parse.util.LogFileUtils.readFileToString;
import static fileparser.service.parse.util.QueryBuilder.buildSearchQuery;

@Service
public class SearchServiceImpl implements SearchService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CVPServersDao cvpServersDao;
    @Autowired
    PatternService patterService;

    private String parse(List<String> logFileContent, Pattern pattern) {
        StringBuilder sb = new StringBuilder();
        logFileContent.forEach(content -> {
            logger.debug("Start parsing..\n" + content);

            if (pattern.matcher(content).find()) {
                logger.debug("Find Matching content!");
                sb.append(content);
            }
        });
        return sb.toString();
    }

    private Pattern getPatternForRequest(List<String> searchCriteria, String date) {
        String pattern = patterService.createSearchPattern(searchCriteria, date);
        return Pattern.compile(pattern, Pattern.DOTALL | Pattern.MULTILINE);
    }

    @Override
    public Map<String, String> searchInfoFromLogFiles(Map<String, List<String>> searchQuery) {
        Map<String, String> results = new LinkedHashMap<>();

        List<String> serversListFromClient = searchQuery.get("servers");
        List<String> apps = searchQuery.get("apps");
        List<String> searchOption = searchQuery.get("searchOption");
        List<String> searchValue = searchQuery.get("searchValue");
        String date = searchQuery.get("date").get(0);

        List<Server> server = cvpServersDao.getServers();

        for (int i = 0; i < serversListFromClient.size(); i++) {
            int finalI = i;
            server.forEach(ss -> {
                if (serversListFromClient.get(finalI).equalsIgnoreCase(ss.getName())) {
                    serversListFromClient.set(finalI, ss.getIpAddress());
                }
            });
        }

        List<String> searchConditions = buildSearchQuery(searchOption, searchValue);
        Pattern pattern = getPatternForRequest(searchConditions, date);

        for (String appName : apps) {
            for (String ipAddress : serversListFromClient) {

                String logFilePath = createUrl(ipAddress, appName, date);
                List<String> content = readFileToString(logFilePath);

                if (content == null) break;

                long startTime = System.currentTimeMillis();
                String searchResult = parse(content, pattern);
                long endTime = System.currentTimeMillis();

                logger.warn("Total execution time for pattern " + pattern + ": " + (endTime - startTime) + "ms");

                if (searchResult.isEmpty()) {
                    // search in previous file if result is empty and final result will be used
                    logFilePath = (getPreviousFilePath(logFilePath));
                    content = readFileToString(logFilePath);

                    if (content == null) break;
                    startTime = System.currentTimeMillis();

                    searchResult = parse(content, pattern);

                    endTime = System.currentTimeMillis();
                    logger.warn("Total execution time for pattern " + pattern + ": " + (endTime - startTime) + "ms");

                    results.put(appName, (searchResult));
                } else {
                    results.put(appName, (searchResult));
                    break;

                }
            }
        }
        return results;
    }

    private String getPreviousFilePath(String filePath) {
        logger.debug("Getting information in previous file " + filePath);
        String dateOfFileName = filePath.substring(filePath.length() - 14, filePath.length() - 4);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateOfFileName, formatter);

        return filePath.replace(filePath.substring(filePath.length() - 14, filePath.length() - 4),
                date.minusDays(1).toString());

    }
}