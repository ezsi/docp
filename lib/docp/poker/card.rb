require 'docp'

class Docp::Poker::Card
  @@CARDS = "23456789TJQKA"
  @@JOKER_CARD = '?'
  @@BLACK_CARD = 'B'

  def self.cards(s)
    @@CARDS.map do |card| Card.new(card,s) end
  end

  def self.blacks 
    Card::cards('S') + CARD::cards('C')
  end

  def self.reds
    Card::cards('D') + CARD::cards('H')
  end

  attr_reader :r
  attr_reader :s

  def initialize(r, s)
    raise ArgumentError.new('invalid color, accepted: C, H, S, D') unless %w(C H S D).include?(s)
    raise ArgumentError.new('invalid rank, accepted: 23456789TJQKA') if @@CARDS.index(r).nil? && r != @@JOKER_CARD

    @r = r
    @s = s
  end

  def substitutes
    if ( c == @@JOKER_CARD ) 
      s == @@BLACK_CARD ? Card.blacks : Card.reds
    else
      []
    end
  end

  def rank 
    @@CARDS.index(r) + 2
  end

  def joker?
    r == @@JOKER_CARD
  end

  def ==(other)
    other.r == r && other.s == s
  end

end
