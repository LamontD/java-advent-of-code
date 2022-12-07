package com.lamontd.adventofcode.advent2022.dec07;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class DeviceDir extends DeviceItem {
    private final DeviceDir parentDir;
    private Set<DeviceItem> children = new HashSet<>();

    private final String absolutePath;

    public DeviceDir(DeviceDir parentDir, String name) {
        super(name, "dir");
        this.parentDir = parentDir;
        if (parentDir == null) {
            this.absolutePath = name;
        } else {
            this.absolutePath = parentDir.absolutePath + name + "/";
        }
    }

    public void addChild(DeviceItem childItem) {
        children.add(childItem);
    }

    @Override
    public String getAbsolutePath() {
        return this.absolutePath;
    }

    public boolean hasChildDirectory(String childDirName) {
        for (DeviceItem childItem : children) {
            if (StringUtils.equals(childDirName, childItem.name) && childItem.type == "dir") {
                return true;
            }
        }
        return false;
    }

    public Set<DeviceItem> getChildren() {
        return children;
    }

    public DeviceDir getParentDir() {
        return parentDir;
    }

    public DeviceItem findChild(String childName) {
        for (DeviceItem item : children) {
            if (StringUtils.equals(childName, item.name)) {
                return item;
            }
        }
        return null;
    }

    public long getSize() {
        return children.stream().mapToLong(DeviceItem::getSize).sum();
    }

    public boolean isRoot() { return this.parentDir == null; }

    @Override
    public String getDisplayString(String indent) {
        final StringBuilder sb = new StringBuilder();
        sb.append(indent).append("- ").append(name).append(" (dir)");
        children.stream().forEach(item -> sb.append("\n  ").append(item.getDisplayString(indent + "  ")));
        return sb.toString();
    }
}
