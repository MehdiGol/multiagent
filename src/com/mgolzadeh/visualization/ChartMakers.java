package com.mgolzadeh.visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.SortOrder;

public class ChartMakers {

	///////////////////////////////////////////////////////////////////////////////////////////
	public static JFreeChart createBarChart(final CategoryDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createBarChart3D(
            "",      // chart title
            "Category",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        final CategoryPlot plot = chart.getCategoryPlot();
        final CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 9.0)
        );
        
        final CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelsVisible(true);
        final BarRenderer r = (BarRenderer) renderer;
        
        
        
        return chart;

    }
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static JFreeChart createHorizontalBarChart(final CategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createBarChart(
            "",                 // chart title
            "",                  // domain axis label
            "",                 // range axis label
            dataset,                     // data
            PlotOrientation.VERTICAL,  // orientation
            true,                        // include legend
            true,
            false
        );
        chart.setBackgroundPaint(Color.lightGray);
        final CategoryPlot plot = chart.getCategoryPlot();
        return chart;
    }
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static JFreeChart createCategoryLineChart(final CategoryDataset dataset) {
        
		final JFreeChart chart = ChartFactory.createLineChart(
	            "",       // chart title
	            "",                    // domain axis label
	            "",                   // range axis label
	            dataset,                   // data
	            PlotOrientation.VERTICAL,  // orientation
	            true,                      // include legend
	            true,                      // tooltips
	            false                      // urls
	        );
		
		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
	    plot.setRangeGridlinePaint(Color.white);
	    
	    final CategoryItemRenderer rendr= new LineAndShapeRenderer();
	    plot.setRenderer(rendr);
		
		return chart;
		
    }
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static JFreeChart createXYChart(final XYDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createXYLineChart("Predictors Error", "Type", "Value", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(){
            Stroke soild = new BasicStroke(2.0f);
            Stroke dashed =  new BasicStroke(1.0f,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f);
        };

        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        renderer.setBaseStroke(new BasicStroke(3));
        plot.setRenderer(renderer);
        return chart;
    }
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static JFreeChart createDifferenceChart(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "",
            "Data", "Values",
            dataset,
            PlotOrientation.VERTICAL,
            true,  // legend
            true,  // tool tips
            false  // URLs
        );
       
        final XYPlot plot = chart.getXYPlot();
        plot.setRenderer(new XYDifferenceRenderer(Color.green, Color.red, false));
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart;      
    }
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static JFreeChart createDataPredictionChart(final XYDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "",       // chart title
            "Samples",                    // domain axis label
            "Value",                   // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(){
            Stroke soild = new BasicStroke(2.0f);
            Stroke dashed =  new BasicStroke(1.0f,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f);
        };

        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        renderer.setBaseStroke(new BasicStroke(3));
        plot.setRenderer(renderer);
        return chart;
    }
}
