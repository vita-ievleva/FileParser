package fileparser.service.parse.impl;

import fileparser.service.parse.PatternService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatternServiceImpl implements PatternService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String formatDateToParseFile(String dateToBeParse) {
        dateToBeParse = dateToBeParse.substring(5, 7) + "\\/" + dateToBeParse.substring(8, 10) + "\\/" + dateToBeParse.substring(0, 4);

        logger.debug("Converted date to parse file is " + dateToBeParse);
        return dateToBeParse;
    }

    @Override
    public String createSearchPattern(List<String> searchCriteria, String date) {
        List<String> queries = new ArrayList<>();
        date = formatDateToParseFile(date);
        logger.debug("Creating Search Pattern with date = [" + date + "]");

        StringBuilder sb = new StringBuilder();

        // if one criteria
        if (searchCriteria.size() == 1) {
            sb.append("(^\\b").append(date).append("\\b).*?").append("(\\b%s\\b)");
            queries = searchCriteria;
        } else if (searchCriteria.get(1).equalsIgnoreCase("AND")) {
            sb.append("(^\\b").append(date).append("\\b).*?").append("(\\b%s\\b$)");
        } else if (searchCriteria.get(1).equalsIgnoreCase("OR")) {
            sb.append("(^\\b").append(date).append("\\b).*?").append("(\\b%s\\b$)");
        }

        if (searchCriteria.size() > 1) {
            String finalDate = date;
            String finalDate1 = date;
            searchCriteria.forEach(item -> {
                if (item.equalsIgnoreCase("AND")) {
                    sb.append("(.|\\n|\\r)*(^\\b").append(finalDate).append("\\b).*?").append("(\\b%s\\b$)");
                } else if (item.equalsIgnoreCase("OR")) {
                    sb.append("|(^\\b").append(finalDate1).append("\\b).*").append("(\\b%s\\b$)");
                }
            });
            queries = // get queries without conditions AND OR
                    searchCriteria.stream().filter(item -> !(item.equalsIgnoreCase("AND")
                            || item.equalsIgnoreCase("OR"))).collect(Collectors.toList());
        }

        String pattern = String.format(sb.toString(), queries.toArray());
        logger.debug("Search Pattern is created = " + pattern);

        return pattern;
    }
}