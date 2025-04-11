package hse.kpo.services.catamarans;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.interfaces.catamarans.CatamaranFactory;
import hse.kpo.interfaces.catamarans.CatamaranProvider;
import hse.kpo.repos.CatamaranRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Сервис продажи катамаранов.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HseCatamaranService implements CatamaranProvider {

    private final CatamaranRepository catamaranRepository;

    private final CustomerProvider customerProvider;

    /**
     * Метод продажи катамаранов.
     */
    public void sellCatamarans() {
        var customers = customerProvider.getCustomers();

        customers.stream().filter(customer -> Objects.isNull(customer.getCatamaran())).forEach(customer -> {
            var catamaran = takeCatamaran(customer);
            if (Objects.nonNull(catamaran)) {
                customer.setCatamaran(catamaran);
            } else {
                log.warn("No catamaran in CatamaranService");
            }
        });
    }

    @Override
    public Catamaran takeCatamaran(Customer customer) {

        var filtered = catamaranRepository.findAll()
                                          .stream()
                                          .filter(catamaran -> catamaran.isCompatible(customer))
                                          .toList();

        var first = filtered.stream().findFirst();

        first.ifPresent(catamaranRepository::delete);

        return first.orElse(null);
    }

    public <T> Catamaran addCatamaran(CatamaranFactory<T> factory, T params) {
        return catamaranRepository.save(factory.create(params));
    }

    public List<Catamaran> getCatamarans() {
        return catamaranRepository.findAll();
    }
}