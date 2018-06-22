package ${package}.rest;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/${appName}")
public class AppController {

  @Autowired
  private CamelContext context;

  @RequestMapping(value="")
  public String index() {
    return "${appName} app started";
  }
}
