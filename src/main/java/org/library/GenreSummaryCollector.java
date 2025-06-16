package org.library;

import org.library.Book;
import org.library.GenreSummary;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;


/**
 * A custom collector that groups books by genre and calculates
 * the total count and average rating for each genre.
 *
 * <p>It collects {@link Book} objects into a {@code Map<String, GenreSummary>},
 * where the key is the genre and the value is a summary of the number of books
 * and their average rating.
 */

public class GenreSummaryCollector implements  Collector<
        Book,
        Map<String, GenreSummary>,
        Map<String, GenreSummary>
        >{
    @Override
    public Supplier<Map<String, GenreSummary>> supplier() {
        // Creates a new empty HashMap to accumulate results
        return HashMap::new; // new HashMap()
    }

    @Override
    public BiConsumer<Map<String, GenreSummary>, Book> accumulator() {
        // For each book, update (or create if not exists) the genre's summary
        return (map, book) ->{
            map.computeIfAbsent(book.getGenre(), g -> new GenreSummary())
                    .addRating(book.getRating());
        };
    }

    /**
     * Stream of [
     * {"Programming","Java",2.05 },{"Programming","PHP",3.25 }, {"Programming","Python",4.50 }
     * {"History","Syria",2.05 },{"History","Germany",3.25 }, {"History","India",4.50 }
     * ]
     * Collect Map {
     *     "Programming": {
     *               count: 3
     *               totalRating:9.80
     *           }
     *     "History":{
     *         count:3,
     *         totalRating:9.80
     *     }
     * }
     *
     * */

    @Override
    public BinaryOperator<Map<String, GenreSummary>> combiner() {
        return (map1, map2)->{
            map2.forEach((genre, summary) ->
                    map1.merge(
                            genre, summary, (s1, s2) ->  {
                                s1.setCount(s2.getCount()); // s1.count += s2.count
                                s1.setTotalRating(s2.getTotalRating()); // s1.totalRating += s2.totalRating
                                return s1;
                            }
                    )

            );
            return map1;
        };

        /**
         *  map1 {
         *      *     "Programming": {
         *      *               count: 2
         *      *               totalRating:5.30
         *      *           }
         *      *
         *      * }
         *
         *   map2 {
         *
         *    "Programming": {
         *          *                    count: 1
         *          *                     totalRating:4.50
         *          *                }
         *     "History":{
         *          *               count:3,
         *          *               totalRating:9.80
         *          *           }
         *
         *   }
         *
         * */
    }

    @Override
    public Function<Map<String, GenreSummary>, Map<String, GenreSummary>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }

    /**
     * Why Is A and R the Same?
     * This is called identify and finish, and it means:
     * "I don't want to convert my accumulator into something else , it's already the result I want.
     * */




}