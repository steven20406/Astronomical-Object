import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by Steven 臧 on 29/04/2017.
 */
public class AOBUserInterface extends JFrame {
    private AstronomicalTypeRadio astronomicalType;
    private JComboBox<String> elementBar;
    private JComboBox<String> operatorBar;
    private JTextField rangeBar;
    private JLabel botShowQuery;
    private JLabel picture;
    private JScrollPane scrollPlanetsSection;
    private JScrollPane scrollStarsSection;
    private JScrollPane scrollMessiersSection;
    private Object[][] AOBTabledata;
    private JTable showPlanetsQueryDataTable;
    private JTable showStarsQueryDataTable;
    private JTable showMessiersQueryDataTable;
    private JTextArea queryDataCount;
    private JButton addCondition;
    private JButton doQuery;
    private int count = 1;
//    private ShowPlanetsDataTable showPlanetsQueryDataTable;

    AOBUserInterface() {

        //-------------------------------------------------------------------------------
        //Top section

        JFrame frame = new JFrame("AOBController");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600);

        astronomicalType = new AstronomicalTypeRadio("planets", "stars", "messiers");

        String[] element = {"ra", "decl", "magnitude", "distance"};
        elementBar = new JComboBox(element);

        String[] operator = {">", "=", "<", ">=", "<=", "!="};
        operatorBar = new JComboBox(operator);

        rangeBar = new JTextField(15);

        JPanel condition = new JPanel();
        condition.add(elementBar, BorderLayout.WEST);
        condition.add(operatorBar, BorderLayout.CENTER);
        condition.add(rangeBar, BorderLayout.EAST);

        addCondition = new JButton("Add Condition");

        JPanel topSection = new JPanel();
        topSection.add(astronomicalType, BorderLayout.WEST);
        topSection.add(condition, BorderLayout.CENTER);
        topSection.add(addCondition, BorderLayout.EAST);

        //Top section
        //-------------------------------------------------------------------------------
        //Center section
        showPlanetsQueryDataTable = new JTable(new AOBModel("planets"));
        showStarsQueryDataTable = new JTable(new AOBModel("stars"));
        showMessiersQueryDataTable = new JTable(new AOBModel("messiers"));

        scrollPlanetsSection = new JScrollPane(showPlanetsQueryDataTable);
        scrollStarsSection = new JScrollPane(showStarsQueryDataTable);
        scrollMessiersSection = new JScrollPane(showMessiersQueryDataTable);
        scrollStarsSection.setVisible(false);
        scrollMessiersSection.setVisible(false);

        JPanel centerSectionShow = new JPanel();
        centerSectionShow.setPreferredSize(new Dimension(500,430));
        centerSectionShow.add(scrollPlanetsSection, BorderLayout.EAST);
        centerSectionShow.add(scrollStarsSection, BorderLayout.EAST);
        centerSectionShow.add(scrollMessiersSection, BorderLayout.EAST);

        picture = new JLabel();
        picture.setIcon(new ImageIcon(new ImageIcon("image/" + astronomicalType.getElement() + ".jpg").getImage().getScaledInstance(500, 430, Image.SCALE_DEFAULT)));

        JPanel centerSection = new JPanel();
        centerSection.add(picture, BorderLayout.WEST);
        centerSection.add(centerSectionShow, BorderLayout.EAST);


        //Center section
        //-------------------------------------------------------------------------------
        //Bot section
        queryDataCount = new JTextArea();

        botShowQuery = new JLabel("The query syntax will show in here!");
        doQuery = new JButton("Query");
//        doQuery.addActionListener(new doQueryAction());

        JPanel botSection = new JPanel();
        botSection.add(botShowQuery);
        botSection.add(doQuery);
        botSection.add(queryDataCount);


        //Bot section
        //-------------------------------------------------------------------------------

        frame.add(topSection, BorderLayout.NORTH);
        frame.add(centerSection, BorderLayout.CENTER);
        frame.add(botSection, BorderLayout.SOUTH);

        frame.setVisible(true);

