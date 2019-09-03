package customml.parametricmethods

/**
 * This is an implementation of the 1D parametric classifier. The input is a sequence of
 * x and r values. Because it is 1D, here is only one Double for the x and the r is
 * a one-hot encoded sequence.
 */
class Classifier1D(xr: Seq[(Double, Seq[Double])]) {
  
  /**
   * Given a value for x, return one-hot encoding for the class that it belong in.
   */
  def classify(x: Double): Seq[Double] = {
    ???
  }
}

object Classifier1D {
  /**
   * This is an alternate way of making a classifier where the r values are not
   * one-hot encoded. They can be whatever type you want, it should just be a fairly small
   * set of value. The result is both a classifier and a lookup table to let the user
   * know which value of type A corresponds with each class in the one-hot encoded
   * r values that are spit out by the classifier.
   */
  def apply[A](xAndClass: Seq[(Double, A)]): (Classifier1D, Seq[A]) = {
    ???
  }
}