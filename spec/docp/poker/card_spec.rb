require 'spec_helper'

describe Docp::Poker::Card do

  context 'regular card' do 
    let(:card) { Docp::Poker::Card.new('K', 'C') }

    it 'should not be a joker' do
      expect(card.joker?).not_to be true
    end

    it 'should rank 13' do 
      expect(card.rank).to be 13
    end

  end

  context 'invalid card' do

    it 'with wrong color should raise expection' do 
      expect { Docp::Poker::Card.new('K', 'Y') }.to raise_error(ArgumentError, 'invalid color, accepted: C, H, S, D')
    end

    it 'with wrong rank should raise expection' do 
      expect { Docp::Poker::Card.new('1', 'S') }.to raise_error(ArgumentError, 'invalid rank, accepted: 23456789TJQKA')
    end

  end

  context 'joker card' do 
    let(:card) { Docp::Poker::Card.new('?', 'S') }

    it 'should be a joker' do
      expect(card.joker?).to be true
    end
  end

end
