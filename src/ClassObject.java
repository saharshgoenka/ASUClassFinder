public class ClassObject {
    String className;
    String classNumber;
    String teacherName;
    String wantedTime;
    int reservedForOthers;

    // could have just checked for matching class id number
    // would still need class name and number | but not time and teacher name
    // instead of xpaths using teacher name, would use class iD instead (no more same teacher same time issues || staff issues)

    public ClassObject(String className, String classNumber, String teacherName, String wantedTime, int reservedForOthers) {
        this.className = className;
        this.classNumber = classNumber;
        this.teacherName = teacherName;
        this.wantedTime = wantedTime;
        this.reservedForOthers = reservedForOthers;
    }
}
