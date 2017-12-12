package bg.uni.sofia.fmi.mjt.sentiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Testing {

	public static void main(String[] args) {

		List<WordData> list = new ArrayList<WordData>();
		list.add(new WordData("zdravei", 1, 2.6));
		list.add(new WordData("obich", 1, 3.8));
		list.add(new WordData("ujasno", 1, 1.2));

		System.out.println(Arrays.toString(
				list.stream().sorted((p1, p2) -> Double.compare(p2.getSentimentScore(), p1.getSentimentScore()))
						.map(p1 -> p1.getWord()).toArray()));
	}

}
