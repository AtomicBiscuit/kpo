package zoo;

import clinic.interfaces.Clinic;
import org.springframework.beans.factory.annotation.Autowired;

public class Zoo {
    @Autowired
    Clinic clinic;
}
