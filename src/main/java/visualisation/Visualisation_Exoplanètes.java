package visualisation;

import model.Planete;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;
import tools.Colors;
import tools.ReadCSV;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Visualisation_Exoplanètes extends JDialog {
    private JPanel choicePanel;
    private JPanel XYProperties;
    private ChartPanel chartPanelPlot;
    private XYSeriesCollection xySeriesCollection;
    private JFreeChart chart;
    private JPanel contentPane;
    private JPanel mainPanel;
    private ArrayList<Planete> data;
    private JList listX;
    private JList listY;
    private JCheckBox echelleXLogCheckBox;
    private JCheckBox echelleYLogCheckBox;
    private JCheckBox radialVelocityCheckBox;
    private JCheckBox imagingCheckBox;
    private JCheckBox eclipseTimingVariationsCheckBox;
    private JCheckBox astrometryCheckBox;
    private JCheckBox microLensingCheckBox;
    private JCheckBox pulsarTimingCheckBox;
    private JCheckBox orbitalBrightnessModulationCheckBox;
    private JCheckBox pulsationTimingVariationsCheckBox;
    private JCheckBox transitTimingVariationsCheckBox;
    private JCheckBox transitCheckBox;
    private JCheckBox allCheckBox;
    private JPanel definitionSeries;
    private JPanel SeriesDefinition;
    private JPanel chartPanel;
    private JPanel series;
    private JPanel barPanel;
    private String attributeX;
    private String attributeY;
    private boolean xLog;
    private boolean yLog;
    private int nbMaxSeries;
    private List<JCheckBox> checkBoxes;
    private XYLineAndShapeRenderer renderer;
    private DefaultCategoryDataset barChartDataset;
    private JFreeChart barChart;
    private ChartPanel barChartPlot;
    private CategoryItemRenderer barRenderer;
    private int[] elements;
    private AbstractCategoryItemRenderer barChartRenderer;
    private Shape tempShape;

    private final int[] sizeChartPanel = new int[]{1200,1000};
    private final int[] sizeBarPanel = new int[]{450,50};


    public Visualisation_Exoplanètes() {

        setContentPane(contentPane);
        setModal(true);

        nbMaxSeries = 10;
        elements = new int[nbMaxSeries];

        data = ReadCSV.INSTANCE.run();
        xySeriesCollection = new XYSeriesCollection();
        genererEmptyChart();





        // on définit l'affichage par défaut de l'interface

        echelleXLogCheckBox.setSelected(true);
        echelleYLogCheckBox.setSelected(true);
        xLog = echelleXLogCheckBox.isSelected();
        yLog = echelleYLogCheckBox.isSelected();

        listX.setSelectedIndex(0);
        listY.setSelectedIndex(1);
        attributeX = (String) listX.getSelectedValue();
        attributeY = (String) listY.getSelectedValue();


        ChangeListener listener = changeEvent -> {
            if (isGenerationPossible()) {
                genererChart(attributeX, attributeY, xLog, yLog);
            }
        };

        checkBoxes = new ArrayList<>();
        checkBoxes.add(radialVelocityCheckBox);
        checkBoxes.add(imagingCheckBox);
        checkBoxes.add(eclipseTimingVariationsCheckBox);
        checkBoxes.add(astrometryCheckBox);
        checkBoxes.add(microLensingCheckBox);
        checkBoxes.add(pulsarTimingCheckBox);
        checkBoxes.add(orbitalBrightnessModulationCheckBox);
        checkBoxes.add(pulsationTimingVariationsCheckBox);
        checkBoxes.add(transitTimingVariationsCheckBox);
        checkBoxes.add(transitCheckBox);


        for (JCheckBox checkBox : checkBoxes) {
            checkBox.addChangeListener(listener);
            checkBox.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseMoved(MouseEvent mouseEvent) {
                    if (checkBox.isSelected()) {
                        setTransparentExcept(getId(checkBox.getText()));
                    }
                }
            });
        }

        radialVelocityCheckBox.setSelected(true);
        transitCheckBox.setSelected(true);


        addListListeners();

        addCheckboxListeners();

        allCheckBox.addActionListener(actionEvent -> {

            boolean b = allCheckBox.isSelected();

            for (JCheckBox checkBox : checkBoxes) {
                checkBox.setSelected(b);
            }
        });

        genererChart(attributeX,attributeY,xLog,yLog);


    }


    public static void main(String[] args) {
        Visualisation_Exoplanètes dialog = new Visualisation_Exoplanètes();
        dialog.setTitle("Visualisation de données sur les exoplanètes");
        dialog.pack();
        dialog.setResizable(false);
        dialog.setVisible(true);
        System.exit(0);
    }



    private boolean isGenerationPossible() {

        return listX.getSelectedIndex() != -1 && listY.getSelectedIndex() != -1 && listX.getSelectedIndex() != listY.getSelectedIndex();
    }




    private void genererEmptyChart() {

        xySeriesCollection = new XYSeriesCollection();

        chart = ChartFactory.createScatterPlot(
                "Pas de données",
                "",                      // x axis label
                "",                      // y axis label
                xySeriesCollection,         // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );
        chartPanelPlot = new ChartPanel(chart,sizeChartPanel[0],sizeChartPanel[1],ChartPanel.DEFAULT_MINIMUM_DRAW_WIDTH,ChartPanel.DEFAULT_MINIMUM_DRAW_HEIGHT,
                ChartPanel.DEFAULT_MAXIMUM_DRAW_WIDTH,ChartPanel.DEFAULT_MAXIMUM_DRAW_HEIGHT,true,true,true,true,true,true);
        mainPanel.add(chartPanelPlot);




        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);


        pack();
    }








    private void genererChart (String attributX, String attributY, boolean xLog, boolean yLog) {

        int seriesSelected = 0;
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                seriesSelected++;
            }
        }

        if (seriesSelected==0) {
            genererEmptyChart();

        }

        else {

            mainPanel.removeAll();

            xySeriesCollection = new XYSeriesCollection();

            chart = ChartFactory.createScatterPlot(
                    "Représentation de " + attributY + " en fonction de " + attributX,
                    attributX,                      // x axis label
                    attributY,                      // y axis label
                    getDataset(attributX, attributY),         // data
                    PlotOrientation.VERTICAL,
                    true,                     // include legend
                    true,                     // tooltips
                    false                     // urls
            );


            XYPlot plot = (XYPlot) chart.getPlot();
            renderer = (XYLineAndShapeRenderer) plot.getRenderer();
            setShapes();



            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.BLACK);
            plot.setRangeGridlinePaint(Color.BLACK);



            if (xLog) {
                LogAxis abscisse = new LogAxis(attributX);
                plot.setDomainAxis(abscisse);
            }

            if (yLog) {
                LogAxis ordonnee = new LogAxis(attributY);
                plot.setRangeAxis(ordonnee);
            }


            chartPanelPlot = new ChartPanel(chart, sizeChartPanel[0],sizeChartPanel[1], ChartPanel.DEFAULT_MINIMUM_DRAW_WIDTH, ChartPanel.DEFAULT_MINIMUM_DRAW_HEIGHT,
                    ChartPanel.DEFAULT_MAXIMUM_DRAW_WIDTH, ChartPanel.DEFAULT_MAXIMUM_DRAW_HEIGHT, true, true, true, true, true, true);
            mainPanel.add(chartPanelPlot);

            if (yLog || xLog) {
                chartPanelPlot.setMouseWheelEnabled(false);
            }
            else {
                chartPanelPlot.setMouseWheelEnabled(true);
            }

            chartPanelPlot.addChartMouseListener(new ChartMouseListener() {
                @Override
                public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                    MouseEvent me = chartMouseEvent.getTrigger();
                    if (me.getClickCount() == 2) {
                        chartPanelPlot.restoreAutoBounds();
                    }

                }

                @Override
                public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {

                    Object entity = chartMouseEvent.getEntity();

                    if (entity instanceof XYItemEntity) {
                        XYItemEntity xyEntity = (XYItemEntity) entity;
                        int series = xyEntity.getSeriesIndex();

                        setTransparentExcept(series);
                    }

                    else {

                        setAllNonTransparent ();

                    }

                }
            });


            pack();

        }
    }


    private void setTransparentExcept (int serie) {

        List<XYSeries> liste = xySeriesCollection.getSeries();

        for (int i = 0 ; i < liste.size() ; i++) {

            if (i != serie) {
                Color c = (Color) renderer.getSeriesPaint(i);
                renderer.setSeriesPaint(i, new Color(c.getRed(), c.getGreen(), c.getBlue(), 15));
            }
        }

    }


    private void setAllNonTransparent () {

        List<XYSeries> liste = xySeriesCollection.getSeries();

        for (int i = 0 ; i < liste.size() ; i++) {

            Color c = (Color) renderer.getSeriesPaint(i);
            renderer.setSeriesPaint(i,new Color(c.getRed(),c.getGreen(),c.getBlue(),255));

        }

    }




    public XYSeriesCollection getDataset(String attrX, String attrY) {

        XYSeries radialVelocity = new XYSeries("Radial Velocity");
        radialVelocity.setDescription("Radial Velocity");

        XYSeries imaging = new XYSeries("Imaging");
        imaging.setDescription("Imaging");


        XYSeries eclipseTimingVariations = new XYSeries("Eclipse Timing Variations");
        eclipseTimingVariations.setDescription("Eclipse Timing Variations");

        XYSeries astrometry = new XYSeries("Astrometry");
        astrometry.setDescription("Astrometry");

        XYSeries transit = new XYSeries("Transit");
        transit.setDescription("Transit");

        XYSeries orbitalBrightnessModulation = new XYSeries("Orbital Brightness Modulation");
        orbitalBrightnessModulation.setDescription("Orbital Brightness Modulation");

        XYSeries transitTimingVariations = new XYSeries("Transit Timing Variations");
        transitTimingVariations.setDescription("Transit Timing Variations");

        XYSeries microlensing = new XYSeries("Microlensing");
        microlensing.setDescription("Microlensing");

        XYSeries pulsarTiming = new XYSeries("Pulsar Timing");
        pulsarTiming.setDescription("Pulsar Timing");

        XYSeries pulsationTimingVariations = new XYSeries("Pulsation Timing Variations");
        pulsationTimingVariations.setDescription("Pulsation Timing Variations");


        if (radialVelocityCheckBox.isSelected()) {
            xySeriesCollection.addSeries(radialVelocity);
        }

        if (imagingCheckBox.isSelected()) {
            xySeriesCollection.addSeries(imaging);
        }

        if (eclipseTimingVariationsCheckBox.isSelected()) {
            xySeriesCollection.addSeries(eclipseTimingVariations);
        }

        if (astrometryCheckBox.isSelected()) {
            xySeriesCollection.addSeries(astrometry);
        }

        if (orbitalBrightnessModulationCheckBox.isSelected()) {
            xySeriesCollection.addSeries(orbitalBrightnessModulation);
        }

        if (transitTimingVariationsCheckBox.isSelected()) {
            xySeriesCollection.addSeries(transitTimingVariations);
        }

        if (microLensingCheckBox.isSelected()) {
            xySeriesCollection.addSeries(microlensing);
        }

        if (pulsarTimingCheckBox.isSelected()) {
            xySeriesCollection.addSeries(pulsarTiming);
        }

        if (pulsationTimingVariationsCheckBox.isSelected()) {
            xySeriesCollection.addSeries(pulsationTimingVariations);
        }

        if (transitCheckBox.isSelected()) {
            xySeriesCollection.addSeries(transit);
        }

        elements = new int[10];

        for (Planete p : data) {

            String x_string = get(p, attrX);
            String y_string = get(p, attrY);

            if (!Objects.equals(x_string, "") && !Objects.equals(y_string, "") && !x_string.isEmpty() && !y_string.isEmpty()) {

                double x = Double.parseDouble(x_string);
                double y = Double.parseDouble(y_string);

                String methode = p.getMethode();

                switch (methode) {
                    case "Radial Velocity":
                        radialVelocity.add(x, y);
                        elements[0]++;
                        break;
                    case "Transit":
                        transit.add(x,y);
                        elements[1]++;
                        break;
                    case "Imaging":
                        imaging.add(x, y);
                        elements[2]++;
                        break;
                    case "Eclipse Timing Variations":
                        eclipseTimingVariations.add(x, y);
                        elements[3]++;
                        break;
                    case "Astrometry":
                        astrometry.add(x, y);
                        elements[4]++;
                        break;
                    case "Orbital Brightness Modulation":
                        orbitalBrightnessModulation.add(x, y);
                        elements[5]++;
                        break;
                    case "Transit Timing Variations":
                        transitTimingVariations.add(x, y);
                        elements[6]++;
                        break;
                    case "Microlensing":
                        microlensing.add(x, y);
                        elements[7]++;
                        break;
                    case "Pulsar Timing":
                        pulsarTiming.add(x, y);
                        elements[8]++;
                        break;
                    case "Pulsation Timing Variations":
                        pulsationTimingVariations.add(x, y);
                        elements[9]++;
                        break;
                    default:
                        break;
                }
            }
        }

        radialVelocityCheckBox.setText("Radial Velocity");
        transitCheckBox.setText("Transit");
        imagingCheckBox.setText("Imaging");
        eclipseTimingVariationsCheckBox.setText("Eclipse Timing Variations");
        astrometryCheckBox.setText("Astrometry");
        orbitalBrightnessModulationCheckBox.setText("Orbital Brightness Modulation");
        transitTimingVariationsCheckBox.setText("Transit Timing Variations");
        microLensingCheckBox.setText("Microlensing");
        pulsarTimingCheckBox.setText("Pulsar Timing");
        pulsationTimingVariationsCheckBox.setText("Pulsation Timing Variations");

        generateBarChart(elements);

        return xySeriesCollection;
    }


    private void generateBarChart(int[] elements) {

        barChartDataset = new DefaultCategoryDataset();
        String serie = "occurrences";
        barChartDataset.addValue(elements[0],serie,"Radial Velocity");
        barChartDataset.addValue(elements[1],serie,"Transit");
        barChartDataset.addValue(elements[2],serie,"Imaging");
        barChartDataset.addValue(elements[3],serie,"Eclipse Timing Variations");
        barChartDataset.addValue(elements[4],serie,"Astrometry");
        barChartDataset.addValue(elements[5],serie,"Orbital Brightness Modulation");
        barChartDataset.addValue(elements[6],serie,"Transit Timing Variations");
        barChartDataset.addValue(elements[7],serie,"Microlensing");
        barChartDataset.addValue(elements[8],serie,"Pulsar Timing");
        barChartDataset.addValue(elements[9],serie,"Pulsation Timing Variations");

        // create the chart...
        barChart = ChartFactory.createBarChart(
                "Occurrences de chaque méthode pour les paramètres observés",         // chart title
                "Méthodes",               // domain axis label
                "Valeur",                  // range axis label
                barChartDataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
        );

        final CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4.0)
        );

        barRenderer = plot.getRenderer();
        barRenderer.setSeriesItemLabelGenerator(0,new StandardCategoryItemLabelGenerator());
        barRenderer.setSeriesItemLabelsVisible(0, true);
        barRenderer.setSeriesPaint(0,Color.black);


        barChartPlot = new ChartPanel(barChart, sizeBarPanel[0], sizeBarPanel[1], ChartPanel.DEFAULT_MINIMUM_DRAW_WIDTH, ChartPanel.DEFAULT_MINIMUM_DRAW_HEIGHT,
                ChartPanel.DEFAULT_MAXIMUM_DRAW_WIDTH, ChartPanel.DEFAULT_MAXIMUM_DRAW_HEIGHT, true, true, true, true, true, true);
        barPanel.add(barChartPlot);

    }



    public String get(Planete p, String attribut) {
        String data = "";

        switch (attribut) {

            case "Masse" :
                data = p.getMasse();
                break;
            case "Distance" :
                data = p.getDistance();
                break;
            case "Masse de l'étoile" :
                data = p.getMasse_etoile();
                break;
            case "Semi Grand Axe" :
                data = p.getSemi_grand_axe();
                break;
            case "Période orbitale" :
                data = p.getPeriode_orbitale();
                break;
            case "Rayon Planète" :
                data = p.getRayon_planete();
                break;
            case "Densité" :
                data = p.getDensite();
                break;
            default:
                break;
        }

        return data;

    }



    private void addCheckboxListeners() {

        listX.addListSelectionListener(listSelectionEvent -> {
            attributeX = (String) ((JList) listSelectionEvent.getSource()).getSelectedValue();

            if (isGenerationPossible()) genererChart(attributeX, attributeY, xLog, yLog);
        });

        listY.addListSelectionListener(listSelectionEvent -> {
            attributeY = (String) ((JList) listSelectionEvent.getSource()).getSelectedValue();

            if (isGenerationPossible()) {
                genererChart(attributeX,attributeY,xLog,yLog);
            }
        });

    }


    private void addListListeners () {

        echelleXLogCheckBox.addActionListener(actionEvent -> {
            xLog = echelleXLogCheckBox.isSelected();

            if (isGenerationPossible()) {
                genererChart(attributeX,attributeY,xLog,yLog);
            }
        });


        echelleYLogCheckBox.addActionListener(actionEvent -> {
            yLog = echelleYLogCheckBox.isSelected();

            if (isGenerationPossible()) {
                genererChart(attributeX,attributeY,xLog,yLog);
            }
        });
    }


    public void setShapes () {
        Shape shape1 = ShapeUtilities.createDiagonalCross(2,2);
        Shape shape2 = ShapeUtilities.createDiamond(4);
        Shape shape3 = ShapeUtilities.createDownTriangle(3);
        Shape shape4 = ShapeUtilities.createRegularCross(3,3);
        Shape shape5 = ShapeUtilities.createUpTriangle(3);

        setShape(shape1, "Radial Velocity");
        setShape(shape2 ,"Imaging");
        setShape(shape3, "Transit");
        setShape(shape4, "Astrometry");
        setShape(shape5, "Eclipse Timing Variations");
        setShape(shape1, "Orbital Brightness Modulation");
        setShape(shape2, "Transit Timing Variations");
        setShape(shape3, "Microlensing");
        setShape(shape4, "Pulsar Timing");
        setShape(shape5, "Pulsation Timing Variations");


    }

    public int getId (String s) {
        List<XYSeries> list = xySeriesCollection.getSeries();
        for (int i = 0 ; i < list.size() ; i++) {

            if (Objects.equals(list.get(i).getDescription(), s)) {
                return i;
            }

        }
        return -1;
    }


    public void setShape (Shape shape, String string) {
        int id = getId(string);
        if (id >= 0) {
            renderer.setSeriesShape(id, shape);

            String enumRef = string.replace(" ","_");
            enumRef = enumRef.toUpperCase();

            renderer.setSeriesPaint(id,Colors.valueOf(enumRef).getColor());

        }
    }

}
