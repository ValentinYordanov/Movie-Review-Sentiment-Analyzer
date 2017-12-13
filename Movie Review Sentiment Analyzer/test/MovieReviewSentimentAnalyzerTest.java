import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import bg.uni.sofia.fmi.mjt.sentiment.MovieReviewSentimentAnalyzer;
import bg.uni.sofia.fmi.mjt.sentiment.WordData;

public class MovieReviewSentimentAnalyzerTest {

	MovieReviewSentimentAnalyzer test = new MovieReviewSentimentAnalyzer("movieReviews3.txt", "stopwords.txt");
	MovieReviewSentimentAnalyzer test2 = new MovieReviewSentimentAnalyzer("movieReviews4.txt", "stopwords.txt");

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

		assertEquals("Sentiment score is not right!", 3.5, test.getReviewSentiment("introspective fans "), 1.01);

	}

	@Test
	public void IsReviewSentimentRight3() {

		assertEquals("Sentiment score is not right!", 3.67, test.getReviewSentiment("introspective fans independent"),
				1.01);

	}

	@Test
	public void IsReviewSentimentAsNameRight() {

		assertEquals("Sentiment score as name is not right!", "positive",
				test.getReviewSentimentAsName(("introspective independent")));

	}

	@Test
	public void IsReviewSentimentAsNameRight2() {

		assertEquals("Sentiment score as name is not right!", "neutral", test.getReviewSentimentAsName("fans"));

	}

	@Test
	public void IsReviewSentimentAsNameRight3() {

		assertEquals("Sentiment score as name is not right!", "unknown", test.getReviewSentimentAsName("any"));

	}

	@Test
	public void IsReviewSentimentAsNameRight4() {

		assertEquals("Sentiment score as name is not right!", "somewhat negative",
				test.getReviewSentimentAsName("inept"));

	}

	@Test
	public void IsReviewSentimentAsNameRight5() {

		assertEquals("Sentiment score as name is not right!", "somewhat positive",
				test.getReviewSentimentAsName("positively"));

	}

	@Test
	public void IsReviewSentimentAsNameRight6() {

		assertEquals("Sentiment score as name is not right!", "negative", test.getReviewSentimentAsName("movie"));

	}

	@Test
	public void isDictionarySizeRight() {

		assertEquals("Dictionary size not right!", 24, test2.getSentimentDictionarySize());

	}

	@Test
	public void getMostPositiveNWords() {

		Set<String> set = new HashSet<>(test2.getMostPositiveWords(4));
		assertTrue("Most positive words doesnt work", set.contains("movie"));
	}

	@Test
	public void getMostNegativeNWords() {

		Set<String> set = new HashSet<>(test2.getMostNegativeWords(6));
		assertTrue("Most negative words doesnt work", set.contains("person"));
	}

	@Test
	public void getMostFrequentNWords() {

		Set<String> set = new HashSet<>(test2.getMostFrequentWords(1));
		assertTrue("Most frequent words doesnt work", set.contains("car"));
	}

	@Test
	public void getWordSentiment() {

		assertEquals("Word Sentiment doesnt work!", 3.5, test2.getWordSentiment("movie"), 1.1);

	}

	@Test
	public void getWordSentimentUnknowsWord() {

		assertEquals("Word Sentiment doesnt work!", -1, test2.getWordSentiment("any"), 1.1);

	}

	/*@Test
	public void WordDataEqualsAndHashCode() {
		WordData x = new WordData("Foo Bar", 1, 2.5); // equals and hashCode check name field value
		WordData y = new WordData("Foo Bar", 1, 2.5);
		assertTrue(x.equals(y));
		assertTrue(y.equals(x));
		assertTrue(x.hashCode() == y.hashCode());
	}

	@Test
	public void WordDataEqualsNull() {

		WordData x = new WordData("Foo Bar", 1, 2.5);
		assertFalse("Null equals fails", x.equals(null));

	}

	@Test
	public void WordDataEqualsSame() {

		WordData x = new WordData("Foo Bar", 1, 2.5);
		assertTrue("Same obj equals fails", x.equals(x));

	}

	@Test
	public void WordDataEqualsDifClass() {

		WordData x = new WordData("Foo Bar", 1, 2.5);
		String y = "asd";
		assertFalse("Different obj equals fails", x.equals(y));

	}

	@Test
	public void WordDataDifData() {

		WordData x = new WordData("Foo Bar", 1, 2.5);
		WordData y = new WordData("Foo Bar2", 1, 2.5);
		assertFalse("Different data equals fails", x.equals(y));

	}

	@Test
	public void WordDataDifData2() {

		WordData x = new WordData("Foo Bar", 1, 2.5);
		WordData y = new WordData(null, 1, 2.5);
		assertFalse("Different data equals fails", y.equals(x));

	}*/
}
