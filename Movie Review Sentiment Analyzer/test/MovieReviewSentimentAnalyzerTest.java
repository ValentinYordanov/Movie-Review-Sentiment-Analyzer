import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bg.uni.sofia.fmi.mjt.sentiment.*;

class MovieReviewSentimentAnalyzerTest {

	MovieReviewSentimentAnalyzer test = new MovieReviewSentimentAnalyzer("movieReviews2.txt", "stopwords.txt");
	
	@Test
	void IsAStopWordtest() {
		
		assertTrue("Should be a stopword!", test.isStopWord("a"));
		
	}
	
	@Test
	void IsNotAStopWordtest() {
		
		assertFalse("Should NOT be a stopword!", test.isStopWord("aa"));
		
	}
}
