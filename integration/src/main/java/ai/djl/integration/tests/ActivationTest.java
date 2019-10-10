/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.integration.tests;

import ai.djl.Model;
import ai.djl.integration.util.Assertions;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataDesc;
import ai.djl.ndarray.types.Shape;
import ai.djl.training.Activation;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import ai.djl.training.TrainingConfig;
import ai.djl.training.initializer.Initializer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ActivationTest {

    private TrainingConfig config = new DefaultTrainingConfig(Initializer.ONES);

    @Test
    public void testRelu() {
        try (Model model = Model.newInstance()) {
            model.setBlock(Activation.reluBlock());

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {-1, 0, 2});
                NDArray expected = manager.create(new float[] {0, 0, 2});
                Assert.assertEquals(expected, Activation.relu(original));
                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assert.assertEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testSigmoid() {
        try (Model model = Model.newInstance()) {
            model.setBlock(Activation.sigmoidBlock());

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {0});
                NDArray expected = manager.create(new float[] {0.5f});
                Assertions.assertAlmostEquals(expected, Activation.sigmoid(original));

                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assertions.assertAlmostEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testTanh() {
        try (Model model = Model.newInstance()) {
            model.setBlock(Activation.tanhBlock());

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {0});
                NDArray expected = manager.create(new float[] {0});
                Assert.assertEquals(expected, Activation.tanh(original));
                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assert.assertEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testSoftrelu() {
        try (Model model = Model.newInstance()) {
            model.setBlock(Activation.softreluBlock());

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {0, 0, 2});
                NDArray expected = manager.create(new float[] {.6931f, .6931f, 2.1269f});
                Assertions.assertAlmostEquals(expected, Activation.softrelu(original));

                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assertions.assertAlmostEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testLeakyrelu() {
        try (Model model = Model.newInstance()) {
            float alpha = 1.0f;
            model.setBlock(Activation.leakyReluBlock(alpha));

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {-1, 0, 2});
                NDArray expected = manager.create(new float[] {-1, 0, 2});
                Assert.assertEquals(expected, Activation.leakyRelu(original, alpha));

                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assertions.assertAlmostEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testElu() {
        try (Model model = Model.newInstance()) {
            float alpha = 1.0f;
            model.setBlock(Activation.eluBlock(alpha));

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {0, 2});
                NDArray expected = manager.create(new float[] {0, 2});
                Assert.assertEquals(expected, Activation.elu(original, alpha));

                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assert.assertEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testSelu() {
        try (Model model = Model.newInstance()) {
            model.setBlock(Activation.seluBlock());

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {0});
                NDArray expected = manager.create(new float[] {0});
                Assert.assertEquals(expected, Activation.selu(original));

                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assert.assertEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testGelu() {
        try (Model model = Model.newInstance()) {
            model.setBlock(Activation.geluBlock());

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {0});
                NDArray expected = manager.create(new float[] {0});
                Assert.assertEquals(expected, Activation.gelu(original));

                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assert.assertEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testSwish() {
        try (Model model = Model.newInstance()) {
            float beta = 1.0f;
            model.setBlock(Activation.swishBlock(beta));

            try (Trainer trainer = model.newTrainer(config)) {
                NDManager manager = trainer.getManager();
                NDArray original = manager.create(new float[] {0});
                NDArray expected = manager.create(new float[] {0});
                Assert.assertEquals(expected, Activation.swish(original, beta));

                NDList expectedList = new NDList(expected);
                NDList ndList = new NDList(original);
                Assert.assertEquals(expectedList, trainer.forward(ndList));
            }
        }
    }

    @Test
    public void testPrelu() {
        try (Model model = Model.newInstance()) {
            model.setBlock(Activation.preluBlock());

            try (Trainer trainer = model.newTrainer(config)) {
                trainer.initialize(new DataDesc[] {new DataDesc(new Shape(3))});
                NDManager manager = trainer.getManager();

                NDArray original = manager.create(new float[] {-1, 0, 2});
                NDList expected = new NDList(manager.create(new float[] {-1, 0, 2}));
                Assert.assertEquals(expected, trainer.forward(new NDList(original)));
            }
        }
    }
}