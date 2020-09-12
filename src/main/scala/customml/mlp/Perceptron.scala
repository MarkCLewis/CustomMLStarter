package customml.mlp
import scala.reflect.ClassTag

/**
 * This class is intended to implement a single layer perceptron(s) that is only capable of
 * doing linear classification or regression. Because there is so much code shared between them,
 * this one class will do both classification and regression. The second argument tells you
 * whether you are training for regression of classification. The only alters the function that
 * you put on the outputs to get probabilities. Note that this function is also used for the
 * purposes of training.
 */
class Perceptron(xr: Seq[(Seq[Double], Seq[Double])], usage: Perceptron.Value) {
  val outputs = if (usage == Perceptron.Classifier && xr.head._2.length < 3) 1 else xr.head._2.length
  val inputs = xr.head._1.length + 1 // Add one for the bias
  val w = Array.fill(outputs, inputs)(math.random * 0.01 - 0.005)

  train()

  /**
   * This one method is used to get the output of the perceptron(s) with appropriate an function
   * applied for the use case.
   */
  def fit(x: Seq[Double]): Seq[Double] = {
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
   * Hint: the fit function can be used to calculate y. I got mine to pass the tests with a fixed 
   * learning factor of 0.0003 and 10,000 epochs of online learning. If I made the learning factor
   * higher my weights would blow up to infinity and then give me a bunch of NaNs. You can probably
   * avoid that with an adpative learning rate.
   */
  private def train(): Unit = {
    ???
  }
}

object Perceptron extends Enumeration {
  val Classifier, Regressor = Value

  /**
   * I'm giving you this function to make it easy to run through th inputs in random order
   * for online learning.
   */
  def randomized[A: ClassTag](xs: Seq[A]): Seq[A] = {
    val copy = xs.map(x => x).toArray
    for (i <- 0 until copy.length-1) {
      val r = util.Random.nextInt(copy.length - i) + i
      val tmp = copy(r)
      copy(r) = copy(i)
      copy(i) = tmp
    }
    copy
  }
}