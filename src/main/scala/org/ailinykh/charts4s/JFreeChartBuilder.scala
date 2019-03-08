package org.ailinykh.charts4s

import org.jfree.chart.axis.{NumberAxis, ValueAxis}
import org.jfree.chart.plot.{CombinedDomainXYPlot, CombinedRangeXYPlot, PlotOrientation, XYPlot}
import org.jfree.chart.renderer.xy.{StandardXYItemRenderer, XYDotRenderer}
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.chart.{ChartFactory, JFreeChart}
import org.jfree.data.xy.{DefaultXYDataset, XYSeriesCollection}

//case class PlotBuilder(xyData: Option[XYData] = None)


object JFreeChartBuilder extends ChartBuilder {
  def show(cd: ChartDescription) = {
    def buildDataset(series: Seq[Series])={
      val ds = new DefaultXYDataset()
      series.foreach{ s =>
        val size = s.data.x.size
        val arr = Array(s.data.x, s.data.y)
        ds.addSeries(s.name, arr)

      }
      ds
    }

    val af = new ApplicationFrame("title")
    val cplot = cd.layout match {
      case ColumnLayout(plots, showLegend, domain) =>
        val xyplots = plots.map{ p =>
          val ds = buildDataset(p.series)
          val xyplot = new XYPlot()
          xyplot.setDataset(ds)
          val renderer = new StandardXYItemRenderer()
          xyplot.setRenderer( renderer )
          xyplot.setRangeAxes(Array(new NumberAxis(p.label)))

          renderer.setDefaultSeriesVisibleInLegend(showLegend)
          xyplot
        }
        val cplot = new CombinedDomainXYPlot(new NumberAxis(domain));
        xyplots.foreach(c => cplot.add(c))
        cplot
      case RowLayout(plots, showlegend, range) =>
        val xyplots = plots.map{ p =>
          val ds = buildDataset(p.series)
          val xyplot = new XYPlot()
          xyplot.setDataset(ds)
          val renderer = new StandardXYItemRenderer()
          //val renderer = new XYDotRenderer()
          //renderer.setDotWidth(4)
          //renderer.setDotHeight(4)
          xyplot.setRenderer( renderer)
          xyplot.setDomainAxes(Array(new NumberAxis(p.label)))
          renderer.setDefaultSeriesVisibleInLegend(showlegend)
          xyplot
        }
        val cplot = new CombinedRangeXYPlot(new NumberAxis(range))
        cplot.setDomainCrosshairVisible(true)
        cplot.setRangeCrosshairVisible(true)
        xyplots.foreach(c => cplot.add(c))
        cplot

    }

    import org.jfree.chart.ChartPanel
    val chart = new JFreeChart(cd.title, cplot);
    val panel = new ChartPanel(chart)
    panel.setMouseWheelEnabled(true)
    panel.setPreferredSize(new java.awt.Dimension(500, 270))
    af.setContentPane(panel)
    af.setVisible(true)
    af.pack()
  }
}
