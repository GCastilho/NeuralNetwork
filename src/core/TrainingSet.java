package core;

public class TrainingSet {
	private double[][][] trainingSet;
	private int setNum;

	//TODO: Capacidade de carregar o trainingSet de um arquivo (recebendo a localização do arquivo como parâmetro)
	TrainingSet(double[][][] trainingSet) {
		this.trainingSet = trainingSet;
		this.setNum = 0;
	}

	public boolean hasNext() {
		if (trainingSet.length <= setNum) {
			this.setNum = 0;
			return (false);
		} else {
			return (true);
		}
	}

	public double[][] getNext() {
		if (trainingSet.length <= setNum) {
			return (null);
		} else {
			this.setNum++;
			return (trainingSet[setNum-1]);
		}
	}

	public int getSize() {
		return (this.trainingSet.length);
	}
}
