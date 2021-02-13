package Team;

public class TeamManager {
    public Team north;
    public Team south;

    public TeamManager() {
        this.north = new Team(Side.North);
        this.south = new Team(Side.South);
    }
}
