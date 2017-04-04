package fileparser.service.parse.util;

import fileparser.dao.util.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class QueryBuilder {

    private static Logger logger = LoggerFactory.getLogger(QueryBuilder.class);

    public static List<String> buildSearchQuery(List<String> searchOption, List<String> searchValue) {
        searchOption = replaceUIValueWithSearchKey(searchOption);

        List<String> result = new ArrayList<>();

        for (int i = 0, j = 1; i < searchValue.size(); ++i) {
            result.add(searchOption.get(2 * i) + "=" + searchValue.get(i));
            if (j > searchOption.size() - 1 || searchValue.size() == 1) break;
            result.add(searchOption.get(j));
            j = j + 2;
        }
        logger.debug("Converted query = " + result);

        return result;
    }

    private static List<String> replaceUIValueWithSearchKey(List<String> searchOption) {
        Map<String, String> map = PropertiesReader.getSearchCriteria();

        for (int i = 0; i < searchOption.size(); i++) {
            int finalI = i;
            map.forEach((k, v) -> {
                if (searchOption.get(finalI).equalsIgnoreCase(v))
                    searchOption.set(finalI, k);
            });
        }
        return searchOption;
    }

}
