package task2;

//This is a superclass qualification that has a
abstract class Qualification {

    private final QualificationType type;
    private final String name;
    private final Instructor awardedBy;

    public Qualification(QualificationType type, String name, Instructor awardedBy) {
        this.type = type;
        this.name = name;
        this.awardedBy = awardedBy;
    }
        
    public QualificationType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public Instructor getAwardedBy() {
        return this.awardedBy;
    }
}
