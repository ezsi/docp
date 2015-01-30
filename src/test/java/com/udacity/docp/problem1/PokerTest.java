package com.udacity.docp.problem1;

import com.udacity.docp.problem1.Poker.Hand;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class PokerTest {

    private Hand sf = new Hand("6C 7C 8C 9C TC"); // straight flush
    private Hand fk = new Hand("9D 9H 9S 9C 7D"); // four of a kind
    private Hand fh = new Hand("TD TC TH 7C 7D"); // full house
    private Hand f = new Hand("2C 7C 4C 9C TC"); // flush
    private Hand s1 = new Hand("AS 2S 3S 4S 5C"); // A-5 straight
    private Hand s2 = new Hand("2C 3C 4C 5S 6S"); // 2-6 straight
    private Hand tk = new Hand("TD TC TH 4C 7D"); // three of a kind
    private Hand tp = new Hand("5S 5D 9H 9C 6S"); // two pair
    private Hand sp = new Hand("5S 5D AH 9C 6S"); // single pair
    private Hand ah = new Hand("AS 2S 3S 4S 6C"); // A high
    private Hand sh = new Hand("2S 3S 4S 6C 7D"); // 7 high
    private Hand w1 = new Hand("6C 7C 8C 9C TC 5C ?B");
    private Hand w2 = new Hand("TD TC 5H 5C 7C ?R ?B");
    private Hand w3 = new Hand("JD TC TH 7C 7D 7S 7H");

    @Test
    public void testStraightFlushRank() {
        Assert.assertEquals(sf.rank(), Arrays.asList(8, 10, 9, 8, 7, 6));
    }

    @Test
    public void testFourKindRank() {
        Assert.assertEquals(fk.rank(), Arrays.asList(7, 9, 9, 9, 9, 7));
    }

    @Test
    public void testFullHouseRank() {
        Assert.assertEquals(fh.rank(), Arrays.asList(6, 10, 10, 10, 7, 7));
    }

    @Test
    public void testFlushRank() {
        Assert.assertEquals(f.rank(), Arrays.asList(5, 10, 9, 7, 4, 2));
    }

    @Test
    public void testStraightRank() {
        Assert.assertEquals(s1.rank(), Arrays.asList(4, 5, 4, 3, 2, 1));
    }

    @Test
    public void testStraight2Rank() {
        Assert.assertEquals(s2.rank(), Arrays.asList(4, 6, 5, 4, 3, 2));
    }

    @Test
    public void testThreeKindRank() {
        Assert.assertEquals(tk.rank(), Arrays.asList(3, 10, 10, 10, 7, 4));
    }

    @Test
    public void testTwoPairRank() {
        Assert.assertEquals(tp.rank(), Arrays.asList(2, 9, 9, 6, 5, 5));
    }

    @Test
    public void testPairRank() {
        Assert.assertEquals(sp.rank(), Arrays.asList(1, 14, 9, 6, 5, 5));
    }

    @Test
    public void testAceHighRank() {
        Assert.assertEquals(ah.rank(), Arrays.asList(0, 14, 6, 4, 3, 2));
    }

    @Test
    public void test7HighRank() {
        Assert.assertEquals(sh.rank(), Arrays.asList(0, 7, 6, 4, 3, 2));
    }

    @Test
    public void testPoker() {
        Assert.assertEquals(Poker.poker(s1, s2, ah, sh), Arrays.asList(s2));
        Assert.assertEquals(Poker.poker(s1, fk, ah, sh), Arrays.asList(fk));
        Assert.assertEquals(Poker.poker(sf, fk, fh), Arrays.asList(sf));
        Assert.assertEquals(Poker.poker(fh, sf, fk), Arrays.asList(sf));
        Assert.assertEquals(Poker.poker(fk, fh), Arrays.asList(fk));
        Assert.assertEquals(Poker.poker(fh, fh), Arrays.asList(fh, fh)); // tie
        Assert.assertEquals(Poker.poker(sf), Arrays.asList(sf));
        Assert.assertEquals(Poker.poker(fk), Arrays.asList(fk));
        Assert.assertEquals(Poker.poker(fh), Arrays.asList(fh));
        Assert.assertEquals(Poker.poker(s1, s2), Arrays.asList(s2));
        Assert.assertEquals(Poker.poker(s2, s1), Arrays.asList(s2));
        Hand[] fhs = new Hand[100];
        Arrays.fill(fhs, fh);
        fhs[10] = sf;
        Assert.assertEquals(Poker.poker(fhs), Arrays.asList(sf));
    }

    @Test
    public void testBestWildHand() {
        Assert.assertEquals(Poker.bestWildHand(w1), Arrays.asList(new Hand("7C 8C 9C JC TC")));
        Assert.assertEquals(Poker.bestWildHand(w2), Arrays.asList(new Hand("7C TC TD TH TS")));
        Assert.assertEquals(Poker.bestWildHand(w3), Arrays.asList(new Hand("7C 7D 7H 7S JD")));
    }

}

