package core;

import NeuralNetwork.NeuralNetwork;

public class Core {
	//TODO: Remover saídas de texto do core, saídas de texto devem ser feitas pelo cliente ou GUI
	private NeuralNetwork neuralNetwork;
	private TrainingSet trainingSet;

	public Core (int[] networkLayers) throws IllegalArgumentException {
		if (networkLayers.length < 3) throw new IllegalArgumentException("Network needs at least 3 layers");
		this.neuralNetwork = new NeuralNetwork(networkLayers);
		this.trainingSet = null;
	}

    public double[] think(double[] input) throws IllegalArgumentException {
		return(this.neuralNetwork.think(input));
    }

    public void setTrainingSet(double[][][] trainingSet) {
		this.trainingSet = new TrainingSet(trainingSet);
	}

    public void train(int iterations) throws NullPointerException,IllegalStateException {
		if (trainingSet == null) throw new IllegalStateException("TrainingSet is not defined");
		double[][][] weight = neuralNetwork.getWeight();
		for (int set=0; set<iterations; set++) {
			double error = 0;
			while (trainingSet.hasNext()){
				double[][] exercise = trainingSet.getNext();
				double[][][] adjustmentWeight = neuralNetwork.learn(exercise);
				error += neuralNetwork.getError();

				//Adjust the weight
				for (int i=1; i<weight.length; i++) {
					for (int j=0; j<weight[i].length; j++) {
						for (int k=0; k<weight[i][j].length; k++) {
							weight[i][j][k] += adjustmentWeight[i][j][k];
						}
					}
				}
			}
			neuralNetwork.setWeight(weight);
			System.out.printf("Error for iteration %d: %f\n", set, error/trainingSet.getSize());
		}
	}

	public void setWeight(double[][][] weight) {
		try {
			neuralNetwork.setWeight(weight);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Weight matrix size mismatch");
		}
	}
}
