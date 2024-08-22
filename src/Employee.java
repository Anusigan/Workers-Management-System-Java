class Employee {
    private String id;
    private String name;
    private Project project;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setProjectScores(int score1, int score2, int score3) {
        this.project = new Project(score1, score2, score3);
    }

    public Project getProject() {
        return project;
    }

    public void displayReport() {
        System.out.println(id + " | " + name + " | " + project.getScore1() + " | " + project.getScore2() + " | " + project.getScore3() + " | " + project.getTotalScore() + " | " + project.getAverageScore() + " | " + project.getGrade());
    }
}

