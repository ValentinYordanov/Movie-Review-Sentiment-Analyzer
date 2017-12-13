package bg.uni.sofia.fmi.mjt.sentiment;

public class WordData {

	private int timesFound;
	private double sentimentScore;

	public WordData(int timesFound, double sentimentScore) {
		this.timesFound = timesFound;
		this.sentimentScore = sentimentScore;
	}

	public int getTimesFound() {
		return timesFound;
	}

	public void setTimesFound(int timesFound) {
		this.timesFound = timesFound;
	}

	public double getCurrentSentimentScore() {
		return this.sentimentScore;
	}

	public double getSentimentScore() {
		return (double) sentimentScore / timesFound;
	}

	public void setSentimentScore(double sentimentScore) {
		this.sentimentScore = sentimentScore;
	}

}
