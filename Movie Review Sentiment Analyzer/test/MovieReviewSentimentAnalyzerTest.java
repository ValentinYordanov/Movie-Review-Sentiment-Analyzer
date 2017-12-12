import static org.junit.Assert.*;

import org.junit.Test;

import bg.uni.sofia.fmi.mjt.sentiment.MovieReviewSentimentAnalyzer;

public class MovieReviewSentimentAnalyzerTest {

	MovieReviewSentimentAnalyzer test = new MovieReviewSentimentAnalyzer("movieReviews3.txt", "stopwords.txt");

	@Test
	public void IsAStopWordtest() {

		assertTrue("Should be a stopword!", test.isStopWord("a"));

	}

	@Test
	public void IsNotAStopWordtest() {

		assertFalse("Should NOT be a stopword!", test.isStopWord("aa"));

	}

	@Test
	public void IsReviewSentimentRight() {

		assertEquals("Sentiment score is not right!", 4, test.getReviewSentiment("introspective independent "), 1.01);

	}

	@Test
	public void IsReviewSentimentRight2() {

		assertEquals("Sentiment score is not right!", 2.5, test.getReviewSentiment("introspective fans "), 1.01);

	}

}
