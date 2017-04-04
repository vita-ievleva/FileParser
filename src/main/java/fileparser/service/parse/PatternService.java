package fileparser.service.parse;

import java.util.List;


public interface PatternService {
    String createSearchPattern(List<String> searchCriteria, String date);
}
