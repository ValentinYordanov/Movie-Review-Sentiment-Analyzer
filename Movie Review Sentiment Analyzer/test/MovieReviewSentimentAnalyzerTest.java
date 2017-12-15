import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import bg.uni.sofia.fmi.mjt.sentiment.MovieReviewSentimentAnalyzer;

public class MovieReviewSentimentAnalyzerTest {

	MovieReviewSentimentAnalyzer dictionarySizeAndCollectionTesting = new MovieReviewSentimentAnalyzer(
			"movieReviews4.txt", "stopwords.txt");
	MovieReviewSentimentAnalyzer reviewSentimentTester = new MovieReviewSentimentAnalyzer("reviewSentimentTests.txt",
			"stopwords.txt");

	@Test
	public void IsAStopWordtest() {

		assertTrue("Should be a stopword!", dictionarySizeAndCollectionTesting.isStopWord("a"));

	}

	@Test
	public void IsNotAStopWordtest() {

		assertFalse("Should NOT be a stopword!", dictionarySizeAndCollectionTesting.isStopWord("aa"));

	}

	@Test
	public void IsReviewSentimentRight() {

		assertEquals("Sentiment score is not right!", 3.30556,
				reviewSentimentTester.getReviewSentiment("good summer hobby"), 0.0001); // good - 3.5; summer - 3.75;
		// hobby - 2.666666667

	}

	@Test
	public void IsReviewSentimentRight2() {

		assertEquals("Sentiment score is not right!", 3.5,
				reviewSentimentTester.getReviewSentiment("any/MacAbre\\of'filmmaker"), 0.0001); // macabre - 3 filmmaker
																								// - 4

	}

	@Test
	public void IsReviewSentimentRight3() {

		assertEquals("Sentiment score is not right!", 2.3334,
				reviewSentimentTester.getReviewSentiment("Very SwinGing''RussiaN/Absorbing!!"), 0.0001); // swinging - 1
																											// Russian -
																											// 4
																											// Absorbing
																											// - 2

	}

	@Test
	public void IsReviewSentimentAsNameRight() {

		assertEquals("Sentiment score as name is not right! - positive", "positive",
				reviewSentimentTester.getReviewSentimentAsName("any/MacAbre\\of'filmmaker"));

	}

	@Test
	public void IsReviewSentimentAsNameRight2() {

		assertEquals("Sentiment score as name is not right! - neutral", "neutral",
				reviewSentimentTester.getReviewSentimentAsName("Very SwinGing''RussiaN/Absorbing!!"));

	}

	@Test
	public void IsReviewSentimentAsNameRight3() {

		assertEquals("Sentiment score as name is not right! - unknown", "unknown",
				reviewSentimentTester.getReviewSentimentAsName("any"));

	}

	@Test
	public void IsReviewSentimentAsNameRight4() {

		assertEquals("Sentiment score as name is not right! - somewhat negative", "somewhat negative",
				reviewSentimentTester.getReviewSentimentAsName("Shyamalan should stop"));

	}

	@Test
	public void IsReviewSentimentAsNameRight5() {

		assertEquals("Sentiment score as name is not right! - somewhat positive", "somewhat positive",
				reviewSentimentTester.getReviewSentimentAsName("good summer hobby"));

	}

	@Test
	public void IsReviewSentimentAsNameRight6() {

		assertEquals("Sentiment score as name is not right! - negative", "negative",
				reviewSentimentTester.getReviewSentimentAsName("sneaky feel"));

	}

	@Test
	public void isDictionarySizeRight() {

		assertEquals("Dictionary size not right!", 24, dictionarySizeAndCollectionTesting.getSentimentDictionarySize());

	}

	@Test
	public void getMostPositiveNWords() {

		Set<String> set = new HashSet<>(dictionarySizeAndCollectionTesting.getMostPositiveWords(4));
		assertTrue("Most positive words doesnt work", set.contains("movie"));
	}

	@Test
	public void getMostNegativeNWords() {

		Set<String> set = new HashSet<>(dictionarySizeAndCollectionTesting.getMostNegativeWords(5));
		assertTrue("Most negative words doesnt work", set.contains("person"));
	}

	@Test
	public void getMostNegativeNWords2() {

		Set<String> set = new HashSet<>(dictionarySizeAndCollectionTesting.getMostNegativeWords(4));
		assertFalse("Most negative words doesnt work", set.contains("person"));
	}

	@Test
	public void getMostFrequentNWords() {

		Set<String> set = new HashSet<>(dictionarySizeAndCollectionTesting.getMostFrequentWords(3));
		System.out.println(dictionarySizeAndCollectionTesting.getMostFrequentWords(2));
		assertTrue("Most frequent words doesnt work", set.contains("person"));
	}

	@Test
	public void getMostFrequentNWords2() {

		Set<String> set = new HashSet<>(dictionarySizeAndCollectionTesting.getMostFrequentWords(1));
		assertFalse("Most frequent words doesnt work", set.contains("person"));
	}

	@Test
	public void getWordSentiment() {

		assertEquals("Word Sentiment doesnt work!", 3.75, reviewSentimentTester.getWordSentiment("summer"), 0.0001);

	}

	@Test
	public void getWordSentiment2() {

		assertEquals("Word Sentiment doesnt work!", 2.6666, reviewSentimentTester.getWordSentiment("hobby"), 0.0001);

	}

	@Test
	public void getWordSentimentUnknowsWord() {

		assertEquals("Word Sentiment doesnt work!", -1, reviewSentimentTester.getWordSentiment("any"), 0.0001);

	}
}
