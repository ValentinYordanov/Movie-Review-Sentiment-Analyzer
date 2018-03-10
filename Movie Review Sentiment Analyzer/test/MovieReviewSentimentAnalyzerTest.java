import bg.uni.sofia.fmi.mjt.sentiment.MovieReviewSentimentAnalyzer;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class MovieReviewSentimentAnalyzerTest {

	private static MovieReviewSentimentAnalyzer reviewSentimentTester;
	private static MovieReviewSentimentAnalyzer dictionarySizeAndCollectionTesting;
	private static MovieReviewSentimentAnalyzer bigFileTester;

	@BeforeClass
	public static void beforeClass() throws FileNotFoundException {

		reviewSentimentTester = new MovieReviewSentimentAnalyzer("reviewSentimentTests.txt", "stopwords.txt");
		dictionarySizeAndCollectionTesting = new MovieReviewSentimentAnalyzer(
				"movieReviews4.txt", "stopwords.txt");
		 bigFileTester = new MovieReviewSentimentAnalyzer("movieReviews.txt",
				"stopwords.txt");
	}

	@Test
	public void IsReviewSentimentRight() {

		assertEquals("Sentiment score is not right!", 3.30556,
				reviewSentimentTester.getReviewSentiment("good summer hobby"), 0.0001);

		assertEquals("Sentiment score is not right!", 3.5,
				reviewSentimentTester.getReviewSentiment("any/MacAbre\\of'filmmaker"), 0.0001);

		assertEquals("Sentiment score is not right!", 2.3334,
				reviewSentimentTester.getReviewSentiment("Very SwinGing''RussiaN/Absorbing!!"), 0.0001);

		assertEquals("Sentiment score as name is not right! - positive", "positive",
				reviewSentimentTester.getReviewSentimentAsName("any/MacAbre\\of'filmmaker"));

		assertEquals("Sentiment score as name is not right! - neutral", "neutral",
				reviewSentimentTester.getReviewSentimentAsName("Very SwinGing''RussiaN/Absorbing!!"));

		assertEquals("Sentiment score as name is not right! - unknown", "unknown",
				reviewSentimentTester.getReviewSentimentAsName("any"));

		assertEquals("Sentiment score as name is not right! - somewhat negative", "somewhat negative",
				reviewSentimentTester.getReviewSentimentAsName("Shyamalan should stop"));

		assertEquals("Sentiment score as name is not right! - somewhat positive", "somewhat positive",
				reviewSentimentTester.getReviewSentimentAsName("good summer hobby"));

		assertEquals("Sentiment score as name is not right! - negative", "negative",
				reviewSentimentTester.getReviewSentimentAsName("sneaky feel"));

	}

	@Test
	public void getNWords() {

		assertEquals("Dictionary size not right!", 24, dictionarySizeAndCollectionTesting.getSentimentDictionarySize());

		assertTrue("Should be a stopword!", dictionarySizeAndCollectionTesting.isStopWord("a"));

		assertFalse("Should NOT be a stopword!", dictionarySizeAndCollectionTesting.isStopWord("movie"));

		assertTrue("Most positive words doesn't work",
				dictionarySizeAndCollectionTesting.getMostPositiveWords(4).contains("movie"));

		assertTrue("Most negative words doesn't work",
				dictionarySizeAndCollectionTesting.getMostNegativeWords(5).contains("person"));

		assertFalse("Most negative words doesn't work",
				dictionarySizeAndCollectionTesting.getMostNegativeWords(4).contains("person"));

		assertTrue("Most frequent words doesn't work",
				dictionarySizeAndCollectionTesting.getMostFrequentWords(3).contains("person"));

		assertFalse("Most frequent words doesn't work",
				dictionarySizeAndCollectionTesting.getMostFrequentWords(1).contains("person"));
	}

	@Test
	public void getWordSentiment() {

		assertEquals("Word Sentiment doesn't work!", 3.75, reviewSentimentTester.getWordSentiment("summer"), 0.0001);

		assertEquals("Word Sentiment doesn't work!", 2.6666, reviewSentimentTester.getWordSentiment("hobby"), 0.0001);

		assertEquals("unknown word Sentiment doesn't work!", -1, reviewSentimentTester.getWordSentiment("any"), 0.0001);

	}

	@Test
	public void getWordSentimentFBTests() {

		assertEquals("Word sentiment doesn't work!", 1.75, bigFileTester.getWordSentiment("Dude"), 0.0001);

		assertEquals("Word sentiment doesn't work!", 2.6, bigFileTester.getWordSentiment("international"), 0.0001);

		assertEquals("Review sentiment doesn't work", 1.4637421952077123,
				bigFileTester.getReviewSentiment("A weak script that ends with a quick and boring finale."), 0.0001);

		assertEquals("Review sentiment doesn't work", 2.180814662891809,
				bigFileTester.getReviewSentiment("The funniest comedy of the year, good work! Don't miss it!"), 0.0001);
	}

}