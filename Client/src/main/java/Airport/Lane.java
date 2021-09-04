package Airport;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Lane {
    String name;
    boolean open;
    Categories category;
    Queue<Integer> flights;

    public Lane(String name, Categories category) {
        this.flights = new LinkedList<>();
        this.name = name;
        this.open = true;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lane lane = (Lane) o;
        return Objects.equals(name, lane.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}