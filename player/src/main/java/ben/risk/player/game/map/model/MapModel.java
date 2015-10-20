package ben.risk.player.game.map.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import ben.ui.math.Geo;
import ben.risk.player.mvc.AbstractModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Map Model.
 */
public final class MapModel extends AbstractModel {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(MapModel.class.getSimpleName());

    /**
     * The map data.
     */
    private final List<Geo> map = new ArrayList<>();

    /**
     * Constructor.
     * @param mapResourceName the map file
     */
    public MapModel(String mapResourceName) {
        loadMap(mapResourceName);
    }

    @Override
    public String toString() {
        return MapModel.class.getSimpleName();
    }

    /**
     * Load the map.
     * @param mapResourceName the map file
     */
    private void loadMap(String mapResourceName) {
        LOGGER.info("Loading map: " + mapResourceName);
        try {
            InputStream stream = getClass().getResourceAsStream(mapResourceName);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            NodeList points = doc.getElementsByTagName("point");
            for (int i = 0; i < points.getLength(); i++) {
                Node node = points.item(i);
                NamedNodeMap attributes = node.getAttributes();
                double latitude = Double.parseDouble(attributes.getNamedItem("latitude").getTextContent());
                double longitude = Double.parseDouble(attributes.getNamedItem("longitude").getTextContent());
                map.add(new Geo(latitude, longitude));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Get the map data.
     * @return the map data
     */
    @NotNull
    public List<Geo> getMap() {
        return map;
    }
}