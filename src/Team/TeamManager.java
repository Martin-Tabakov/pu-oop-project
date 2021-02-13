package Team;

public class TeamManager {
    Team north;
    Team south;

    public TeamManager() {
        this.north = new Team(Side.North);
        this.south = new Team(Side.South);
    }
}
