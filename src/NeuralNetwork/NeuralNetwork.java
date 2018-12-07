package NeuralNetwork;

import java.util.stream.DoubleStream;

public class NeuralNetwork {
	private double error;
	private double[][] z, bias, layer;
	private double[][][] weight, adjustmentWeight;

	public NeuralNetwork(int[] layers) {
		int size = layers.length;

		//Create layers's matrix
		this.layer = new double[size][];
		for(int layerNum=0; layerNum<size; ++layerNum) {
			layer[layerNum] = new double[layers[layerNum]];
		}

		/*  Create z, weight, adjustmentWeight and bias's matrix
			The z matrix is the neuron's input array for each layer
			The weight's matrix are in the MxN format, M being the neurons of the next layer and N the neurons of the previous layer
			The adjustmentWeight's matrix are the calibration values for the weight's matrix (they have the same size)
			*** bias is currently not used ****/
		this.z = new double[size][];
		this.weight = new double[size][][];
		this.adjustmentWeight = new double[size][][];
		this.bias = new double[size][];
		for(int layerNum=1; layerNum<size; ++layerNum) {
			weight[layerNum] = new double[layers[layerNum]][layers[layerNum-1]];
			adjustmentWeight[layerNum] = new double[layers[layerNum]][layers[layerNum-1]];
			bias[layerNum] = new double[layers[layerNum]];
		}

		//Fill in the biases with 0
		for(int m=1; m<bias.length; ++m) {
			for(int n=0; n<bias[m].length; ++n) {
				bias[m][n] = 0.0;
			}
		}

		//Fill in weight matrix with random values from -5 to 5 that are not 0
		for(int layerNum=1; layerNum<weight.length; ++layerNum) {
			for(int m=0; m<weight[layerNum].length; ++m) {
				for(int n=0; n<weight[layerNum][m].length; ++n) {
					weight[layerNum][m][n] = 0.0;
					while(weight[layerNum][m][n] == 0) {
						weight[layerNum][m][n] = Math.random() * 10 - 5;
					}
				}
			}
		}
		System.out.printf("Created Neural network with: %d layers\n", size);
	}

	public double[] think(double[] input) {
		if(layer[0].length != input.length) {
			//TODO: Throw exception
			System.out.println("Empty think input");
		}
		layer[0] = input;
		for(int i=1; i<layer.length; ++i){
			z[i] = SimpleMatrixLibrary.multiply(weight[i], layer[i-1]);    //Calculate Z
			for(int j=0; j<layer[i].length; ++j){
				layer[i][j] = nonLinear(z[i][j]);       //Calculate activation function for each layer
			}
		}
		return(layer[layer.length-1]);                  //Return last neuron's output as an array
	}

	//TODO: Modificar o nome 'learn' da função para atender mais precisamente o q ela faz
	public void learn(double[][] learnSet) {
		System.out.print("Input: ");
		for (int i=0; i<learnSet[0].length; i++) {
			System.out.printf("%f ", learnSet[0][i]);
		}
		System.out.print("\nTarget output: ");
		for (int i=0; i<learnSet[1].length; i++) {
			System.out.printf("%f ", learnSet[0][i]);
		}
		System.out.println();
		/*
			learnSet[0][] are the inputs
			learnSet[1][] are the expected outputs (the right answers)
		*/
		double[][] sigma = new double[layer.length][];

		//Train the network
		double[] output = think(learnSet[0]);
		double[] error = new double[output.length];
		for(int neuron=0; neuron<output.length; ++neuron){
			error[neuron] = output[neuron] - learnSet[1][neuron];
		}
		this.error = Math.abs(DoubleStream.of(error).sum()/output.length);

		//Backpropagation
		//Calculate sigma of last neuron
		sigma[layer.length-1] = new double[output.length];
		for(int neuron=0; neuron<output.length; ++neuron){
			sigma[layer.length-1][neuron] = error[neuron] * nonLinearDerivative(z[z.length-1][neuron]);
		}

		//Sigma of the others neurons
		for(int layerNum=layer.length-2; 0<layerNum; --layerNum){
			sigma[layerNum] = SimpleMatrixLibrary.multiply(SimpleMatrixLibrary.transposeMatrix(weight[layerNum+1]), sigma[layerNum+1]);
			for(int neuron=0; neuron<sigma[layerNum].length; ++neuron){
				sigma[layerNum][neuron] = sigma[layerNum][neuron] * nonLinearDerivative(z[layerNum][neuron]);
			}
		}

		//Calculate adjustment weight
		for(int layerNum=layer.length-1; 0<layerNum; --layerNum){
			for(int m=0; m<weight[layerNum].length; ++m){
				for(int n=0; n<weight[layerNum][0].length; ++n){
					this.adjustmentWeight[layerNum][m][n] = 0.0;	//Reset adjustmentWeight's values
					this.adjustmentWeight[layerNum][m][n] -= SimpleMatrixLibrary.multiply(sigma[layerNum], layer[layerNum-1])[m][n];
				}
			}
		}
	}

	private static double nonLinear(double x){
		return(1/( 1 + Math.pow(Math.E,(-1*x))));
	}

	private static double nonLinearDerivative(double x){
		double sigmoid = nonLinear(x);
		return(sigmoid*(1-sigmoid));
	}

	public void updateWeights() {
		for(int layerNum=layer.length-1; 0<layerNum; --layerNum){
			for(int m=0; m<weight[layerNum].length; ++m){
				for(int n=0; n<weight[layerNum][0].length; ++n){
					this.weight[layerNum][m][n] += this.adjustmentWeight[layerNum][m][n];
					this.adjustmentWeight[layerNum][m][n] = 0.0;	//Reset adjustmentWeight's values
				}
			}
		}
	}

	public double getError() {
		return(this.error);
	}

	public double[][] getLayers() {
		return(this.layer);
	}

	public double[][][] getWeight() {
		return(this.weight);
	}
}
