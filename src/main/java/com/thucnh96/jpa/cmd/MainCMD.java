package com.thucnh96.jpa.cmd;

import com.thucnh96.jpa.modal.DbType;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :09/11/2021 - 4:56 PM
 */
public class MainCMD {

    private static void help(Options options) {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("java -jar excel-to-json.jar or visit local http://localhost:8080 ", options);
    }
    public static void main(String[] args) {
        try {
            Options options = new Options();
            options.addOption("?", "help", true, "This help text.");
            options.addOption(new Option("url", "url connect to datasource."));
            options.addOption(new Option("username", "user datasource."));
            options.addOption(new Option("password", "password datasource."));
            options.addOption(new Option("schema", "type datasource in ( " + String.join(",", Arrays.stream(DbType.values()).map(e -> e.name()).collect(Collectors.toList())) + " )" ));
            options.addOption(new Option("prefix", "table startWiths"));
            options.addOption(new Option("subfix", "table endWiths"));
            options.addOption(new Option("package", "package project"));
            options.addOption(new Option("out", "out file patch"));
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = null;
            try {
                cmd = parser.parse(options, args);
            } catch (ParseException e) {
                help(options);
                return;
            }
            if (cmd.hasOption("?")) {
                help(options);
                return;
            }
            JpaKitConverterConfig config = JpaKitConverterConfig.create(cmd);
            String valid = config.valid();
            if (valid != null) {
                help(options);
                return;
            }
            String messageRun = JpaKitCmdConfig.excuteCommand(config,null);
            System.out.println("\n" + messageRun + "\n");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
