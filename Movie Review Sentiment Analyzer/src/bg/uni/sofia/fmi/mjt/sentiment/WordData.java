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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(sentimentScore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + timesFound;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WordData other = (WordData) obj;
		if (Double.doubleToLongBits(sentimentScore) != Double.doubleToLongBits(other.sentimentScore)) {
			return false;
		}
		if (timesFound != other.timesFound) {
			return false;
		}
		if (word == null) {
			if (other.word != null) {
				return false;
			}
		} else if (!word.equals(other.word)) {
			return false;
		}
		return true;
	}
}
