package benchmarks;

import java.util.List;

public interface User {

    int getId();

    String getName();

    String[] getPhones();

    List<Double> getCards();

    Gender getGender();

    enum Gender {MALE, FEMALE}
}
