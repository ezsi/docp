package com.udacity.docp
package problem1

import Poker._
import org.scalatest._

class PokerTest extends FlatSpec with Matchers {

  val sf = Hand("6C 7C 8C 9C TC") // straight flush
  val fk = Hand("9D 9H 9S 9C 7D") // four of a kind
  val fh = Hand("TD TC TH 7C 7D") // full house
  val tp = Hand("5S 5D 9H 9C 6S") // two pair
  val s1 = Hand("AS 2S 3S 4S 5C") // A-5 straight
  val s2 = Hand("2C 3C 4C 5S 6S") // 2-6 straight
  val ah = Hand("AS 2S 3S 4S 6C") // A high
  val sh = Hand("2S 3S 4S 6C 7D") // 7 high

  "A hand of s1, s2, ah, sh" should "return s2" in {
    poker(s1, s2, ah, sh) should be(List(s2))
  }

  "A kind of fk" should "return 9, None, None, 7" in {
    fk.kind(4) should be(Some(9))
    fk.kind(3) should be(None)
    fk.kind(2) should be(None)
    fk.kind(1) should be(Some(7))
  }

  "A two pair of fk" should "return None" in {
    fk.twoPair should be(None)
  }

  "A two pair of tp" should "return (9, 5)" in {
    tp.twoPair should be(Some((9, 5)))
  }

  "A straight of List(9, 8, 7, 6, 5) " should "return true" in {
    Hand("9C 8C 7C 6C 5C").straight should be(true)
  }

  "A straight of List(9, 8, 8, 6, 5) " should "return false" in {
    Hand("9C 8C 8C 6C 5C").straight should be(false)
  }

  "A flush of sf" should "return true" in {
    sf.flush should be(true)
  }

  "A flush of fk" should "return false" in {
    fk.flush should be(false)
  }

  "A card rank for sf " should "return List(10, 9, 8, 7, 6)" in {
    sf.ranks should be(List(10, 9, 8, 7, 6))
  }

  "A card rank for fk " should "return List(9, 9, 9, 9, 7)" in {
    fk.ranks should be(List(9, 9, 9, 9, 7))
  }

  "A card rank for fh " should "return List(10, 10, 10, 7, 7)" in {
    fh.ranks should be(List(10, 10, 10, 7, 7))
  }

  "A sf " should "have a higher rank than fk, fh" in {
    poker(sf, fk, fh) should be(List(sf))
    poker(fh, sf, fk) should be(List(sf))
  }

  "A fk " should "have a higher rank than fh" in {
    poker(fk, fh) should be(List(fk))
  }

  "A fh " should "have return a tie" in {
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

  "A sf rank" should "return (8, 10)" in {
    sf.rank should be(List(8, 10))
  }

  "A fk rank" should "return (7, 9, 7)" in {
    fk.rank should be(List(7, 9, 7))
  }

  "A fh rank" should "return (6, 10, 7)" in {
    fh.rank should be(List(6, 10, 7))
  }

}