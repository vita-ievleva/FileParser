package fileparser.controller;

import fileparser.service.parse.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchFileParserController {

    @Autowired
    private SearchService searchService;

    @PostMapping(value = "/search")
    public ResponseEntity<?> parseFile(@RequestBody Map<String, List<String>> searchQuery) throws Exception {
        Map<String, String> response = searchService.searchInfoFromLogFiles(searchQuery);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
