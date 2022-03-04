public class classInfoObject {
    String className;
    String classNumber;
    String teacherName;
    String wantedTime;
    String reservedForOthers;
    String classID;

    public classInfoObject(String className, String classNumber, String teacherName, String wantedTime, String reservedForOthers, String classID) {
        this.className = className;
        this.classNumber = classNumber;
        this.teacherName = teacherName;
        this.wantedTime = wantedTime;
        this.reservedForOthers = reservedForOthers;
        this.classID = classID;
    }
}
