package fm;

import fm.domains.types.Identifier;
import fm.factories.FinancialFactory;
import fm.helpers.ConsoleReader;
import fm.params.console.ConsoleBankAccountParams;
import fm.params.console.ConsoleCategoryParams;
import fm.params.console.ConsoleOperationParams;
import fm.reports.export.impl.CSVDataExporter;
import fm.reports.export.impl.JSONDataExporter;
import fm.services.AnalysisService;
import fm.storages.AccountStorage;
import fm.storages.CategoryStorage;
import fm.storages.OperationStorage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class Bank {
    private AnalysisService analysisService;

    private AccountStorage accountStorage;

    private CategoryStorage categoryStorage;

    private OperationStorage operationStorage;

    private CSVDataExporter csvDataExporter;

    private JSONDataExporter jsonDataExporter;

    private FinancialFactory financialFactory;

    public void run() {
        var helper = new ConsoleReader(System.in, System.out);
        int num = 1;

        while (true) {
            try {
                var answer = helper.readInt("Enter", 1, 10);
                if (answer == 1) {
                    var account = financialFactory.createAccount(new ConsoleBankAccountParams(System.in, System.out),
                                                                 new Identifier(num));
                    accountStorage.addAccount(account);
                } else if (answer == 2) {
                    var data = financialFactory.createCategory(new ConsoleCategoryParams(System.in, System.out),
                                                               new Identifier(num));
                    categoryStorage.addCategory(data);
                } else if (answer == 3) {
                    var data = financialFactory.createOperation(new ConsoleOperationParams(System.in, System.out),
                                                                new Identifier(num));
                    operationStorage.addOperation(data);
                } else if (answer == 4) {
                    var id = helper.readInt("Bank account id: ", 0, 1_000_000);
                    var from = helper.readDate("from: ");
                    var to = helper.readDate("to: ");
                    log.info("Delta is " + analysisService.getDelta(new Identifier(id), from, to));
                } else if (answer == 5) {
                    var id = helper.readInt("Category id: ", 0, 1_000_000);
                    log.info("Delta is " + analysisService.getSumByCategory(new Identifier(id)));
                } else if (answer == 6 || answer == 7) {
                    try {
                        if (answer == 6) {
                            File file = new File("./dump.csv");
                            csvDataExporter.export(new PrintWriter(file));
                        } else {
                            File file = new File("./dump.json");
                            jsonDataExporter.export(new PrintWriter(file));
                        }
                    } catch (FileNotFoundException e) {
                        log.error("Error while opening file");
                    }
                } else if (answer == 10) {
                    return;
                }
                log.info("Id: " + num);
                num++;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}