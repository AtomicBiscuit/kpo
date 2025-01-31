package hse.kpo.domains;

import lombok.ToString;
import hse.kpo.interfaces.IEngine;

@ToString
public class HandEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}
