package fileparser.service.parse;


import java.util.List;
import java.util.Map;

public interface SearchService {
    Map<String, String> searchInfoFromLogFiles(Map<String, List<String>> searchQuery);

}
