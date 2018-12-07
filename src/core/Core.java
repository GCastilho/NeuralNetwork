package core;

import NeuralNetwork.NeuralNetwork;

public class Core {
    public static void main(String[] args) {
    	// Prova de conceito e testes básicos
    	System.out.println("Hey, Vsauce. Michael Here!");
    	int[] layers = {2,3,1};
    	NeuralNetwork neuralNetwork = new NeuralNetwork(layers);

    	double[] entrada = {1.0, 0.0};
    	double[] pensamento = neuralNetwork.think(entrada);
    	for (int i=0; i<pensamento.length; i++){
    		System.out.printf("Neuron %d: %f\n", i, pensamento[i]);
	    }

	    double[][] treinamento = { {1.0, 0.0}, {1.0} };
	    neuralNetwork.learn(treinamento);
	    neuralNetwork.updateWeights();
	    System.out.printf("Error: %f\n", neuralNetwork.getError());

	    pensamento = neuralNetwork.think(entrada);
	    for (int i=0; i<pensamento.length; i++){
		    System.out.printf("Neuron %d: %f\n", i, pensamento[i]);
	    }
    }
}
