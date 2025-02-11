package zoo;

import clinic.interfaces.Clinic;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Класс, представляющий зоопарк.
 */
public class Zoo {
    @Autowired
    Clinic clinic;
}
