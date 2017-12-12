package bg.uni.sofia.fmi.mjt.sentiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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

		List<WordData> listOfWords = new ArrayList<>();

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

				String currentWord = "";
				int i = 1;
				while (i < line.length()) {

					if (Character.isLetterOrDigit(line.charAt(i))) {
						currentWord += line.charAt(i);
					}

					else {
						if (!stopWords.contains(currentWord) && currentWord.matches("[a-zA-Z0-9][a-zA-Z0-9]*")) {
							listOfWords.add(new WordData(currentWord, 1, Character.digit(line.charAt(0), 10)));
							currentWord = "";
						}
					}

					i++;
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		for (int i = 0; i < listOfWords.size() - 1; i++) {

			double currentSentimentScore = listOfWords.get(i).getSentimentScore();
			int numberOftimes = 1;
			for (int j = i + 1; j < listOfWords.size(); j++) {
				if (listOfWords.get(i).getWord().toLowerCase().equals(listOfWords.get(j).getWord().toLowerCase())) {
					currentSentimentScore += listOfWords.get(j).getSentimentScore();
					numberOftimes++;
					listOfWords.remove(j);
					j--;
				}
			}
			currentSentimentScore /= numberOftimes;

			words.put(listOfWords.get(i).getWord().toLowerCase(),
					new WordData(listOfWords.get(i).getWord().toLowerCase(), numberOftimes, currentSentimentScore));

		}
	}

	@Override
	public double getReviewSentiment(String review) {

		double sentimentScore = -1.0;
		int numberOfWordsInTheReview = 0;

		int i = 0;
		String currentWord = null;
		while (i < review.length()) {

			if (Character.isLetterOrDigit(review.charAt(i))) {
				currentWord += review.charAt(i);
			} else {
				if (stopWords.contains(currentWord) && !words.containsKey(currentWord)) {
					sentimentScore += 0;
				} else {

					sentimentScore += words.get(currentWord).getSentimentScore();
					numberOfWordsInTheReview++;
				}
			}

			i++;
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
		return list.stream().sorted((p1, p2) -> Integer.compare(p2.getTimesFound(), p1.getTimesFound())).limit(n)
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

	public static void main(String[] args) {
		
		//MovieReviewSentimentAnalyzer test = new MovieReviewSentimentAnalyzer("movieReviews.txt", "stopwords.txt");
		System.out.println("".matches("[a-zA-Z0-9][a-zA-Z0-9]*"));
	}
	
}
