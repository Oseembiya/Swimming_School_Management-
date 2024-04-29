package task2;

   

public class PersonalSurvival extends Qualification {


    private final Level level;

    public PersonalSurvival(Level level, String name, Instructor awardedBy) {

        super(QualificationType.PERSONAL.SURVIVAL , name, awardedBy); 

        this.level = level;
    }


}
