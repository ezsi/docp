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
    def rank: List[Int] = {
      if (straight && flush)
        List(8, ranks.max)

      else if (!kind(4).isEmpty)
        List(7, kind(4).get, kind(1).get)

      else if (!kind(3).isEmpty && !kind(2).isEmpty)
        List(6, kind(3).get, kind(2).get)

      else if (flush)
        5 +: ranks

      else if (straight)
        List(4, ranks.max)

      else if (!kind(3).isEmpty)
        List(3, kind(3).get) ++ ranks

      else if (!twoPair.isEmpty)
        List(2, twoPair.get._1, twoPair.get._2) ++ ranks

      else if (!kind(2).isEmpty)
        List(1, kind(2).get) ++ ranks

      else
        0 +: ranks
    }

    /**
     * The list of ranks, sorted with higher first.
     */
    lazy val ranks: List[Int] = {
      val rs = cards.map(_.rank).sortBy(-_)
      if (rs == List(14, 5, 4, 3, 2)) List(5, 4, 3, 2, 1) else rs
    }

    def straight: Boolean =
      (ranks.max - ranks.min) == 4 && ranks.toSet.size == 5

    def flush: Boolean = cards.map(_.s).toSet.size == 1

    /**
     * Returns the first rank that this hand has exactly n of.
     */
    def kind(kind: Int, ranks: List[Int] = ranks): Option[Int] =
      ranks.distinct.map(item => (item, ranks.count(_ == item)))
        .filter {
          case (rank, count) if count == kind => true
          case _ => false
        }.map(_._1).headOption

    /**
     * Returns the 2 pairs as a tuple(highest, lowest)
     */
    def twoPair: Option[(Int, Int)] = {
      val highPair = kind(2)
      val lowPair = kind(2, ranks.reverse)
      (highPair, lowPair) match {
        case (Some(high), Some(low)) if high != low => Some((high, low))
        case _ => None
      }
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
    hands.filter(_.rank == bestHand.rank).toList
  }

}