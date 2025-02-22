package hse.kpo;

import hse.kpo.domains.Customer;
import hse.kpo.domains.Report;
import hse.kpo.factories.cars.CatamaranWithWheelsFactory;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.LevitationCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.factories.catamarans.HandCatamaranFactory;
import hse.kpo.factories.catamarans.LevitationCatamaranFactory;
import hse.kpo.factories.catamarans.PedalCatamaranFactory;
import hse.kpo.observers.ReportSalesObserver;
import hse.kpo.params.CatamaranWithWheelsEngineParams;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import hse.kpo.services.HseCarService;
import hse.kpo.services.HseCatamaranService;
import hse.kpo.storages.CarStorage;
import hse.kpo.storages.CatamaranStorage;
import hse.kpo.storages.CustomerStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Hse {
    CarStorage carStorage;

    CatamaranStorage catamaranStorage;

    CustomerStorage customerStorage;

    PedalCarFactory pedalCarFactory;

    PedalCatamaranFactory pedalCatamaranFactory;

    LevitationCarFactory levitationCarFactory;

    LevitationCatamaranFactory levitationCatamaranFactory;

    HandCarFactory handCarFactory;

    HandCatamaranFactory handCatamaranFactory;

    CatamaranWithWheelsFactory catamaranWithWheelsFactory;

    ReportSalesObserver observer;

    public void addCustomer(String name, int legPower, int handPower, int iq) {
        var customer = Customer.builder().name(name).legPower(legPower).handPower(handPower).iq(iq).build();
        customerStorage.addCustomer(customer);
    }

    public void addPedalCar(int pedalSize) {
        carStorage.addCar(pedalCarFactory, new PedalEngineParams(pedalSize));
    }

    public void addPedalCatamaran(int pedalSize) {
        catamaranStorage.addCatamaran(pedalCatamaranFactory, new PedalEngineParams(pedalSize));
    }

    public void addLevitatingCar() {
        carStorage.addCar(levitationCarFactory, EmptyEngineParams.DEFAULT);
    }

    public void addLevitatingCatamaran() {
        catamaranStorage.addCatamaran(levitationCatamaranFactory, EmptyEngineParams.DEFAULT);
    }

    public void addHandCar() {
        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    }

    public void addHandCatamaran() {
        catamaranStorage.addCatamaran(handCatamaranFactory, EmptyEngineParams.DEFAULT);
    }

    public void addHandCatamaranWithWheels() {
        var catamaran = handCatamaranFactory.create(EmptyEngineParams.DEFAULT, 0);
        carStorage.addCar(catamaranWithWheelsFactory, new CatamaranWithWheelsEngineParams(catamaran));
    }

    public void addPedalCatamaranWithWheels(int pedalSize) {
        var catamaran = pedalCatamaranFactory.create(new PedalEngineParams(pedalSize), 0);
        carStorage.addCar(catamaranWithWheelsFactory, new CatamaranWithWheelsEngineParams(catamaran));
    }

    public void addLevitatingCatamaranWithWheels() {
        var catamaran = levitationCatamaranFactory.create(EmptyEngineParams.DEFAULT, 0);
        carStorage.addCar(catamaranWithWheelsFactory, new CatamaranWithWheelsEngineParams(catamaran));
    }

    void sell() {
        var hseCarService = new HseCarService(carStorage, customerStorage);
        hseCarService.addObserver(observer);
        hseCarService.sellCars();

        var hseCatamaranService = new HseCatamaranService(catamaranStorage, customerStorage);
        hseCatamaranService.addObserver(observer);
        hseCatamaranService.sellCatamarans();
    }

    Report generateReport() {
        return observer.buildReport();
    }
}
