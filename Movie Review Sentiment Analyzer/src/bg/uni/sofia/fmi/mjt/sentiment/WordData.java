package bg.uni.sofia.fmi.mjt.sentiment;

public class WordData {

	private String word;
	private int timesFound;
	private double sentimentScore;

	public WordData(String word, int timesFound, double sentimentScore) {
		this.word = word;
		this.timesFound = timesFound;
		this.sentimentScore = sentimentScore;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getTimesFound() {
		return timesFound;
	}

	public void setTimesFound(int timesFound) {
		this.timesFound = timesFound;
	}

	public double getSentimentScore() {
		return sentimentScore;
	}

	public void setSentimentScore(double sentimentScore) {
		this.sentimentScore = sentimentScore;
	}

}
