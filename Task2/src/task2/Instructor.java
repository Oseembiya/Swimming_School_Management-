
package task2;

import java.util.ArrayList;
import java.util.List;


class Instructor {

    private final String name;

    private final List<Qualification> qualifications;

    public Instructor(String name) {

        this.name = name;
        this.qualifications = new ArrayList<>();
    }

    String getName() {
       return this.name;
  }
}
