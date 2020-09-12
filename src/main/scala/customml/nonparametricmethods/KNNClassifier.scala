package customml.nonparametricmethods

/**
 * This is a k-nn classifier. Note that you don't ever calculate distances between
 * the output values, r, so they aren't one-hot encoded here. Use the provided distance
 * function to calculate the distances between x values.
 */
class KNNClassifier[A](k: Int, xr: Seq[(Seq[Double], A)], distance: (Seq[Double], Seq[Double]) => Double) {
  def classify(x: Seq[Double]): A = {
    ???
  }
}