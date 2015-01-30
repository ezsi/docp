package com.udacity.docp.problem1;

import java.util.*;

public class Poker {

    public static class Card {

        private static final char JOKER_CARD = '?';
        private static final char BLACK_CARD = 'B';
        private static final char RED_CARD = 'R';
        private static final String CARDS = "23456789TJQKA";
        private static final char[] CARD_CHARS = CARDS.toCharArray();

        private char r;
        private char s;

        public Card(char r, char s) {
            this.r = r;
            this.s = s;
        }

        public int rank() {
            return CARDS.indexOf(r) + 2;
        }

        public boolean joker() {
            return r == JOKER_CARD;
        }

        public static List<Card> cards(char suite) {
            ArrayList<Card> list = new ArrayList<>();
            for( char c : CARD_CHARS ) {
                list.add(new Card(c, suite));
            }
            return list;
        }

        public static List<Card> cards(char ... suites) {
            List<Card> cards = new ArrayList<>();
            for( int i = 0; i < suites.length; i++ ) {
                cards.addAll(cards(suites[i]));
            }
            return cards;
        }

        public List<Card> substitutes() {
            if( r == JOKER_CARD && s == BLACK_CARD) {
                return cards('S', 'C');
            } else if ( r == JOKER_CARD && s == RED_CARD) {
                return cards('D', 'H');
            }
            return new ArrayList<>();
        }

        @Override
        public String toString() {
            return "" + r + s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Card card = (Card) o;

            if (r != card.r) return false;
            if (s != card.s) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = (int) r;
            result = 31 * result + (int) s;
            return result;
        }
    }

    public static class Hand implements Comparable<Hand> {

        public static final String CARD_SEPARATOR = " ";
        private final Set<Card> cards;
        private final List<Integer> ranks;

        private static Set<Card> toCards(String cardsAsString) {
            String[] cardStrings = cardsAsString.split(CARD_SEPARATOR);
            Set<Card> cards = new HashSet<>(cardStrings.length);
            for (int i = 0; i < cardStrings.length; i++) {
                String card = cardStrings[i];
                cards.add(new Card(card.charAt(0), card.charAt(1)));
            }
            return cards;
        }

        public Hand(String cards) {
            this(toCards(cards));
        }

        public Hand(Collection<Card> cards, Card ... extraCards) {
            this.cards = new HashSet<>(cards.size());
            this.cards.addAll(cards);
            this.cards.addAll(Arrays.asList(extraCards));
            this.ranks = calculateRanks();
        }

        /**
         * The list of ranks, sorted with higher first.
         */
        private List<Integer> calculateRanks() {
            List<Integer> ranks = new ArrayList<>(cards.size());
            for( Card card : cards ) {
                ranks.add(card.rank());
            }
            Collections.sort(ranks, Collections.reverseOrder());
            return ranks.equals(Arrays.asList(14, 5, 4, 3, 2)) ? Arrays.asList(5, 4, 3, 2, 1) : ranks;
        }

        private boolean flush() {
            Set<Character> suites = new HashSet<>();
            for( Card card : cards ) {
                suites.add(card.s);
            }
            return suites.size() == 1;
        }

        private boolean straight() {
            return (Collections.max(ranks) - Collections.min(ranks)) == 4 && (new HashSet<>(ranks)).size() == 5 ;
        }


        /**
         * Returns a value indicating the ranking of a hand.
         */
        public List<Integer> rank() {
            Map<Integer, Integer> countMap = new HashMap<>();
            for( Integer rank : ranks ){
                Integer count = countMap.get(rank);
                if( count == null ) {
                    countMap.put(rank, 1);
                } else {
                    countMap.put(rank, count + 1);
                }
            }

            List<Map.Entry<Integer, Integer>> countList = new ArrayList<>(countMap.entrySet());
            Collections.sort(countList, new Comparator<Map.Entry<Integer, Integer>>() {
                @Override
                public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                    int cmp = o2.getValue().compareTo(o1.getValue()); // sort by count
                    return cmp == 0 ? o2.getKey().compareTo(o1.getKey()) : cmp; // if tie then sort by rank
                }
            });

            List<Integer> counts = new ArrayList<>();
            for(Map.Entry<Integer, Integer> count : countList){
                counts.add(count.getValue());
            }
            Integer[] cs = counts.toArray(new Integer[]{});

