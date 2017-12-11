package bg.uni.sofia.fmi.mjt.sentiment;

import java.util.Collection;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

	//public MovieReviewSentimentAnalyzer(String reviewsFileName, String stopwordsFileName) {
	//}
	
	@Override
	public double getReviewSentiment(String review) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return false;
	}

}
