package org.ailinykh.charts4s


case class XYData(x: Array[Double], y:Array[Double])

sealed trait Layout{
  def plots: Seq[Plot]
  def showLegend: Boolean
}
case class ColumnLayout(plots: Seq[Plot], showLegend: Boolean = false, domain: String = "") extends Layout
case class RowLayout(plots: Seq[Plot],showLegend: Boolean = false, range: String="") extends Layout

case class PlotDescription()
case class ChartDescription(layout: Layout, title: String = "")

case class Series( data: XYData, name: String = "")

case class Plot(series: Seq[Series], label: String)

trait ChartBuilder {
  def show(cd: ChartDescription)
}
