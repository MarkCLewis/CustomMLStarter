package customml.multivariatemethods

class NaiveBayesClassifier(xr: Seq[(Seq[Double], Seq[Double])]) {

  def classify(x: Seq[Double]): Seq[Double] = {
    ???
  }
}

object NaiveBayesClassifier {
  def apply[A](xAndClass: Seq[(Seq[Double], A)]): (NaiveBayesClassifier, Seq[A]) = {
    ???
  }
}