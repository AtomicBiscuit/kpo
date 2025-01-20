package studying;

import java.util.Objects;

public class HseCarService {

    private final ICarProvider carProvider;

    private final ICustomerProvider customerProvider;

    public HseCarService(ICarProvider carProvider, ICustomerProvider customersProvider) {
        this.carProvider = carProvider;
        this.customerProvider = customersProvider;
    }

    public void sellCars() {
        var customers = customerProvider.getCustomers();

        customers.stream().filter(customer -> Objects.isNull(customer.getCar())).forEach(customer -> {
            var car = carProvider.takeCar(customer);
            if (Objects.nonNull(car)) {
                customer.setCar(car);
            }
        });
    }
}
