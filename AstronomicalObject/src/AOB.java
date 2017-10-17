import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class AOB {
    private BufferedReader readerPlanets, readerStars, readerMessiers;

    /*
     * Constructor of AOB 3 Buffer reader 3 List, store three different type of
     * data 1 list collect all the data
     */
    AOB(String s1, String s2, String s3) throws FileNotFoundException,
            IOException {
        readerPlanets = new BufferedReader(new FileReader(s1));
        readerStars = new BufferedReader(new FileReader(s2));
        readerMessiers = new BufferedReader(new FileReader(s3));

        this.readPlanetsData();
        this.readStarsData();
        this.readMessiersData();
    }

    /*
     * fetch Planets data lines one by one call the store method call the set
     * method, take list as parameter
     */
    public void readPlanetsData() throws IOException {
        String s;
        while ((s = readerPlanets.readLine()) != null) {
            String[] data = this.storePlanets(s);
            this.setPlanets(data);
        }
    }

    /*
     * fetch Stars data lines one by one call the store method call the set
     * method, take list as parameter
     */
    public void readStarsData() throws IOException {
        String s;
        while ((s = readerStars.readLine()) != null) {
            String[] data = this.storeStars(s);
            this.setStars(data);
        }
    }

    /*
     * fetch Messiers data lines one by one call the store method call the set
     * method, take list as parameter
     */
    public void readMessiersData() throws IOException {
        String s;
        while ((s = readerMessiers.readLine()) != null) {
            String[] data = this.storeStars(s);
            this.setMessiers(data);
        }
    }

    /*
     * split data by "|" and ignore the "space" store data in the temporary list
     * Planets use this method to deal data return the list
     */
    public String[] storePlanets(String s) {
        String[] temp = s.split("\\s+|\\|");
//		for (int i = 0; i < splitData.length; i++) {
//			temp = temp.add(splitData[i]);
//		}
        return temp;
    }

    /*
     * replace all the "space" split data by "|" store data in the temporary
     * list Stars and messier use this method to deal data return the list
     */
    public String[] storeStars(String s) {
        s = s.replaceAll(" +", "");
        String temp[] = s.split("\\|");
        return temp;
    }

    // create a Planets object and add it to the planet list
    public void setPlanets(String[] data) {
        Planets p1 = new Planets(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
        Planets.planetsSet.add(p1);
    }

    // create a Stars object and add it to the star list
    public void setStars(String[] data) {
        Constellations.addConstellations(data[6]);
        Stars p1 = new Stars(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), data[5], data[6]);
        Stars.starsSet.add(p1);
    }

    // create a Messier object and add it to the Messiers list
    public void setMessiers(String[] data) {
        Double M42p, rap, decp, magp, disp;
        String con, des;

        if (data.length == 7) {
            Constellations.addConstellations(data[5]);
            Messiers p1 = new Messiers(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), data[5], data[6]);
            Messiers.messierSet.add(p1);
        } else {
            Constellations.addConstellations(data[5]);
            Messiers p1 = new Messiers(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), data[5], "No Data Here!");
            Messiers.messierSet.add(p1);
        }
    }


    public static void main(String[] args) {
        try {
            if (args.length != 4) {
                System.out
                        .println("Please type 3 file name. Planet,Stars,Messier and query document.");
                System.exit(-1);
            }
            AOB aob = new AOB(args[0], args[1], args[2]);
            ArrayList<HashSet<AOS>> collection = new ArrayList<>();
            collection.add(Planets.planetsSet);
            collection.add(Stars.starsSet);
            collection.add(Messiers.messierSet);

            //stage 3
            AOBUserInterface ui= new AOBUserInterface();
            AOBAction createQuery = new AOBAction();
            AOBController search = new AOBController(ui,createQuery);

            //Stage 2
            QueryData.readQueryFromFile(args[3]);

            //Stage 1
            // Q1
//            System.out.println("Q1 : " + Planets.planetsSet.size());

            // Q2
//            System.out.println("Q2 : " + Messiers.messierSet.size());

            // Q3
//            System.out.println("Q3 : " + Stars.starsSet.size());

            // Q4
//            AOS q4ans = null;
//            for (HashSet<AOS> set : collection) {
//                for (AOS aos : set) {
//                    if (q4ans == null) {
//                        q4ans = aos;
//                    }
//                    q4ans = aos.getdistance() < q4ans.getdistance() ? aos : q4ans;
//                }
//            }
//            System.out.println("Q4 : " + q4ans.toString());

            // Q5
//            AOS q5ans = null;
//            for (HashSet<AOS> set : collection) {
//                for (AOS aos : set) {
//                    if (q5ans == null) {
//                        q5ans = aos;
//                    }
//                    q5ans = aos.getdistance() > q5ans.getdistance() ? aos : q5ans;
//                }
//            }
//            System.out.println("Q5 : " + q5ans);

            // Q6
//            AOS q6ans = null;
//            for (AOS aos : Stars.starsSet) {
//                if (q6ans == null) {
//                    q6ans = aos;
//                }
//                q6ans = aos.getdistance() < q6ans.getdistance() ? aos : q6ans;
//            }
//            System.out.println("Q6 : " + q6ans.toString());

            // Q7
//            AOS q7ans = null;
//            for (AOS aos : Stars.starsSet) {
//                if (q7ans == null) {
//                    q7ans = aos;
//                }
//                q7ans = aos.getmagnitude() < q7ans.getmagnitude() ? aos : q7ans;
//            }
//            System.out.println("Q7 : " + q7ans.toString());

            // Q8
//            AOS q8ans = null;
//            for (AOS aos : Stars.starsSet) {
//                if (q8ans == null) {
//                    q8ans = aos;
//                }
//                q8ans = aos.getmagnitude() > q8ans.getmagnitude() ? aos : q8ans;
//            }
//            System.out.println("Q8 : " + q8ans.toString());

            // Q9

//            System.out.println("Q9 : " + Constellations.allConstellations.size());

            // Find Q10
//            System.out.println("Q10: " + Constellations.largestConstellations().getValue());

        } catch (
                FileNotFoundException e)

        {
            System.out.println("Cannot write to file");
            System.exit(1);
        } catch (
                NumberFormatException e)

        {
            System.out.println("Cannot parse number");
            System.exit(1);
        } catch (
                IOException e)

        {
            System.out.println("Error writing to file");
            System.exit(1);
        } catch (
                NullPointerException e)

        {
            System.out.println("Null Point!");
            System.exit(1);
        }

    }
}