        //-------------------------------------------------------------------------------
    }

    class AstronomicalTypeRadio extends JPanel {
        private ButtonGroup astronomicalType;

        AstronomicalTypeRadio(String radio1, String radio12, String radio3) {
            super(new BorderLayout());
            JRadioButton planetButton = new JRadioButton(radio1);
            JRadioButton starsButton = new JRadioButton(radio12);
            JRadioButton messiersButton = new JRadioButton(radio3);

            planetButton.setSelected(true);

            planetButton.addActionListener(new changeAstonomicalType());
            starsButton.addActionListener(new changeAstonomicalType());
            messiersButton.addActionListener(new changeAstonomicalType());

            astronomicalType = new ButtonGroup();
            astronomicalType.add(planetButton);
            astronomicalType.add(starsButton);
            astronomicalType.add(messiersButton);

            JPanel radioPanel = new JPanel(new GridLayout(1, 0));
            radioPanel.add(planetButton);
            radioPanel.add(starsButton);
            radioPanel.add(messiersButton);

            add(radioPanel, BorderLayout.LINE_START);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        public String getElement() {
            for (Enumeration<AbstractButton> buttons = astronomicalType.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    return button.getText();
                }
            }
            return null;
        }

        class changeAstonomicalType implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                botShowQuery.setText("Change astronomical astronomicalType : " + AOBUserInterface.this.astronomicalType.getElement());
                count = 1;
                picture.setIcon(new ImageIcon(new ImageIcon("image/" + AOBUserInterface.this.astronomicalType.getElement() + ".jpg").getImage().getScaledInstance(500, 400, Image.SCALE_DEFAULT)));
                queryDataCount.setText("");

                if (AOBUserInterface.this.astronomicalType.getElement().equals("planets")) {
                    scrollPlanetsSection.setVisible(true);
                    scrollStarsSection.setVisible(false);
                    scrollMessiersSection.setVisible(false);

                    AOBTabledata=null;
                    rangeBar.setText("");
                }

                if (AOBUserInterface.this.astronomicalType.getElement().equals("stars")) {
                    scrollPlanetsSection.setVisible(false);
                    scrollStarsSection.setVisible(true);
                    scrollMessiersSection.setVisible(false);

                    AOBTabledata=null;
                    rangeBar.setText("");
                }

                if (AOBUserInterface.this.astronomicalType.getElement().equals("messiers")) {
                    scrollPlanetsSection.setVisible(false);
                    scrollStarsSection.setVisible(false);
                    scrollMessiersSection.setVisible(true);

                    AOBTabledata=null;
                    rangeBar.setText("");
                }
            }
        }
    }

    public class AOBModel implements TableModel{
        int columnCount;
        String typeName;
        String colSix;
        String colSeven;


        AOBModel(String type) {
            if (type.equals("planets")) {
                columnCount=6;
                typeName = "Planets Name";
                colSix = "Albedo";
            } else if (type.equals("stars")) {
                columnCount=7;
                typeName = "Star catalogue number";
                colSix = "Type";
                colSeven = "Constellation";
            } else if (type.equals("messiers")) {
                columnCount=7;
                typeName = "Messier catalogue number";
                colSix = "Constellation";
                colSeven = "Description";
            }
        }

        @Override
        public int getRowCount() {
            if(AOBTabledata==null) return 0;
            return AOBTabledata.length;
        }

        @Override
        public int getColumnCount() {
            return columnCount;
        }

        @Override
        public String getColumnName(int columnIndex) {
            if(columnIndex==0) return typeName;
            if(columnIndex==1) return "RA";
            if(columnIndex==2) return "Declination";
            if(columnIndex==3) return "Magnitude";
            if(columnIndex==4) return "Distance";
            if(columnIndex==5) return colSix;
            if(columnIndex==6) return colSeven;
            return null;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if(AOBTabledata==null) return "";
            return AOBTabledata[rowIndex][columnIndex];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        }

        @Override
        public void addTableModelListener(TableModelListener l) {

        }

        @Override
        public void removeTableModelListener(TableModelListener l) {

        }
    }

    public void addConditionListener(ActionListener al) {
        addCondition.addActionListener(al);
    }

    public void addDoQueryListener(ActionListener al) {
        doQuery.addActionListener(al);
    }

    //Getter and Setter

    public AstronomicalTypeRadio getAstronomicalType() {
        return astronomicalType;
    }

    public void setAstronomicalType(AstronomicalTypeRadio astronomicalType) {
        this.astronomicalType = astronomicalType;
    }

    public JComboBox<String> getElementBar() {
        return elementBar;
    }

    public void setElementBar(JComboBox<String> elementBar) {
        this.elementBar = elementBar;
    }

    public JComboBox<String> getOperatorBar() {
        return operatorBar;
    }

    public void setOperatorBar(JComboBox<String> operatorBar) {
        this.operatorBar = operatorBar;
    }

    public JTextField getRangeBar() {
        return rangeBar;
    }

    public void setRangeBar(JTextField rangeBar) {
        this.rangeBar = rangeBar;
    }

    public JLabel getBotShowQuery() {
        return botShowQuery;
    }

    public void setBotShowQuery(JLabel botShowQuery) {
        this.botShowQuery = botShowQuery;
    }

    public JLabel getPicture() {
        return picture;
    }

    public void setPicture(JLabel picture) {
        this.picture = picture;
    }

    public JTextArea getQueryDataCount() {
        return queryDataCount;
    }

    public void setQueryDataCount(JTextArea queryDataCount) {
        this.queryDataCount = queryDataCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object[][] getAOBTabledata() {
        return AOBTabledata;
    }

    public void setAOBTabledata(Object[][] AOBTabledata) {
        this.AOBTabledata = AOBTabledata;
    }

    public JTable getShowPlanetsQueryDataTable() {
        return showPlanetsQueryDataTable;
    }

//    public void setShowPlanetsQueryDataTable(JTable showPlanetsQueryDataTable) {
//        this.showPlanetsQueryDataTable = showPlanetsQueryDataTable;
//    }

    public JTable getShowStarsQueryDataTable() {
        return showStarsQueryDataTable;
    }

    public void setShowStarsQueryDataTable(JTable showStarsQueryDataTable) {
        this.showStarsQueryDataTable = showStarsQueryDataTable;
    }

    public JTable getShowMessiersQueryDataTable() {
        return showMessiersQueryDataTable;
    }

    public void setShowMessiersQueryDataTable(JTable showMessiersQueryDataTable) {
        this.showMessiersQueryDataTable = showMessiersQueryDataTable;
    }

    public JScrollPane getScrollPlanetsSection() {
        return scrollPlanetsSection;
    }

    public void setScrollPlanetsSection(JScrollPane scrollPlanetsSection) {
        this.scrollPlanetsSection = scrollPlanetsSection;
    }
}
