package com.udacity.docp
package problem1

object Poker extends App {

  case class Card(r: Char, s: Char) { // rank, suite

    def this(card: Card) = this(card.r, card.s)

    def rank: Int = "--23456789TJQKA".indexOf(r)

    override def toString = r.toString + s.toString

  }

  object Card {

    /**
     * Accepts cards like: 6C, TC, 7D, etc...
     */
    def apply(chars: String): Card = {
      if (chars.length != 2)
        throw new IllegalArgumentException("cards should consist of two characters")
      Card(chars.charAt(0), chars.charAt(1))
    }

  }

  case class Hand(cards: List[Card]) {

    /**
     * Return a value indicating the ranking of a hand.
     */
    def rank: (Int, List[Int]) = {
      val straight = (ranks.max - ranks.min) == 4 && ranks.toSet.size == 5
      val flush = cards.map(_.s).toSet.size == 1

      val counts = ranks.distinct
        .map {
          rank => (rank, ranks.count(_ == rank))
        }.sortBy {
          case (rank, count) => -1 * count -> -1 * rank
        }.map(_._2)

      val r = counts match {
        case 5 :: _ => 9
        case _ if straight && flush => 8
        case 4 :: 1 :: _ => 7
        case 3 :: 2 :: _ => 6
        case _ if flush => 5
        case _ if straight => 4
        case 3 :: 1 :: 1 :: _ => 3
        case 2 :: 2 :: 1 :: _ => 2
        case 2 :: 1 :: 1 :: 1 :: _ => 1
        case _ => 0
      }
      (r, ranks)
    }

    /**
     * The list of ranks, sorted with higher first.
     */
    lazy val ranks: List[Int] = {
      val rs = cards.map(_.rank).sortBy(-_)
      if (rs == List(14, 5, 4, 3, 2)) List(5, 4, 3, 2, 1) else rs
    }
  }

  object Hand {

    def apply(cards: String): Hand = {
      val cs = cards.split(" ")
      if (cs.length != 5)
        throw new IllegalArgumentException("a hand should consist of 5 cards")
      Hand(cs.map(card => Card(card)).toList)
    }
  }

  type Deck = List[Card]

  def deck: Deck = scala.util.Random.shuffle(
    (for (c <- "23456789TJQKA"; s <- "SDHC") yield Card(c, s)).toList)

  def deal(numHands: Int, numCards: Int = 5, deck: Deck = deck): List[Hand] =
    if (numHands == 0) Nil
    else Hand(deck.take(numCards)) +:
      deal(numHands - 1, numCards, deck.drop(numCards))

  /**
   * Returns the best hand or best hands in case of a tie.
   */
  def poker(hands: Hand*): List[Hand] = {
    import scala.math.Ordering.Implicits._
    val bestHand = hands.maxBy(_.rank) // hands.max
    println(hands)
    println(bestHand)
    hands.filter(_.rank == bestHand.rank).toList
  }

}