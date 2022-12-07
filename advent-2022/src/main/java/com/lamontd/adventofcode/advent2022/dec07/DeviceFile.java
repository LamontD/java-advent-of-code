package com.lamontd.adventofcode.advent2022.dec07;

public class DeviceFile extends DeviceItem {
    private final DeviceDir parentDir;
    private final long size;

    public DeviceFile(DeviceDir parentDir, String name, long size) {
        super(name, "file");
        this.parentDir = parentDir;
        this.size = size;
    }

    public DeviceDir getParentDir() {
        return parentDir;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String getDisplayString(String indent) {
        return indent + "- " + name + " (file, size=" + size + ")";
    }

    @Override
    public String getAbsolutePath() {
        return parentDir.getAbsolutePath() + name;
    }
}
