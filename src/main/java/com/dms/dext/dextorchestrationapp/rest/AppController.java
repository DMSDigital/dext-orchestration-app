package com.dms.dext.dextorchestrationapp.rest;

import com.dms.dext.dextorchestrationapp.codegeneration.SpringBootAppGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/app")
public class AppController {

  @Autowired
  SpringBootAppGenerator springBootAppGenerator;

  @RequestMapping(value = "/create/{appName}",method = RequestMethod.POST)
  public String create(@PathVariable("appName") String appName) {
    springBootAppGenerator.generateNewApp(appName);
    return String.format("%s app created",appName);
  }
}
