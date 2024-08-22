class Project {
    private int score1;
    private int score2;
    private int score3;

    public Project(int score1, int score2, int score3) {
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
    }

    public int getTotalScore() {
        return score1 + score2 + score3;
    }

    public double getAverageScore() {
        return getTotalScore() / 3.0;
    }

    public String getGrade() {
        double average = getAverageScore();
        if (average >= 80) return "Outstanding";
        if (average >= 70) return "Exceeds Expectations";
        if (average >= 40) return "Meets Expectations";
        return "Needs Improvement";
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public int getScore3() {
        return score3;
    }
}

