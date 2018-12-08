package core;

import NeuralNetwork.NeuralNetwork;

public class Core {
	private NeuralNetwork neuralNetwork;

    public void createNetwork(int[] layers) throws IllegalArgumentException {
    	if (layers.length < 3) throw new IllegalArgumentException("Network needs at least 3 layers");
	    this.neuralNetwork = new NeuralNetwork(layers);
    }

    public double[] networkThink(double[] input) throws IllegalArgumentException {
		return(this.neuralNetwork.think(input));
    }

    public double learn(double[][] trainSet) throws IllegalArgumentException {
    	neuralNetwork.learn(trainSet);
    	return(neuralNetwork.getError());
	}

	public void updateWeights() {
    	neuralNetwork.updateWeights();
	}
}