            int rank = 0;
            if( cs[0] == 5 ) {
                rank = 9;
            } else if ( straight() && flush() ) {
                rank = 8;
            } else if ( cs[0] == 4 && cs[1] == 1 ) {
                rank = 7;
            } else if ( cs[0] == 3 && cs[1] == 2 ) {
                rank = 6;
            } else if ( flush() ) {
                rank = 5;
            } else if ( straight() ) {
                rank = 4;
            } else if ( cs[0] == 3 && cs[1] == 1 && cs[2] == 1 ) {
                rank = 3;
            } else if ( cs[0] == 2 && cs[1] == 2 && cs[2] == 1 ) {
                rank = 2;
            } else if ( cs[0] == 2 && cs[1] == 1 && cs[2] == 1 && cs[3] == 1) {
                rank = 1;
            }

            List<Integer> res = new ArrayList<>();
            res.add(rank);
            res.addAll(ranks);
            return res;
        }

        @Override
        public int compareTo(Hand o) {
            List<Integer> rank1 = rank();
            List<Integer> rank2 = o.rank();
            int cmp = 0;
            for (int i = 0; i < rank1.size() && cmp == 0; i++) {
                cmp = rank1.get(i).compareTo(rank2.get(i));
            }
            return cmp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Hand hand = (Hand) o;

            if (!cards.equals(hand.cards)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return cards.hashCode();
        }

        @Override
        public String toString() {
            return "Hand{" +
                    "cards=" + cards +
                    '}';
        }
    }


    public static List<Hand> poker(Hand ... hands) {
        Hand bestHand = Collections.max(Arrays.asList(hands));
        List<Hand> result = new ArrayList<>();
        for( Hand hand :  hands ) {
            if( hand.equals(bestHand) ) {
                result.add(hand);
            }
        }
        return result;
    }

    public static List<Hand> deal(int numHands, int numCards, List<Card> deck){
        List<Hand> hands = new ArrayList<>();
        for (int i = 0; i < numHands; i++) {
            hands.add(new Hand(deck.subList(i*numCards, (i+1)*numCards)));
        }
        return hands;
    }

    public static List<Hand> bestWildHand(Hand wildHand) {
        assert( wildHand.cards.size() == 7 );
        List<Card> cards = new ArrayList<>();
        List<Card> jokers = new ArrayList<>();
        for( Card card : wildHand.cards ){
            if( card.joker() ){
                jokers.add(card);
            } else {
                cards.add(card);
            }
        }
        Set<Hand> hands = new HashSet<>();
        hands.add(new Hand(cards));

        for( Card joker : jokers ) { // replacing jokers all possible ways
            List<Card> substitutes = joker.substitutes();
            Set<Hand> newHands = new HashSet<>();
            for( Hand hand  : hands) {
                for( Card substitute : substitutes ){
                    newHands.add(new Hand(hand.cards, substitute));
                }
            }
            hands = newHands;
        }

        List<Hand> allHands = new ArrayList<>();
        for( Hand hand: hands ) { // creating all possible subsets of cards (hands)
            List<Set<Card>> subsets = getSubsets(new ArrayList<>(hand.cards), 5);
            for( Set<Card> subset : subsets ){
                allHands.add(new Hand(subset));
            }
        }

        return poker(allHands.toArray(new Hand[]{}));
    }

    private static <T> void getSubsets(List<T> superSet, int k, int idx, Set<T> current, List<Set<T>> solution) {
        if (current.size() == k) { // successful stop clause
            solution.add(new HashSet<>(current));
            return;
        }
        if (idx == superSet.size()) return; // unsuccessful stop clause
        T item = superSet.get(idx);
        current.add(item); //"guess" item is in the subset
        getSubsets(superSet, k, idx+1, current, solution);
        current.remove(item); //"guess" item is not in the subset
        getSubsets(superSet, k, idx+1, current, solution);
    }

    public static <T> List<Set<T>> getSubsets(List<T> superSet, int k) {
        List<Set<T>> res = new ArrayList<>();
        getSubsets(superSet, k, 0, new HashSet<T>(), res);
        return res;
    }

    public static void main(String[] args) {
        List<Card> deck = Card.cards('S', 'D', 'H', 'C');
        Collections.shuffle(deck);
        List<Hand> hands = deal(3, 5, deck);
        System.out.println("hands = " + hands);
    }
}
