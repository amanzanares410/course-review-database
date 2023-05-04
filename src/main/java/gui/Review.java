package gui;

public class Review {
    private String courseCode;
    private Student student;
    private String reviewText;

    public Review(String courseCode, Student student, String reviewText) {
        this.courseCode = courseCode;
        this.student = student;
        this.reviewText = reviewText;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Student getStudent() {
        return student;
    }

    public String getReviewText() {
        return reviewText;
    }
}
