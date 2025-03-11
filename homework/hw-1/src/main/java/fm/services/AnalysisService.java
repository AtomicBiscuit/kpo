package fm.services;

import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.filter.OperationFilter;
import fm.storages.AccountStorage;
import fm.storages.CategoryStorage;
import fm.storages.OperationStorage;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnalysisService {
    private AccountStorage accountStorage;

    private CategoryStorage categoryStorage;

    private OperationStorage operationStorage;

    public int getDelta(Identifier accountId, Date from, Date to) {
        var data = OperationFilter.builder(operationStorage.getAllOperations())
                                  .filterByAccount(accountId)
                                  .filterByDate(from, to)
                                  .build();
        return data.stream().mapToInt(Operation::getSignedAmount).sum();
    }

    public int getSumByCategory(Identifier categoryId) {
        var data = OperationFilter.builder(operationStorage.getAllOperations())
                                  .filterByCategory(categoryId)
                                  .build();
        return data.stream().mapToInt(Operation::getSignedAmount).sum();
    }
}
