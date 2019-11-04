import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    private Location location[];
    private GoModel model;

    @BeforeEach
    void setup(){
        model = new GoModel(5);
        location = new Location[]{new Location(0, 0), new Location(0, 1), new Location(1, 0)};
    }


}
