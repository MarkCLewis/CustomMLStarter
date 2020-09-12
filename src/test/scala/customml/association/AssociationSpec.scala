package customml.association

import org.scalatest.flatspec._
import org.scalatest.matchers._
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import customml.association.Associations.Association

class AssociationSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds

  "insufficient support" should "zero matches" in {
    val groupings = Seq(Set(1), Set(2), Set(3), Set(4), Set(5), Set(1,2), Set(3,4))
    val res = Associations.apriori(groupings, 2, 0.3, 0.01)
    res should be (Set())
  }

  "insufficient confidence" should "zero matches" in {
    val groupings = Seq(Set(1), Set(2), Set(3), Set(4), Set(5), Set(1,2), Set(3,4))
    val res = Associations.apriori(groupings, 2, 0.01, 0.75)
    res should be (Set())
  }

  "simple association" should "one matches" in {
    val groupings = Seq(Set(1), Set(2), Set(3), Set(4), Set(5), Set(1,2), Set(1))
    val res = Associations.apriori(groupings, 2, 0.1, 0.5)
    res should be (Set(Association(Set(2),Set(1))))
  }

  "medium association" should "give five matches" in {
    val groupings = Seq(Set(1,2,3), Set(4,5,2), Set(1,2,4), Set(2,4,6), Set(1,2,3,4,5), Set(1,2,3,6), Set(1,2,3,5,6))
    val res = Associations.apriori(groupings, 3, 0.3, 0.75)
    res should be (Set(Association(Set(1, 3),Set(2)), Association(Set(2, 3),Set(1)), Association(Set(1),Set(2, 3)), Association(Set(1, 2),Set(3)), Association(Set(3),Set(1, 2))))
  }
}