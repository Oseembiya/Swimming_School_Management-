package task2;


public class DistanceSwim extends Qualification {

    private final int distance;

    public DistanceSwim(int distance, String name, Instructor awardedBy) {

        super(QualificationType.DISTANCE, name, awardedBy); 

        this.distance = distance;
    }

     
}

