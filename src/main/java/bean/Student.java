package bean;

/**
 * @author ZJY
 * @ClassName: Student
 * @Description: Student
 * @date 2019/4/26 13:50
 */
public class Student extends Person {
    private String school;

    private String grade;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
