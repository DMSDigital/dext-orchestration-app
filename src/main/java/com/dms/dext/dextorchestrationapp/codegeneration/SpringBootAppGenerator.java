package com.dms.dext.dextorchestrationapp.codegeneration;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SpringBootAppGenerator {

  private static final String JAVA_SPRING_BOOT_APP_TEMPLATE = "src/main/resources/template/SpringBootApp.ftl";
  private static final String JAVA_APP_CONTROLER_TEMPLATE = "src/main/resources/template/AppController.ftl";
  private static final String POM_TEMPLATE = "src/main/resources/template/pom.ftl";
  private static final String JAVA_CLASS_EXT = ".java";
  private static final String APP_CONTROLLER_JAVA = "AppController.java";
  private static final String MAIN_FOLDER = "src"+File.separator + "main" + File.separator
  + "java" + File.separator + "com"+File.separator+"dms"+File.separator+"dext";

  private Configuration cfg;

  public SpringBootAppGenerator() {
    cfg = new Configuration(Configuration.VERSION_2_3_0);
  }

  public void generateNewApp(String appName) {
    Map<String, Object> parameters = getSpringBootAppParameters(appName);
    generateSpringBootAppClass(appName, parameters);
    generateAppController(appName, parameters);
    generatePomFile(appName, parameters);
  }

  private void generateSpringBootAppClass(String appName, Map<String, Object> parameters) {
    generateFile(appName,
        JAVA_SPRING_BOOT_APP_TEMPLATE,
        getSpringBootClassName(appName) + JAVA_CLASS_EXT,
        MAIN_FOLDER + File.separator + appName,
        parameters);
  }

  private void generateAppController(String appName, Map<String, Object> parameters) {
    generateFile(appName,
        JAVA_APP_CONTROLER_TEMPLATE,
        APP_CONTROLLER_JAVA,
        MAIN_FOLDER + File.separator + appName + File.separator + "rest",
        parameters);
  }

  private void generatePomFile(String appName, Map<String, Object> parameters) {
    generateFile(appName,
        POM_TEMPLATE,
        "pom.xml",
        "",
        parameters);
  }

  private void generateFile(String appName, String templateName, String fileName,
      String packageName, Map<String, Object> parameters) {
    try {
      Template template = cfg.getTemplate(templateName);
      String dir = System.getProperty("java.io.tmpdir")
          + appName
          + File.separator
          + packageName;
      File directory = new File(dir);
      if (!directory.exists()){
        //noinspection ResultOfMethodCallIgnored
        directory.mkdirs();
      }

      String filePath = dir + File.separator + fileName;
      System.out.println(String.format("Saving %s",filePath));

      Writer file = new FileWriter(new File(filePath));
      template.process(parameters, file);
      file.flush();
      file.close();
    } catch (IOException | TemplateException e) {
      //TODO use logger to log exceptions
      e.printStackTrace();
    }
  }

  private Map<String, Object> getSpringBootAppParameters(String appName) {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("package", "com.dms.dext." + appName);
    parameters.put("className", getSpringBootClassName(appName));
    parameters.put("appName",appName);
    return parameters;
  }

  private String getSpringBootClassName(String appName) {
    return String
        .format("%sApplication", appName.substring(0, 1).toUpperCase() + appName.substring(1));
  }
}
