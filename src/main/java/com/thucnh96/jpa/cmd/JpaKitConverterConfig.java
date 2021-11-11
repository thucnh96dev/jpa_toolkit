package com.thucnh96.jpa.cmd;

import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.springframework.util.StringUtils;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :09/11/2021 - 4:00 PM
 */
@Data
public class JpaKitConverterConfig {

    private String url;
    private String username;
    private String password;
    private String prefix;
    private String subfix;

    private String out;
    private String type;

    public static JpaKitConverterConfig create(CommandLine cmd) {
        JpaKitConverterConfig config = new JpaKitConverterConfig();

        if (cmd.hasOption("url")) {
            config.url = cmd.getOptionValue("url");
        }

        if (cmd.hasOption("username")) {
            config.username = cmd.getOptionValue("username");
        }
        if (cmd.hasOption("password")) {
            config.password = cmd.getOptionValue("password");
        }
        if (cmd.hasOption("prefix")) {
            config.prefix = cmd.getOptionValue("prefix");
        }
        if (cmd.hasOption("subfix")) {
            config.subfix = cmd.getOptionValue("subfix");
        }
        if (cmd.hasOption("out")) {
            config.out = cmd.getOptionValue("out");
        }
        if (cmd.hasOption("type")) {
            config.type = cmd.getOptionValue("type");
        }
        return config;
    }

    public String valid() {
        if (StringUtils.isEmpty(url)) {
            return "url  may not be empty.";
        }
        return null;
    }

}
