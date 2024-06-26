package org.calendar.objects;

public enum Times {
    ONE(1), TWO(2), THREE(3), FOUR(4),FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(11), TWELVE(12), THIRTEEN(13), FOURTEEN(14), FIFTEEN(15), SIXTEEN(16);

    private final int score;

    Times(int score) {
        this.score =  score;
    }

    public int getScore() {
        return score;
    }
}
