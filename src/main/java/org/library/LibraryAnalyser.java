package org.library;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Analyzes book data using Stream API features: flatMap, collectors, and a custom collector.
 */
public class LibraryAnalyser {


    /**
     * Flattens the list of books from a list of authors.
     * Explanation of flatMap() vs. map():
     * List<Author> authors; // Each author has a List<Book>
     * If we do: authors.stream().map(Author::getBooks)
     * We get a Stream<List<Book>> - a stream o book lists.
     * But with:
     * authors.stream().flatMap(author -> author.getBooks().stream())
     * We get a Stream<Book> - all books across all authors in one flat stream
     *
     *
     */
    public List<Book> getAllBooks(List<Author> authors){
        return authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .collect(Collectors.toList());
    }

    /**
     * Groups books by genre and counts them.
     * Groups books by genre and counts how many books are in each genre.
     * Output: Map<String, Long> like { "Programming" → 2, "Fiction" → 1 }
     */
    public Map<String, Long> countBooksByGenre(List<Book> books){
        return books.stream()
                .collect(
                        Collectors.groupingBy(
                                Book::getGenre,
                                Collectors.counting()
                        )
                );
    }

    /**
     * Calculates average rating by genre.
     * Groups books by genre and calculates the average rating per genre.
     * Output: Map<String, Double> like { "History" → 4.05 }
     */
    public  Map<String, Double> averageRatingByGenre(List<Book> books){
        return books.stream()
                .collect(
                        Collectors.groupingBy(
                                Book::getGenre,
                                Collectors.averagingDouble(Book::getRating)
                        )
                );
    }

    /**
     * Uses a custom collector to summarize genre statistics.
     */
    public Map<String, GenreSummary> summarizeGenres(List<Book> books){
        return books.stream()
                .collect(
                        new GenreSummaryCollector()
                );
    }

}