import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Steven 臧 on 11/04/2017.
 */


public class QueryData {
    private String astronomicalType;
    private ArrayList<QueryCondition> queryConditionsList;


    private QueryData(String astronomicalType) {
        this.astronomicalType = astronomicalType;
        this.queryConditionsList = new ArrayList<>();
    }

    private ArrayList<QueryCondition> getQueryConditionsList() {
        return queryConditionsList;
    }

    @Override
    public String toString() {
        return "QueryData{" +
                "astronomicalType='" + astronomicalType + '\'' +
                '}';
    }

    /**
     *
     * @param Filename
     * @throws FileNotFoundException
     * @throws IOException
     * Read File
     */
    public static void readQueryFromFile(String Filename) throws FileNotFoundException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(Filename));
        String s;


        //Read every line in the file
        int noOfQuery = 1;
        while ((s = bufferedReader.readLine()) != null) {

            System.out.println("No."+noOfQuery+" Query :");
            noOfQuery++;
            QueryData query = readQuery(s);
            if (query != null) {
                HashSet<AOS> result = executeQuery(query);
                System.out.println("Find " + result.size() + " matches.");
                for (AOS aos : result) {
                    System.out.println(aos);
                }
            } else {
                System.out.println("Error syntax!!");
            }
            System.out.println("----------------------------------------------");
        }
    }

    public static HashSet<AOS> readQueryFromString(String queryString){
        int noOfQuery = 1;

            System.out.println("No."+noOfQuery+" Query :");
            noOfQuery++;
            QueryData query = readQuery(queryString);
            if (query != null) {
                HashSet<AOS> result = executeQuery(query);
//                System.out.println("Find " + result.size() + " matches.");
                return result;
            } else {
                System.out.println("Error syntax!!");
            }
            System.out.println("----------------------------------------------");
            return null;
    }

    /**
     *
     * @param str
     * @return QueryData
     * If the return form checkQuery is null   show the message
     * If not null, print the Astronomical type and the QueryCondition list
     * Then return the object QueryData
     */
    public static QueryData readQuery(String str) {
        QueryData query = null;
        if (str != null) {
            query = checkQuery(str);
            if (query == null) {
                System.out.println("Can not execute the query because some error syntax.");
                return null;
            } else {
                System.out.println(query);
                for (QueryCondition x : query.queryConditionsList) {
                    System.out.println(x);
                }
            }
        }
        return query;
    }

    /**
     *
     * @param str
     * @return QueryData
     * Check  the syntax from the file
     * Check Astronomical Type and Query Condition
     * Check every Condition, If valid, add it into Array List which collect the QueryCondition
     * If valid, return a object QueryData which include the Astronomical Type and an array list of QueryCondition
     * If not valid, return null.
     */
    public static QueryData checkQuery(String str) {
        QueryData query = null;
        if (!str.substring(0, 6).equals("select")) {
            System.out.println("Spelling Mistake\" select\", please check! ");
            return null;
        }
        String[] firstSplit = str.split("where");

        /*
        * Check where exist
        * */


        //No Condition or "where" spelling wrong
        if (firstSplit.length == 1) {
            String[] checkAstronomicalType = firstSplit[0].split("\\s");
            String type = checkAstronomicalType[1].trim();
            if (!(type.equals("messiers") || type.equals("stars") || type.equals("planets")) || checkAstronomicalType.length > 2) {
                System.out.println("Astronomical type not exist or wrong spelling \" where\"! Please check!");
                return null;
            } else {
                return new QueryData(type);
            }
        }

        /*
        * Check astronomical type correct
        * */
        String[] checkAstronomicalType = firstSplit[0].split("\\s");
        String type = checkAstronomicalType[1].trim();
        if (!(type.equals("messiers") || type.equals("stars") || type.equals("planets"))) {
            System.out.println("Astronomical type not exist! Please check!");
            return null;
        }
        query = new QueryData(type);

        String[] condition = firstSplit[1].split("and");
        for (int i = 0; i < condition.length; i++) {
            condition[i] = condition[i].trim();
        }
        for (int i = 0; i < condition.length; i++) {
            String[] checkSyntex = condition[i].split("\\s");

            if (checkSyntex.length != 3) {
                System.out.println("And not exist or condition error! Please check!");
                return null;
            } else {
                boolean valid = query.checkCondition(checkSyntex);
                if (valid) {
                    query.addQueryCondition(checkSyntex);
                } else {
                    return null;
                }
            }
        }
//        if(query.getQueryConditionsList().size()==0){
//            return null;
//        }
        return query;
    }

    /**
     *
     * @param condition
     * @return boolean
     * Check the condition syntax, "element" , "operator", and "range"(numbers)
     * If valid, return true
     * Else return false
     */
    private boolean checkCondition(String[] condition) {
        boolean result = true;

        if (!(condition[0].equals("ra") || condition[0].equals("decl") || condition[0].equals("magnitude") || condition[0].equals("distance"))) {
            System.out.println("Element error!");
            result = false;
        }
        if (!(condition[1].equals("<") || condition[1].equals("<=") || condition[1].equals(">") || condition[1].equals(">=") || condition[1].equals("=") || condition[1].equals("!="))) {
            System.out.println("Operator error!");
            result = false;
        }
        try {
            Double.parseDouble(condition[2]);
        } catch (NumberFormatException e) {
            System.out.println("Range is not a number!");
            return false;
        }
        return result;
    }

    /**
     *
     * @param add
     * After Check the condition, Add it into the Array list
     */
    private void addQueryCondition(String[] add) {
        queryConditionsList.add(new QueryCondition(add[0], add[1], Double.parseDouble(add[2])));
    }

    /**
     * The constructor of the condition
     * Contains ""element" , "operator", and "range"(numbers)
     * Getter and Setter Method
     */
    public class QueryCondition {
        private String element;
        private String operator;
        private Double range;

        public QueryCondition(String ele, String ope, Double ran) {
            element = ele;
            operator = ope;
            range = ran;
        }

        public String getElement() {
            return element;
        }

        public void setElement(String element) {
            this.element = element;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public Double getRange() {
            return range;
        }

        public void setRange(Double range) {
            this.range = range;
        }

        @Override
        public String toString() {
            return "QueryCondition{" +
                    "element='" + element + '\'' +
                    ", operator='" + operator + '\'' +
                    ", range=" + range +
                    '}';
        }
    }

    /**
     *
     * @param query
     * @return HashSet<AOS>
     * Execute Query
     * Fetch the corresponding list by Astronomical Type
     * Use "Reflection" to get the method from the AOS class
     */
    private static HashSet<AOS> executeQuery(QueryData query) {
        HashSet<AOS> resultSet = null;
        Class aosClass = AOS.class;
        if (query.astronomicalType.equals("planets")) {
            resultSet = Planets.planetsSet;
        } else if (query.astronomicalType.equals("stars")) {
            resultSet = Stars.starsSet;
        } else if (query.astronomicalType.equals("messiers")) {
            resultSet = Messiers.messierSet;
        }
        for (QueryCondition q : query.getQueryConditionsList()) {
            resultSet = doCondition(aosClass, q, resultSet);
        }
        return resultSet;
    }

    /**
     *
     * @param c
     * @param q
     * @param set
     * @return HashSet<AOS>
     * Fetch the AOS object from the list
     * Do the "operate" method to check if it match the Condition or not
     * If true, add AOS object into the result set
     * If false, check next AOS object
     * return the result set
     */
    private static HashSet<AOS> doCondition(Class c, QueryCondition q, HashSet<AOS> set) {
        HashSet<AOS> resultSet = new HashSet<>();
        try {
            Method method = c.getDeclaredMethod("get" + q.getElement());
            for (AOS aos : set) {
                if (operate(q.operator, (Double) method.invoke(aos), q.getRange())) {
                    resultSet.add(aos);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     *
     * @param operator
     * @param o1
     * @param o2
     * @return boolean
     * Check the relationship from the Condition and the data from two AOS object
     * Return true if correct
     * Return false if incorrect
     */
    private static boolean operate(String operator, double o1, double o2) {
        if (operator.equals(">")) return (o1 > o2);
        else if (operator.equals("=")) return (o1 == o2);
        else if (operator.equals("<")) return (o1 < o2);
        else if (operator.equals(">=")) return (o1 >= o2);
        else if (operator.equals("<=")) return (o1 <= o2);
        else if (operator.equals("!=")) return (o1 != o2);
        else return false;
    }
}
