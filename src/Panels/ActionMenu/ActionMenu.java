package Panels.ActionMenu;

import Panels.Panel;
import Utility.Spot;

public class ActionMenu extends Panel {

    public ActionMenu(Spot position, Spot size) {
        super(position, size);
    }

    /**
     * Returns the selected action
     * @param click Spot showing where the click is made
     * @return String showing which action is made, of no action is selected returns null
     */
    public String getAction(Spot click) {
        if (click.getHeight() == 0 && (click.getWidth() >= 0 && click.getWidth() < getSize().getWidth())) return "Attack";
        if (click.getHeight() == 1 && (click.getWidth() >= 0 && click.getWidth() < getSize().getWidth())) return "Move";
        if (click.getHeight() == 2 && (click.getWidth() >= 0 && click.getWidth() < getSize().getWidth())) return "Heal";
        return null;
    }
}
