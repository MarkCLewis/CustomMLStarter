package customml
import scala.reflect.ClassTag

object UtilityFunctions {
  def euclidean(x1: Seq[Double], x2: Seq[Double]): Double = {
    math.sqrt((x1, x2).zipped.foldLeft(0.0)((sum, t) => sum + (t._1 - t._2) * (t._1 - t._2)))
  }
  
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