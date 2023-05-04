package gui;

public class Review {
    private String courseCode;
    private Student student;
    private String reviewText;

    private int rating;

    public Review(String courseCode, Student student, String reviewText, int rating) {
        this.courseCode = courseCode;
        this.student = student;
        this.reviewText = reviewText;
        this.rating = this.rating;
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

    public int getRating()  { return rating; }
}
