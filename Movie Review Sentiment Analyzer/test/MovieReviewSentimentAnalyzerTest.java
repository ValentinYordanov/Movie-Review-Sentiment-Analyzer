import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bg.uni.sofia.fmi.mjt.sentiment.*;

class MovieReviewSentimentAnalyzerTest {

	MovieReviewSentimentAnalyzer test = new MovieReviewSentimentAnalyzer("movieReviews3.txt", "stopwords.txt");
	
	@Test
	void IsAStopWordtest() {
		
		assertTrue("Should be a stopword!", test.isStopWord("a"));
		
	}
	
	@Test
	void IsNotAStopWordtest() {
		
		assertFalse("Should NOT be a stopword!", test.isStopWord("aa"));
		
	}
	
	@Test
	void IsReviewSentimentRight() {
		
		assertEquals("Sentiment score is not right!", 4, test.getReviewSentiment("introspective independent "), 1.01);
		
	}
	
	@Test
	void IsReviewSentimentRight2() {
		
		assertEquals("Sentiment score is not right!", 2.5, test.getReviewSentiment("introspective fans "), 1.01);
		
	}
}
