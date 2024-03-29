/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.neuroph.contrib.bpbench;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.learning.ResilientPropagation;

/**
 * Class that set up neural network with resilient learning rule and predefined
 * settings
 *
 * @author Mladen Savic <mladensavic94@gmail.com>
 */
public class ResilientTraining extends AbstractTraining {

    /**
     * Create instance of a training using predefined neural network and given
     * settings
     *
     * @param neuralNet
     * @param dataset
     * @param settings
     */
    public ResilientTraining(NeuralNetwork neuralNet, DataSet dataset, TrainingSettings settings) {
        super(neuralNet, dataset, settings);
    }

    /**
     * Create instance of a training using given settings and create neural
     * network from that settings
     *
     * @param dataset
     * @param settings
     */
    public ResilientTraining(DataSet dataset, TrainingSettings settings) {
        super(dataset, settings);
    }

    /**
     * Method that set up learning rule with given settings, learns dataset and
     * creates statistics from results of the test
     */
    @Override
    public void testNeuralNet() {
        ResilientPropagation rp = (ResilientPropagation) setParameters();
        getNeuralNet().setLearningRule(rp);
        getNeuralNet().learn(getDataset());
        this.getStats().addData(new TrainingResult(rp.getCurrentIteration(), rp.getTotalNetworkError(), createMatrix()));
        this.getStats().calculateParameters();

    }

    /**
     * Create instance of learning rule and setup given parameters
     *
     * @return returns learning rule with predefined parameters
     */
    @Override
    public LearningRule setParameters() {
        ResilientPropagation rp = new ResilientPropagation();
        rp.setBatchMode(getSettings().isBatchMode());
        rp.setMaxError(getSettings().getMaxError());
        rp.setMaxIterations(getSettings().getMaxIterations());
        rp.setDecreaseFactor(getSettings().getDecreaseFactor());
        rp.setIncreaseFactor(getSettings().getIncreaseFactor());
        rp.setInitialDelta(getSettings().getInitialDelta());
        rp.setMaxDelta(getSettings().getMaxDelta());
        rp.setMinDelta(getSettings().getMinDelta());
        return rp;
    }

}
