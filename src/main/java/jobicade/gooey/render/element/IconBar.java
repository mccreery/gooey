package jobicade.gooey.render.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jobicade.gooey.geom.Direction;

public class IconBar extends GridBuilder<GuiElement> {
    private int totalHalfPoints = 0;
    private GuiElement lastTrailing = null;

    public IconBar() {
        super(new ArrayList<>());
    }

    public int getTotalHalfPoints() {
        return totalHalfPoints;
    }

    public void clear() {
        getSource().clear();
        totalHalfPoints = 0;
    }

    private void syncHalfPoints() {
        List<GuiElement> source = getSource();

        if((this.totalHalfPoints & 1) == 0 || source.isEmpty() || source.get(source.size() - 1) != lastTrailing) {
            this.totalHalfPoints = source.size() * 2;
        }
    }

    public void addGroup(GuiElement leadingHalf, GuiElement full, GuiElement trailingHalf, int halfPoints) {
        syncHalfPoints();
        fillGroupNoSync(leadingHalf, full, trailingHalf, totalHalfPoints + halfPoints);
    }

    public void fillGroup(GuiElement leadingHalf, GuiElement full, GuiElement trailingHalf, int totalHalfPoints) {
        syncHalfPoints();
        fillGroupNoSync(leadingHalf, full, trailingHalf, totalHalfPoints);
    }

    private void fillGroupNoSync(GuiElement leadingHalf, GuiElement full, GuiElement trailingHalf, int totalHalfPoints) {
        if(totalHalfPoints <= this.totalHalfPoints) return;
        List<GuiElement> source = getSource();

        if((this.totalHalfPoints & 1) == 1) {
            if(leadingHalf != null) {
                int i = source.size() - 1;
                source.set(i, new LayeredElement(Arrays.asList(source.get(i), leadingHalf), Direction.CENTER));
            }
            ++this.totalHalfPoints;
        }

        int fullPoints = (totalHalfPoints - this.totalHalfPoints) / 2;
        source.addAll(Collections.nCopies(fullPoints, full));
        this.totalHalfPoints += fullPoints * 2;

        if(this.totalHalfPoints != totalHalfPoints) {
            lastTrailing = trailingHalf;
            source.add(trailingHalf);

            ++this.totalHalfPoints;
        } else {
            lastTrailing = null;
        }
    }
}
