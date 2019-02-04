package Olivia.extended.overlays;

import Olivia.core.Overlay;
import Olivia.core.OverlayArray;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.render.colours.PointColour;
import com.jogamp.opengl.GL2;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Overlay to display a cell (quad) with a label (string) in its centre
 * @author jorge.martinez.sanchez
 * @param <VM> he visualisation class with which it works
 */
public class LabeledCellArray <VM extends VisualisationManager> extends OverlayArray<Overlay<VM>,VM> {

    protected int count;
    protected int font;
    protected PointColour fontColour = new PointColour(0.0f, 0.0f, 1.0f);;
    
    public LabeledCellArray(VM visualisationManager) {
        super(visualisationManager);
        count = 0;
        font = TextOverlay.DEFAULT_FONT;
    }
    
    public void readFromFile(Path path) throws FileNotFoundException, IOException {
        List<String> lines = Files.readAllLines(path);
        if(lines.size()<5) throw new IOException("too few lines");
        String delimiter = "\t";       
        for(int i = 0; i < lines.size(); i+=5) {
           TextOverlay label = new TextOverlay(visualisationManager,lines.get(i));        
           VertexOverlay cellOverlay = new VertexOverlay(visualisationManager,"Cell " + count);
           List<String> quadLines = lines.subList(i+1, i+5);
           cellOverlay.parseQuad(quadLines, delimiter); 
           cellOverlay.setRasterMode(GL2.GL_FILL); 
           cellOverlay.setDefaultColour(new PointColour(1.0f, 0.0f, 0.0f));
           label.moveTo(new Point3D(cellOverlay.getBounds().getCentre().getX(),cellOverlay.getBounds().getCentre().getY(), cellOverlay.getBounds().getMaxZ()+0.5));
           label.setRasterPos(0.0f, 0.0f,0.0f);
           label.setColour(fontColour);
           label.setFont(font);                 
           this.add(cellOverlay);
           this.add(label);
           count++;
        }    
    }
}
