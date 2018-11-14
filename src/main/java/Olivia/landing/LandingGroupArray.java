package Olivia.landing;

import Olivia.core.data.GroupArray;
import Olivia.extended.PointI;

/**
 *
 * @author oscar.garcia
 */
public class LandingGroupArray extends GroupArray<LandingGroup> {
    
    public static int DEFAULT_NUMBER_OF_GROUPS = LandingGroup.LANDING2_NUMBER_TYPES1 * LandingGroup.LANDING2_NUMBER_TYPES2;

    public LandingGroupArray() {
        super(DEFAULT_NUMBER_OF_GROUPS);
        int i;
        /*
        for(i=0; i < LandingGroup.LANDING2_NUMBER_TYPES2;i++){
            for(j=0; j < LandingGroup.LANDING2_NUMBER_TYPES1;j++){
                this.add(new LandingGroup(j,i));
            }
        }
        */
        for(i=0; i < DEFAULT_NUMBER_OF_GROUPS;i++){
                this.add(new LandingGroup(i));
        }
    }
}
