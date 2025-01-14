package graphfx.graphcoloring.test;


public class Level {
    private int id;               // Unique ID for the level
    private String name;          // Name of the level
    private String description;   // Description of the level
    private int difficulty;       // Difficulty level (e.g., 1 = Easy, 2 = Medium, 3 = Hard)
    private int createdBy;        // Admin user ID who created the level

    // Constructor
    public Level(int id, String name, String description, int difficulty, int createdBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.createdBy = createdBy;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficulty=" + difficulty +
                ", createdBy=" + createdBy +
                '}';
    }
}
