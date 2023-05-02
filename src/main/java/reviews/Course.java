package reviews;

public class Course {
    String department;
    int catalogNumber;

    public Course(String department, int catalogNumber) {
        this.department = department;
        this.catalogNumber = catalogNumber;
    }

    public Course() {

    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }
}
