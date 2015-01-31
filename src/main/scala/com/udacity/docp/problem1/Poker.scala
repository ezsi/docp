package com.udacity.docp
package problem1

import scala.math.Ordering.Implicits._

object Poker extends App {

  case class Card(r: Char, s: Char) { // rank, suite
    private val JOKER_CHAR = '?'
    private val BLACK_CHAR = 'B'
    private val RED_CHAR = 'R'

    def this(card: Card) = this(card.r, card.s)

    def rank: Int = Card.CARDS.indexOf(r) + 2

    def joker: Boolean = r == '?'

    def substitutes: Seq[Card] = this match {
      case Card(JOKER_CHAR, BLACK_CHAR) => Card.blacks
      case Card(JOKER_CHAR, RED_CHAR) => Card.reds
      case _ => Seq()
    }

    override def toString = r.toString + s.toString
  }

  object Card {
    private val CARDS = "23456789TJQKA"

    /**
     * Accepts cards like: 6C, TC, 7D, etc...
     */
    def apply(chars: String): Card = {
      if (chars.length != 2)
        throw new IllegalArgumentException("cards should consist of two characters")
      Card(chars.charAt(0), chars.charAt(1))
    }

    def blacks = (for (c <- CARDS; s <- "SC") yield Card(c, s))

    def reds = (for (c <- CARDS; s <- "DH") yield Card(c, s))

  }

  case class Hand(cards: Set[Card]) {

    /**
     * Return a value indicating the ranking of a hand.
     */
    def rank: List[Int] = {

      /**
       * The list of ranks, sorted with higher first.
       */
      def ranks: List[Int] = {
        val rs = cards.toList.map(_.rank).sortBy(-_)
        if (rs == List(14, 5, 4, 3, 2)) List(5, 4, 3, 2, 1) else rs
      }

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
      r +: ranks
    }

    def +(card: Card) = Hand(cards + card)

  }

  object Hand {

    def apply(cards: String): Hand =
      Hand(cards.split(" ").map(card => Card(card)).toSet)
  }

  type Deck = List[Card]

  def deck: Deck = scala.util.Random.shuffle((Card.reds ++ Card.blacks).toList)

  def deal(numHands: Int, numCards: Int = 5, deck: Deck = deck): List[Hand] =
    if (numHands == 0) Nil
    else Hand(deck.take(numCards).toSet) +:
      deal(numHands - 1, numCards, deck.drop(numCards))

  /**
   * Returns the best hand or best hands in case of a tie.
   */
  def poker(hands: Hand*): List[Hand] = {
    val bestHand = hands.maxBy(_.rank) // hands.max
    hands.filter(_.rank == bestHand.rank).toList
  }

  def bestWildHand(hand: Hand): List[Hand] = {
    require(hand.cards.size == 7)
    val (jokers, cards) = hand.cards.partition(_.joker)

    var allHands: Set[Hand] = Set(Hand(cards))
    jokers.foreach { joker =>
      allHands = for {
        hand <- allHands
        substitute <- joker.substitutes
      } yield hand + substitute
    }

    val hands: List[Hand] = allHands.flatMap { hand =>
      hand.cards.toSet.subsets(5).map(Hand(_))
    }.toList
    poker(hands: _*)
  }

}