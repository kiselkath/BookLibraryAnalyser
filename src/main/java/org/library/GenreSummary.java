package org.library;

/**
 *  Represents a summary of books for a specific genre.
 * */

public class GenreSummary {
    private int count;
    private double totalRating;

    /**
     * Adds a rating to the current total and increments the book count.
     *
     * @param rating the rating of the book to add
     */
    public void addRating(double rating){
        totalRating += rating;
        count++;
    }

    /**
     * Returns the number of books counted for this genre.
     *
     * @return the number of books
     */
    public int getCount() {
        return count;
    }

    public double getTotalRating() {
        return totalRating;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    /**
     * Calculates the average rating for this genre.
     *
     * @return the average rating, or 0 if no books have been added
     */
    public double getAverage() {
        return count == 0 ? 0 : (totalRating/ count);
    }

    @Override
    public String toString() {
        return "Count: " + count + ", Average Rating: " + getAverage();
    }
}
