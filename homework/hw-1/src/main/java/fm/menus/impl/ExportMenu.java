package fm.menus.impl;

import static fm.formater.PrettyText.format;

import fm.ConsoleApplication;
import fm.formater.Format;
import fm.helpers.ConsoleHelper;
import fm.menus.Menu;
import fm.reports.export.impl.CSVDataExporter;
import fm.reports.export.impl.JSONDataExporter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("exportMenu")
@AllArgsConstructor
public class ExportMenu implements Menu {
    ConsoleApplication app;

    ConsoleHelper helper;

    CSVDataExporter csvExporter;

    JSONDataExporter jsonExporter;

    @Override
    public void print() {
        var out = helper.getOutput();
        out.println(format("        Export data menu", Format.BLUE, Format.BOLD));
        out.println("Available actions:");
        out.println("    1. Export in csv format");
        out.println("    2. Export in json format");
        out.println("    0. Back to main menu");
    }

    @Override
    public void logic() {
        var action = helper.readInt("Enter number", 0, 2);
        if (action == 0) {
            app.switchState("mainMenu");
            return;
        }

        var filename = helper.readLine("Enter filepath");
        var file = new File(filename);

        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            helper.getOutput()
                  .println(format("Can`t create file, maybe there non-existent directories in path", Format.ERROR));
            return;
        }

        if (action == 1) {
            csvExporter.export(writer);
        } else if (action == 2) {
            jsonExporter.export(writer);
        }
        writer.close();
    }
}
