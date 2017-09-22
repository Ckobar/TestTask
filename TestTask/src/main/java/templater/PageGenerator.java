package templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by Алексей on 22.09.2017.
 */
public class PageGenerator {
    private static final String HTML_DIR = "templates";
    private static final Configuration CFG = new Configuration();

    /**
     * Displays the specified html
     * @param filename
     * @param data
     * @return
     */
    public static String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (TemplateException e){
            e.printStackTrace();
        }
        return stream.toString();
    }
}
