package org.ailinykh.charts4s

object plt {

  implicit class CL(cd: ChartDescription) {
    def cl(showLegend: Boolean= false,  domain: String = "")(rl: ColumnLayout => ColumnLayout ): ChartDescription = {
      cd.copy(layout = rl(ColumnLayout(Seq(), showLegend, domain)))
    }

    def rl(showLegend: Boolean= false, range: String="")(rl: RowLayout => RowLayout ): ChartDescription = {
      cd.copy(layout = rl(RowLayout(Seq(), showLegend, range)))
    }
  }


  implicit class CLPlots(rl: ColumnLayout) {
    def withPlot(ylabel: String = "")(p : Plot => Plot): ColumnLayout = {
      rl.copy(plots = rl.plots :+ p(Plot(Seq(), ylabel)))
    }
  }

  implicit class RLPlots(rl: RowLayout) {
    def withPlot(xlabel: String = "")(p : Plot => Plot): RowLayout = {
      rl.copy(plots = rl.plots :+ p(Plot(Seq(), xlabel)))
    }
  }


  implicit class PlotSeries(p: Plot) {
    def addSeries(s: Series ): Plot = {
      p.copy(series = p.series :+ s)
    }

    def add(sName: String="")(x: Array[Double], y: Array[Double]): Plot = {
      val s = Series(XYData(x, y), sName)
      p.copy(series = p.series :+ s)
    }

    def add(x: Array[Double], y: Array[Double]): Plot = {
      val s = Series(XYData(x, y), "")
      p.copy(series = p.series :+ s)
    }

    def add(series: (String, XYData)): Plot = {
      val s = Series(series._2, series._1)
      p.copy(series = p.series :+ s)
    }
  }

  implicit class ChartShow(cd: ChartDescription) {
    def show()(implicit cb: ChartBuilder) = {
      cb.show(cd)
    }
  }

  def plot(data: Double *): ChartDescription ={
    val seq: Seq[Double] = data.view.zipWithIndex.map {
      case (d, i) => 1.0 * i
    }
    val s1 = Series(XYData(data.toArray, seq.toArray ))
    ChartDescription(ColumnLayout(Seq(Plot(Seq(s1), ""))))
  }

  def plot(x: Iterable[Double], y: Iterable[Double] ): ChartDescription ={

    val s1 = Series(XYData(x.toArray, y.toArray ))
    ChartDescription(ColumnLayout(Seq(Plot(Seq(s1), ""))))
  }

  def rplot(title: String = "", showLegend: Boolean = false, range: String="")(rl: RowLayout => RowLayout) ={
    ChartDescription(rl(RowLayout(Seq(), showLegend, range)), title )
  }


}
