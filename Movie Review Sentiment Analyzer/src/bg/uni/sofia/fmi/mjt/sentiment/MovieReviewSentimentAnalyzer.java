package bg.uni.sofia.fmi.mjt.sentiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
				stopWords.add(line);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(new FileReader(reviewsFileName))) {

			String line;
			while ((line = br.readLine()) != null) {

				line = line.replaceAll("[!,?'/*.=:;-]", " ");
				String[] strings = line.split("\\s+");

				for (int i = 1; i < strings.length; i++) {

					if (words.containsKey(strings[i])) {
						words.put(strings[i], new WordData(strings[i], words.get(strings[i]).getTimesFound() + 1,
								words.get(strings[i]).getSentimentScore() + Double.parseDouble(strings[0])));
					}

					words.put(strings[i], new WordData(strings[i], 1, Double.parseDouble(strings[0])));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		/*
		 * for (int i = 0; i < listOfWords.size() - 1; i++) {
		 * 
		 * double currentSentimentScore = listOfWords.get(i).getSentimentScore(); int
		 * numberOftimes = 1; for (int j = i + 1; j < listOfWords.size(); j++) { if
		 * (listOfWords.get(i).getWord().toLowerCase().equals(listOfWords.get(j).getWord
		 * ().toLowerCase())) { currentSentimentScore +=
		 * listOfWords.get(j).getSentimentScore(); numberOftimes++;
		 * listOfWords.remove(j); j--; } } currentSentimentScore /= numberOftimes;
		 * 
		 * words.put(listOfWords.get(i).getWord().toLowerCase(), new
		 * WordData(listOfWords.get(i).getWord().toLowerCase(), numberOftimes,
		 * currentSentimentScore));
		 * 
		 * }
		 */
	}

	@Override
	public double getReviewSentiment(String review) {

		double sentimentScore = -1.0;
		int numberOfWordsInTheReview = 0;

		review = review.replaceAll("[!,?'/*.=:;-]", " ");
		String[] strings = review.split("\\s+");

		for (int i = 0; i < strings.length; i++) {

			if (stopWords.contains(strings[i]) || !words.containsKey(strings[i])) {

			} else {

				sentimentScore += words.get(strings[i]).getSentimentScore();
				numberOfWordsInTheReview++;

			}
		}

		if (numberOfWordsInTheReview != 0) {
			return (double) sentimentScore / numberOfWordsInTheReview;
		}
		return (double) sentimentScore;
	}

	@Override
	public String getReviewSentimentAsName(String review) {
		double score = getReviewSentiment(review);

		int scoreRounded = (int) Math.round(score);

		if (scoreRounded == 0) {
			return "negative";
		}
		if (scoreRounded == 1) {
			return "somewhat negative";
		}
		if (scoreRounded == 2) {
			return "neutral";
		}
		if (scoreRounded == 3) {
			return "somewhat positive";
		}
		if (scoreRounded == 4) {
			return "positive";
		}
		return "unknown";
	}

	@Override
	public double getWordSentiment(String word) {

		if (words.containsKey(word)) {
			return words.get(word).getSentimentScore();
		}
		return -1.0;
	}

	@Override
	public Collection<String> getMostFrequentWords(int n) {

		Collection<WordData> list = new ArrayList<>(words.values());
		return list.stream().sorted((p1, p2) -> Double.compare(p2.getTimesFound(), p1.getTimesFound())).limit(n)
				.map(p1 -> p1.getWord()).collect(Collectors.toList());
	}

	@Override
	public Collection<String> getMostPositiveWords(int n) {

		Collection<WordData> list = new ArrayList<>(words.values());
		return list.stream().sorted((p1, p2) -> Double.compare(p2.getSentimentScore(), p1.getSentimentScore())).limit(n)
				.map(p1 -> p1.getWord()).collect(Collectors.toList());
	}

	@Override
	public Collection<String> getMostNegativeWords(int n) {
		Collection<WordData> list = new ArrayList<>(words.values());

		return list.stream().sorted((p1, p2) -> Double.compare(p1.getSentimentScore(), p2.getSentimentScore())).limit(n)
				.map(p1 -> p1.getWord()).collect(Collectors.toList());
	}

	@Override
	public int getSentimentDictionarySize() {
		return words.size();
	}

	@Override
	public boolean isStopWord(String word) {
		if (stopWords.contains(word)) {
			return true;
		}
		return false;
	}
}
