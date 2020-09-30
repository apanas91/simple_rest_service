package service.controller.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/test/book")
public class BookController {

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("text", "QA");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("text", "Dev");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("text", "DevOps");
        }});
    }};


    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }
}



