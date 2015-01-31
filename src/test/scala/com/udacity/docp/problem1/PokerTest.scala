package com.udacity.docp
package problem1

import Poker._
import org.scalatest._

class PokerTest extends FlatSpec with Matchers {

  val sf = Hand("6C 7C 8C 9C TC") // straight flush
  val fk = Hand("9D 9H 9S 9C 7D") // four of a kind
  val fh = Hand("TD TC TH 7C 7D") // full house
  val f = Hand("2C 7C 4C 9C TC") // flush
  val s1 = Hand("AS 2S 3S 4S 5C") // A-5 straight
  val s2 = Hand("2C 3C 4C 5S 6S") // 2-6 straight
  val tk = Hand("TD TC TH 4C 7D") // three of a kind
  val tp = Hand("5S 5D 9H 9C 6S") // two pair
  val sp = Hand("5S 5D AH 9C 6S") // single pair
  val ah = Hand("AS 2S 3S 4S 6C") // A high
  val sh = Hand("2S 3S 4S 6C 7D") // 7 high
  val w1 = Hand("6C 7C 8C 9C TC 5C ?B")
  val w2 = Hand("TD TC 5H 5C 7C ?R ?B")
  val w3 = Hand("JD TC TH 7C 7D 7S 7H")

  "A sf rank" should "return straight flush" in {
    sf.rank should be(List(8, 10, 9, 8, 7, 6))
  }

  "A fk rank" should "return four of a kind" in {
    fk.rank should be(List(7, 9, 9, 9, 9, 7))
  }

  "A fh rank" should "return full house" in {
    fh.rank should be(List(6, 10, 10, 10, 7, 7))
  }

  "A f rank" should "return flush" in {
    f.rank should be(List(5, 10, 9, 7, 4, 2))
  }

  "A s1 rank" should "return straight" in {
    s1.rank should be(List(4, 5, 4, 3, 2, 1))
  }

  "A s2 rank" should "return straight" in {
    s2.rank should be(List(4, 6, 5, 4, 3, 2))
  }

  "A tk rank" should "return three of a kind" in {
    tk.rank should be(List(3, 10, 10, 10, 7, 4))
  }

  "A tp rank" should "return two pairs" in {
    tp.rank should be(List(2, 9, 9, 6, 5, 5))
  }

  "A sp rank" should "return single pair" in {
    sp.rank should be(List(1, 14, 9, 6, 5, 5))
  }

  "An ah rank" should "return ace high" in {
    ah.rank should be(List(0, 14, 6, 4, 3, 2))
  }

  "An sh rank" should "return 7 high" in {
    sh.rank should be(List(0, 7, 6, 4, 3, 2))
  }

  "A hand of s1, s2, ah, sh" should "return s2" in {
    poker(s1, s2, ah, sh) should be(List(s2))
  }

  "A hand of s1, fk, ah, sh" should "return s2" in {
    poker(s1, fk, ah, sh) should be(List(fk))
  }

  "A sf " should "have a higher rank than fk, fh" in {
    poker(sf, fk, fh) should be(List(sf))
    poker(fh, sf, fk) should be(List(sf))
  }

  "A fk " should "have a higher rank than fh" in {
    poker(fk, fh) should be(List(fk))
  }

  "A fh " should "return a tie" in {
    poker(fh, fh) should be(List(fh, fh))
  }

  "A single element " should "be the only best rank" in {
    poker(sf) should be(List(sf))
    poker(fk) should be(List(fk))
    poker(fh) should be(List(fh))
  }

  "A sf" should "have higher rank than many fhS" in {
    val hands = Array.concat(Array(sf), Array.fill(99)(fh))
    poker(hands: _*) should be(List(sf))
  }

  "An A-5 straight" should "be smaller than a 2-6 straight" in {
    poker(s1, s2) should be(List(s2))
    poker(s2, s1) should be(List(s2))
  }

  "Best w1 wild hand" should "return (7C 8C 9C JC TC)" in {
    bestWildHand(w1) should be(List(Hand("7C 8C 9C JC TC")))
  }

  "Best w2 wild hand" should "return (7C 8C 9C JC TC)" in {
    bestWildHand(w2) should be(List(Hand("7C TC TD TH TS")))
  }

  "Best w3 wild hand" should "return (7C 7D 7H 7S JD)" in {
    bestWildHand(w3) should be(List(Hand("7C 7D 7H 7S JD")))
  }
}