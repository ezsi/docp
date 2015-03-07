require 'docp'

module Docp::Poker 

  require_relative 'poker/card'

  def self.poker

    Card.new('K','S')
  end

end