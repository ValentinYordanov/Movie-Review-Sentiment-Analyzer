package bg.uni.sofia.fmi.mjt.sentiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

	private Set<String> stopWords;
	private Map<String, WordData> words;

	public MovieReviewSentimentAnalyzer(String reviewsFileName, String stopwordsFileName) {

		stopWords = new HashSet<String>();
		words = new HashMap<String, WordData>();

		try (BufferedReader br = new BufferedReader(new FileReader(stopwordsFileName))) {

			String line;
			while ((line = br.readLine()) != null) {
				line = line.toLowerCase();
				stopWords.add(line);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(new FileReader(reviewsFileName))) {

			String line;
			while ((line = br.readLine()) != null) {

				line = line.toLowerCase();

				String[] wordsInEachLine = line.split("[^a-zA-Z0-9]");

				for (int i = 1; i < wordsInEachLine.length; i++) {

					wordsInEachLine[i] = wordsInEachLine[i].replaceAll(" ", "");

					if (stopWords.contains(wordsInEachLine[i]) || !wordsInEachLine[i].matches("[a-zA-Z0-9][a-zA-Z0-9]*")) {
						continue;
					}

					if (words.containsKey(wordsInEachLine[i])) {
						words.put(wordsInEachLine[i], new WordData(words.get(wordsInEachLine[i]).getTimesFound() + 1,
								words.get(wordsInEachLine[i]).getCurrentSentimentScore() + Double.parseDouble(wordsInEachLine[0])));
						continue;
					}

					words.put(wordsInEachLine[i], new WordData(1, Double.parseDouble(wordsInEachLine[0])));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public double getReviewSentiment(String review) {

		double sentimentScore = 0;
		int numberOfWordsInTheReview = 0;
		double UnknowsCase = -1.0;
		review = review.toLowerCase();
		String[] strings = review.split("[^a-zA-Z0-9]");

		for (int i = 0; i < strings.length; i++) {
			strings[i] = strings[i].replaceAll(" ", "");
			if (getWordSentiment(strings[i]) == -1) {
				continue;
			}

			sentimentScore += getWordSentiment(strings[i]);
			numberOfWordsInTheReview++;
		}

		if (numberOfWordsInTheReview != 0) {
			return sentimentScore / numberOfWordsInTheReview;
		}
		return UnknowsCase;
	}

	@Override
	public String getReviewSentimentAsName(String review) {
		int scoreRounded = (int) Math.round(getReviewSentiment(review));

		switch (scoreRounded) {
		case 0:
			return "negative";
		case 1:
			return "somewhat negative";
		case 2:
			return "neutral";
		case 3:
			return "somewhat positive";
		case 4:
			return "positive";

		}

		return "unknown";
	}

	@Override
	public double getWordSentiment(String word) {

		double unknownCase = -1.0;
		word = word.toLowerCase();

		if (words.containsKey(word)) {
			return words.get(word).getSentimentScore();
		}
		return unknownCase;
	}

	@Override
	public Collection<String> getMostFrequentWords(int n) {

		return words.entrySet().stream()
				.sorted((e1, e2) -> Integer.compare(e2.getValue().getTimesFound(), e1.getValue().getTimesFound()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());

	}

	@Override
	public Collection<String> getMostPositiveWords(int n) {

		return words.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e2.getValue().getSentimentScore(),
						e1.getValue().getSentimentScore()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());

	}

	@Override
	public Collection<String> getMostNegativeWords(int n) {

		return words.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e1.getValue().getSentimentScore(),
						e2.getValue().getSentimentScore()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());

	}

	@Override
	public int getSentimentDictionarySize() {
		return words.size();
	}

	@Override
	public boolean isStopWord(String word) {
		word = word.toLowerCase();
		if (stopWords.contains(word)) {
			return true;
		}
		return false;
	}
}
