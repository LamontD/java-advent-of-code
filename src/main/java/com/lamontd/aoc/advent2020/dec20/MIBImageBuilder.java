package com.lamontd.aoc.advent2020.dec20;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MIBImageBuilder {

    public static MIBImage extractMIBImage(LocalResourceInput resourceInput) {
        List<MIBImageTile> imageTiles = readTiles(resourceInput);
        return new  MIBImage(imageTiles);
    }

    public static List<MIBImageTile> readTiles(LocalResourceInput resourceInput) {
        List<MIBImageTile> imageTiles = new ArrayList<>();
        Iterator<String> stringIterator = resourceInput.getInput().listIterator();
        while (stringIterator.hasNext()) {
            String tileString = stringIterator.next();
            if (tileString.startsWith("Tile") && tileString.endsWith(":")) {
                int tileNumber = Integer.parseInt(tileString.substring(tileString.indexOf(" "), tileString.length() - 1).trim());
                List<String> tileImageData = new ArrayList<>();
                while (stringIterator.hasNext()) {
                    String dataString = stringIterator.next();
                    if (StringUtils.isEmpty(dataString)) {
                        break;
                    }
                    tileImageData.add(dataString);
                }

                imageTiles.add(new MIBImageTile(tileNumber, tileImageData));
            }
        }
        return imageTiles;
    }
}
