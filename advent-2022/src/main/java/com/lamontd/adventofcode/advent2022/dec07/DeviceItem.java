package com.lamontd.adventofcode.advent2022.dec07;

import java.util.Objects;

public abstract class DeviceItem implements Comparable<DeviceItem> {
    protected final String name;
    protected final String type;

    public DeviceItem(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public abstract String getDisplayString(String indent);
    public abstract String getAbsolutePath();
    public abstract long getSize();


    @Override
    public int compareTo(DeviceItem o) {
        return this.getAbsolutePath().compareTo(o.getAbsolutePath());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceItem that = (DeviceItem) o;
        return this.getAbsolutePath().equals(that.getAbsolutePath());
    }

    @Override
    public int hashCode() {
        return this.getAbsolutePath().hashCode();
    }
}
