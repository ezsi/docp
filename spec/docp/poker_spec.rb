require 'spec_helper'

describe Docp::Poker do

  shared_context 'straight flush' do 
    let(:hand) { Hand.new(Hand("6C 7C 8C 9C TC")) }
  end

  context "given a flush" 
    include_context 'straight flush'
    
    it("should return a rank of (8, 10, 9, 8, 7, 6)") {
      expect(sf.rank).to eq [8, 10, 9, 8, 7, 6]
    }
  end

  it 'should be ok' do
    actual = Docp::Poker.poker
    expected = Docp::Poker::Card.new('K', 'S')
    expect(actual).to eq expected
  end

end