/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
	
	// I added 4 more tweets for extra checking 
    
    private static final Instant d1 = Instant.parse("2019-03-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2019-03-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2019-04-17T11:25:00Z");
    private static final Instant d4 = Instant.parse("2019-04-17T11:45:00Z");
    private static final Instant d5 = Instant.parse("2019-04-17T11:47:00Z");
    private static final Instant d6 = Instant.parse("2019-04-17T10:47:00Z");
    
    
    private static final Tweet tweet1 = new Tweet(1, "rajput", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "Hasham", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "USMAN", "can we please pass this test??", d3);
    private static final Tweet tweet4 = new Tweet(4, "WAQAS", "@test1 is this another one of these stupid tests?",d4);
    private static final Tweet tweet5 = new Tweet(5, "rajput", "@test1 @test2 why i am doing these test!",d5);
    private static final Tweet tweet6 = new Tweet(6, "ALI", "@test1 @test1 It's enough bro you don't need to chk",d6);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; 
    }
    
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3,tweet5), "rajput");
        
        assertEquals("same order tweets list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2019-03-17T09:00:00Z");
        Instant testEnd = Instant.parse("2019-03-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("Non-empty list", inTimespan.isEmpty());
        assertTrue("List to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testGetTimespanThreeTweets1() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2,tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
      
    public void testGetTimespanTwoTweets1() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet2));
        assertEquals(d1, timespan.getStart());
        assertEquals(d2, timespan.getEnd());        
    }
    

    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        assertEquals(d1, timespan.getStart());
        assertEquals(d1, timespan.getEnd());        
    }
    
    public void testGetTimespanEmptyTweet() {
        Timespan timespan = Extract.getTimespan(new ArrayList<Tweet>());
        assertEquals(timespan.getEnd(), timespan.getStart());         
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("non-empty list", containing.isEmpty());
        assertTrue("My list that contains test", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("same order tweet", 0, containing.indexOf(tweet1));
    }
    
    
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
