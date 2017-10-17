import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * Created by Steven 臧 on 29/04/2017.
 */

public class AOBController {
    private AOBUserInterface AOBInterface;
    private AOBAction action;

    AOBController(AOBUserInterface ui, AOBAction queryString) {
        this.AOBInterface = ui;
        this.action = queryString;

        this.AOBInterface.addConditionListener(new addConditionButton());
        this.AOBInterface.addDoQueryListener(new doQueryAction());
    }

    class addConditionButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Astronomicaltype = AOBInterface.getAstronomicalType().getElement();
            String element = (String) AOBInterface.getElementBar().getSelectedItem();
            String operator = (String) AOBInterface.getOperatorBar().getSelectedItem();
            String range = AOBInterface.getRangeBar().getText();
            int count = AOBInterface.getCount();
            System.out.println(count);

            if (range.isEmpty()) {
                AOBInterface.getBotShowQuery().setText("select " + Astronomicaltype);
                AOBInterface.setCount(1);
            } else {
                //ra decl magnitude distance
                if (element.equals("ra") || element.equals("decl") || element.equals("magnitude") || element.equals("distance")) {
                    try {
                        Double check = Double.parseDouble(range);

                        if (count == 1) {
                            AOBInterface.getBotShowQuery().setText("select " + Astronomicaltype + " where " + element + " " + operator + " " + check);

                        } else {
                            AOBInterface.getBotShowQuery().setText(AOBInterface.getBotShowQuery().getText() + " and " + element + " " + operator + " " + check);
                        }
                        AOBInterface.setCount(count + 1);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        AOBInterface.getBotShowQuery().setText("Please enter number.");
                        AOBInterface.setCount(1);
                    }
                }
                //
                //
                //
            }
        }
    }

    class doQueryAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = AOBInterface.getBotShowQuery().getText();
            if (query.equals("The query syntax will show in here!") || query.substring(0, 6).equals("Change")) {
                JOptionPane.showMessageDialog(null, "Please create query syntax!");
            }
            else if(query.equals("Please enter number.")){
                JOptionPane.showMessageDialog(null, "Can not search. Please recreate the syntax!");
            }
            else {
                HashSet<AOS> result = action.doQuery(query);
                AOBInterface.getQueryDataCount().setText(result.size() + " matches found");

                Object[][] generateData = null;
                if(AOBInterface.getAstronomicalType().getElement()=="planets") {
                    generateData = action.generatePlanetsData(result);
                    AOBInterface.setAOBTabledata(generateData);
                    AOBInterface.getShowPlanetsQueryDataTable().updateUI();
                }
                else if(AOBInterface.getAstronomicalType().getElement()=="stars"){
                    generateData = action.generateStarsData(result);
                    AOBInterface.setAOBTabledata(generateData);
                    generateData = action.generateStarsData(result);
                    AOBInterface.setAOBTabledata(generateData);
                    AOBInterface.getShowStarsQueryDataTable().updateUI();
                }
                else if(AOBInterface.getAstronomicalType().getElement()=="messiers"){
                    generateData = action.generateMessiersData(result);
                    AOBInterface.setAOBTabledata(generateData);
                    generateData = action.generateMessiersData(result);
                    AOBInterface.setAOBTabledata(generateData);
                    AOBInterface.getShowMessiersQueryDataTable().updateUI();
                }

            }
        }
    }
}
