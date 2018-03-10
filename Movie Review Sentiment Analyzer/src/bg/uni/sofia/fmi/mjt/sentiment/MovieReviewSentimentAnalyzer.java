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

	private Set<String> stopWordsSet;
	private Map<String, WordData> wordsMap;

	public MovieReviewSentimentAnalyzer(String reviewsFileName, String stopWordsFileName) {

		this.stopWordsSet = new HashSet<String>();
		this.wordsMap = new HashMap<String, WordData>();

		readStopWordsFromFile(stopWordsFileName, stopWordsSet);
		readWordsFromFile(reviewsFileName, wordsMap);

	}

	private void readStopWordsFromFile(String stopWordsFileName, Set<String> stopWords) {

		try (BufferedReader br = new BufferedReader(new FileReader(stopWordsFileName))) {

			String line;
			while ((line = br.readLine()) != null) {
				line = line.toLowerCase();
				stopWords.add(line);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void readWordsFromFile(String reviewsFileName, Map<String, WordData> wordsMap) {
		try (BufferedReader br = new BufferedReader(new FileReader(reviewsFileName))) {

			String line;
			while ((line = br.readLine()) != null) {

				line = line.toLowerCase();

				String[] wordsInEachLineArray = line.split("[^a-zA-Z0-9]");

				for (int i = 1; i < wordsInEachLineArray.length; i++) {

					wordsInEachLineArray[i] = wordsInEachLineArray[i].replaceAll(" ", "");

					if (stopWordsSet.contains(wordsInEachLineArray[i]) || !wordsInEachLineArray[i].matches("[a-zA-Z0-9][a-zA-Z0-9]*")) {
						continue;
					}

					if (wordsMap.containsKey(wordsInEachLineArray[i])) {
						wordsMap.put(wordsInEachLineArray[i], new WordData(wordsMap.get(wordsInEachLineArray[i]).getTimesFound() + 1,
								wordsMap.get(wordsInEachLineArray[i]).getCurrentSentimentScore() + Double.parseDouble(wordsInEachLineArray[0])));
						continue;
					}

					wordsMap.put(wordsInEachLineArray[i], new WordData(1, Double.parseDouble(wordsInEachLineArray[0])));

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

		if (wordsMap.containsKey(word)) {
			return wordsMap.get(word).getSentimentScore();
		}
		return unknownCase;
	}

	@Override
	public Collection<String> getMostFrequentWords(int n) {

		return wordsMap.entrySet().stream()
				.sorted((e1, e2) -> Integer.compare(e2.getValue().getTimesFound(), e1.getValue().getTimesFound()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());

	}

	@Override
	public Collection<String> getMostPositiveWords(int n) {

		return wordsMap.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e2.getValue().getSentimentScore(),
						e1.getValue().getSentimentScore()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());

	}

	@Override
	public Collection<String> getMostNegativeWords(int n) {

		return wordsMap.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e1.getValue().getSentimentScore(),
						e2.getValue().getSentimentScore()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());

	}

	@Override
	public int getSentimentDictionarySize() {
		return wordsMap.size();
	}

	@Override
	public boolean isStopWord(String word) {
		word = word.toLowerCase();

		return stopWordsSet.contains(word);
	}
}
