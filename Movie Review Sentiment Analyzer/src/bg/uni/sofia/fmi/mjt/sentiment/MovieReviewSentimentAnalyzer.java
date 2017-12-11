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

				String currentWord = null;
				int i = 1;
				while (i < line.length()) {

					if (Character.isLetterOrDigit(line.charAt(i))) {
						currentWord += line.charAt(i);
					}

					else {
						if (!stopWords.contains(currentWord) && currentWord.matches("[a-zA-Z0-9]*")) {
							listOfWords.add(new WordData(currentWord, 1, Character.digit(line.charAt(0), 10)));
							currentWord = null;
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

		return sentimentScore;
	}

	@Override
	public String getReviewSentimentAsName(String review) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getWordSentiment(String word) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<String> getMostFrequentWords(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getMostPositiveWords(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getMostNegativeWords(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSentimentDictionarySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isStopWord(String word) {
		if (stopWords.contains(word)) {
			return true;
		}
		return false;
	}

}
