package com.ahutoj.config;

import com.ahutoj.AhutOjForumApplication;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServerRuleConfig
{
    private static HashMap<Object, Object> properties = new HashMap<>();

    public Integer ASTRICT_USER_THUMBSUP_SOLUTION_TIME;
    public Integer ASTRICT_USER_PUBLISH_SOLUTION_TIME;
    public Integer ASTRICT_USER_PUBLISH_SOLUTION_COMMENT_TIME;
 
    public ServerRuleConfig()
    {
        Yaml yaml = new Yaml();
        try (InputStream in = ServerRuleConfig.class.getClassLoader().getResourceAsStream("application.yml");)
        {
            properties = yaml.loadAs(in, HashMap.class);
            ASTRICT_USER_THUMBSUP_SOLUTION_TIME = (Integer) getValue("server-rule-config.astrict-user-thumbsup-solution-time");
            ASTRICT_USER_PUBLISH_SOLUTION_TIME = (Integer) getValue("server-rule-config.astrict-user-publish-solution-time");
            ASTRICT_USER_PUBLISH_SOLUTION_COMMENT_TIME = (Integer) getValue("server-rule-config.astrict-user-publish-solution-comment-time");

        } catch (Exception e)
        {
            AhutOjForumApplication.log.error("Init ServerRuleConfig failed !", e);
        }
    }

    public Object getValue(String key)
    {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator))
        {
            separatorKeys = key.split("\\.");
        }
        else
        {
            return properties.get(key);
        }
        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++)
        {
            if (i == 0)
            {
                finalValue = (Map) properties.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null)
            {
                break;
            }
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }

}
