import java.util.HashSet;
import java.util.Objects;

/**
 * Created by Steven 臧 on 29/04/2017.
 */
public class AOBAction {

    public HashSet doQuery(String queryString){
        HashSet<AOS> result = QueryData.readQueryFromString(queryString);
        return result;
    }

    public Object[][] generatePlanetsData(HashSet<AOS> set){
        Object[][] data = new Object[set.size()][];
        int i=0;
        for(AOS aos : set){
            Planets planets = (Planets) aos;
            String[] temp = {planets.getName(),planets.getra().toString(),planets.getdecl().toString(),planets.getmagnitude().toString(),planets.getdistance().toString(),planets.getPlanetAlbedo().toString()};
            data[i]=temp;
            i++;
        }
        return data;
    }
    public Object[][] generateStarsData(HashSet<AOS> set){
        Object[][] data = new Object[set.size()][];
        int i=0;
        for(AOS aos : set){
            Stars stars = (Stars) aos;
            String[] temp = {stars.getStarID().toString() ,stars.getra().toString() ,stars.getdecl().toString() ,stars.getmagnitude().toString() ,stars.getdistance().toString(),stars.getStarType(), stars.getStarConstellation()};
            data[i]=temp;
            i++;
        }
        return data;
    }
    public Object[][] generateMessiersData(HashSet<AOS> set){
        Object[][] data = new Object[set.size()][];
        int i=0;
        for(AOS aos : set){
            Messiers messiers = (Messiers) aos;
            String[] temp = {messiers.getM42().toString() ,messiers.getra().toString() ,messiers.getdecl().toString() ,messiers.getmagnitude().toString() ,messiers.getdistance().toString(), messiers.getConstellations(), messiers.getDescription()};
            data[i]=temp;
            i++;
        }
        return data;
    }
}
