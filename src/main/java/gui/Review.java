package gui;

public class Review {
    private Course course;
    private Student student;
    private String reviewText;
    private int rating;

    public Review(Course course, Student student, String reviewText, int rating) {
        this.course = course;
        this.student = student;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRating()  {
        return rating; }
}
