package customml.mlp

/**
 * Implements and trains an MLP with a single hidden layer of the specified number of nodes.
 */
class MultilayerPerceptron(xr: Seq[(Seq[Double], Seq[Double])], hidden: Int, usage: Perceptron.Value) {
  val outputs = if (usage == Perceptron.Classifier && xr.head._2.length < 3) 1 else xr.head._2.length
  val inputs = xr.head._1.length + 1 // Add one for the bias
  val w = Array.fill(hidden, inputs)(math.random * 0.01 - 0.005)
  val v = Array.fill(outputs, hidden + 1)(math.random * 0.01 - 0.005)

  train()

  /**
   * This one method is used to get the output of the perceptron(s) with an appropriate function
   * applied for the use case.
   */
  def fit(x: Seq[Double]): Seq[Double] = pfit(x)._2

  /**
   * For training you need both z and y values. This method returns both.
   */
  private def pfit(x: Seq[Double]): (Seq[Double], Seq[Double]) = {
    ???
  }

  /**
   * This should only be called when trained for classification. I am providing it as it might help
   * you to understand what the fit function is supposed to do.
   */
  def classify(x: Seq[Double]): Seq[Double] = {
    require(usage == Perceptron.Classifier)
    val probabilities = fit(x)
    val index = if (probabilities.length == 1) {
      if (probabilities.head > 0.5) 0 else 1
    } else probabilities.zipWithIndex.maxBy(_._1)._2
    Seq.tabulate(xr.head._2.length)(i => if (index == i) 1.0 else 0.0)
  }

  /**
   * This method will adjust the weights appropriately so that the outputs do a good job
   * of matching what is desired for the provided inputs. Note that this method is intended
   * to mutate the weights array. We talked about a lot of possible options for doing this.
   * You can pick which ones you use.
   * 
   * Hint: the fit function can be used to calculate y. I had to do more to make this pass the
   * tests regularly. I used an adaptive learning factor and a maximum of 20,000 epochs that
   * would break out if the error got more than 20% above a minimum. I also stored the minimum
   * error configuration (w and v) and restored that at the end.
   */
  private def train(): Unit = {
    ???
  }
}