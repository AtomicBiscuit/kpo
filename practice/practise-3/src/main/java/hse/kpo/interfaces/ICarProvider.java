package hse.kpo.interfaces;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;

public interface ICarProvider {

    Car takeCar(Customer customer);
}
